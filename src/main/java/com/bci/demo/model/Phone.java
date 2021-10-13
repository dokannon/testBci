package com.bci.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

@Entity
public class Phone implements Serializable {
	
	private static final long serialVersionUID = 338146260510720464L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_sequence")
	@SequenceGenerator(name = "phone_sequence", sequenceName = "phone_sequence")
	private Integer id;

	private Integer number;
	private Integer citycode;
	private Integer contrycode;
	
	@JoinColumn( name = "idFk",referencedColumnName = "userId",insertable = true,updatable = false)
	private User userModel;

	public Phone() {

	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public User getUserModel() {
		return userModel;
	}

	public void setUserModel(User userModel) {
		this.userModel = userModel;
	}

	public Integer getCitycode() {
		return citycode;
	}

	public void setCitycode(Integer citycode) {
		this.citycode = citycode;
	}

	public Integer getContrycode() {
		return contrycode;
	}

	public void setContrycode(Integer contrycode) {
		this.contrycode = contrycode;
	}



}
