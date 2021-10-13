package com.bci.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bci.demo.exceptions.DuplicateMailException;
import com.bci.demo.exceptions.InvalidMailDomain;
import com.bci.demo.exceptions.InvalidPassword;
import com.bci.demo.model.User;
import com.bci.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository repositoryUserModel;
	
	private static final String regex = "^(.+)@(.+)$";
	private static final String mayus = "[A-Z]{1,}";
	private static final String minus = "[a-z]{1,}";
	private static final String digits = "[0123456789]{2,}";

	public Boolean create(User user) throws ParseException, DuplicateMailException,InvalidMailDomain, InvalidPassword,Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateFormat = sdf.format(new Date());
		Date today = sdf.parse(dateFormat);
		user.setModified(today);
		user.setCreated(today);
		Pattern mailPatern = Pattern.compile(regex);
		Matcher mailmatcher = mailPatern.matcher(user.getEmail());
		
		Pattern mayusPatern = Pattern.compile(mayus);
		Matcher mayusmatcher = mayusPatern.matcher(user.getPassword());
		
		Pattern minusPatern = Pattern.compile(minus);
		Matcher minusmatcher = minusPatern.matcher(user.getPassword());
		
		Pattern digitsPatern = Pattern.compile(digits);
		Matcher digitsmatcher = digitsPatern.matcher(user.getPassword());
		
		if(!digitsmatcher.matches()) {
			throw new InvalidPassword();
		}
		
		if(!minusmatcher.matches()) {
			throw new InvalidPassword();
		}
		
		if(!mayusmatcher.matches()) {
			throw new InvalidPassword();
		}
		
		if(!mailmatcher.matches()) {
			throw new InvalidMailDomain();
		}
		User mailCondition = repositoryUserModel.getMailByString(user.getEmail());
		if (null != mailCondition) {
			throw new DuplicateMailException();
		}
		repositoryUserModel.save(user);

		return true;
	}

}
