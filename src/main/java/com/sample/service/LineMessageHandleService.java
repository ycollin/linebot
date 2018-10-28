package com.sample.service;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.sample.attendance.api.AttendanceClient;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
@Service
@ComponentScan({ "com.sample.attendance.api" })
public class LineMessageHandleService {

	private final UserStatusRepository userStatusRepository;
	private final AttendanceClient attendanceClient;

	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {

		System.out.println("event: " + event);
		val messageText = event.getMessage().getText();
		val lineUserId = event.getSource().getUserId();
		val userStatus = findByUserId(lineUserId);

		if (userStatus == null || userStatus.isExpired()) {
			userStatusRepository.save(new UserStatus(lineUserId, 1));
			return new TextMessage("社員番号を入力してください");
		}

		// TODO: 汚い
		switch (userStatus.getTalkStatus()) {

		case 0:
			return new TextMessage("社員番号を入力してください");

		case 1: // wait employee id
			if (isValidEmployeeId(messageText)) {
				userStatus.setTalkStatus(2);
				userStatus.setEmployeeId(messageText);
				userStatusRepository.save(userStatus);
				return new TextMessage("パスワードを入力してください");
			} else {
				userStatusRepository.save(userStatus);
				return new TextMessage("社員番号が不正です。再度入力してください");
			}

		case 2: // wait password
			val authResult = attendanceClient.auth(userStatus.getEmployeeId(), messageText);
			if (authResult.isOk()) {
				userStatus.setTalkStatus(3);
				userStatus.setAttendanceToken(authResult.getToken());
				userStatusRepository.save(userStatus);
				return new TextMessage("認証しました。出勤　または　退勤　を入力すると、当日の勤怠を登録できます。");

			} else {
				return setReAuthStatus(userStatus);
			}

		case 3: // authorized
			val id = userStatus.getEmployeeId();
			val token = userStatus.getAttendanceToken();

			if (messageText.equals("出勤")) {
				val result = attendanceClient.simpleAttendanceRegister(id, token);
				val info = attendanceClient.getBaseInfo(id, token);
				if (!result.isOk() || !info.isOk()) {
					return setReAuthStatus(userStatus);
				}
				return new TextMessage(result.getMessage() + "\n" + info.toString());

			} else if (messageText.equals("退勤")) {
				val result = attendanceClient.simpleLeaveRegister(id, token);
				val info = attendanceClient.getBaseInfo(id, token);
				if (!result.isOk() || !info.isOk()) {
					return setReAuthStatus(userStatus);
				}

				return new TextMessage(result.getMessage() + "\n" + info.toString());

			} else {
				return new TextMessage("出勤　または　退勤　を入力すると、当日の勤怠を登録できます。");
			}

		default:
			userStatus.setTalkStatus(1);
			userStatusRepository.save(userStatus);
			return new TextMessage("社員番号を入力してください？");
		}
	}

	// need re-Authorization.
	private TextMessage setReAuthStatus(final UserStatus userStatus) {
		userStatus.setTalkStatus(1);
		userStatus.setAttendanceToken("");
		userStatusRepository.save(userStatus);
		return new TextMessage("認証に失敗しました。再度社員番号を入力してください");
	}

	private UserStatus findByUserId(final String lineUserId) {
		val users = userStatusRepository.findByLineUserId(lineUserId);

		if (users.isEmpty()) {
			return null;
		} else if (users.size() >= 2) {
			userStatusRepository.delete(lineUserId);
			return null;
		}
		return users.get(0);

	}

	private boolean isValidEmployeeId(final String employeeId) {
		return Pattern.compile("[0-9]*").matcher(employeeId).matches();
	}

}
