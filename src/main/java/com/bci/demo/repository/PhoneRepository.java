package com.bci.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bci.demo.model.Phone;
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer>{
	@Query(value="select * from phone where userId = :userId;" ,nativeQuery = true)
	public List<Phone> getPhonesById(@Param("userId") Integer id);
}
