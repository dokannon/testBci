package com.bci.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bci.demo.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	@Query(value = "SELECT HIBERNATE_SEQUENCE.NEXTVAL FROM DUAL;",nativeQuery = true)
	public Integer getNextVal();
	
	@Query(value = "SELECT * FROM USER WHERE EMAIL = :email",nativeQuery = true)
	public User getMailByString(@Param("email") String email);
}
