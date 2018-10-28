package com.sample.service;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

@Entity
@Table(name = "USER_STATUS")
@Data
@NoArgsConstructor
public class UserStatus {


	// どーやるんだっけ？
	public UserStatus(final String lineUserId, final Integer talkStatus) {
		this.lineUserId = lineUserId;
		this.talkStatus = talkStatus;
	}


	@Id
	private String lineUserId;

	private Integer talkStatus; // 0:なし 1:社員番号待ち 2:パスワード待ち 3:トークンあり

	private String employeeId;
	private String attendanceToken;
	private Date targetDate;

//	@Column(name="value",columnDefinition = "default now()")
	private Date updated;

	public boolean isExpired() {
		val limit = Calendar.getInstance();
		limit.add(Calendar.HOUR, -1);
		return false;
//		return (talkStatus != 3 && updated.before(limit.getTime()));
	}

}
