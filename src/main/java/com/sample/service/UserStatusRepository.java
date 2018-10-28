package com.sample.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, String> {

	List<UserStatus> findByEmployeeId(final String employeeId);

	List<UserStatus> findByLineUserId(final String lineUserId);

}
