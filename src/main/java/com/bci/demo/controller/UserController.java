package com.bci.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bci.demo.dto.UserDTO;
import com.bci.demo.exceptions.DuplicateMailException;
import com.bci.demo.exceptions.InvalidMailDomain;
import com.bci.demo.exceptions.Message;
import com.bci.demo.model.User;
import com.bci.demo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController()
@RequestMapping(path = "/")
public class UserController {
	@Autowired
	UserService service;

	@PostMapping("/user")
	public ResponseEntity<?> create(@RequestBody User user) {
		UserDTO userDTO;
		try {

			user.setToken(getJWTToken(user.getName()));
			service.create(user);
			userDTO = new UserDTO();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String dateFormat = sdf.format(new Date());
			Date today = sdf.parse(dateFormat);
			userDTO.setModified(today);
			userDTO.setLast_login(today);
			userDTO.setToken(user.getToken());
			userDTO.setCreated(today);
			userDTO.setId(user.getUserId());
		} catch (ParseException e) {
			Message message = new Message();
			message.setMensaje("error en formato de fecha");
			return new ResponseEntity<Message>(message, HttpStatus.CONFLICT);
		} catch (InvalidMailDomain e) {
			Message message = new Message();
			message.setMensaje("error en formato de dominio de correo");
			return new ResponseEntity<Message>(message, HttpStatus.CONFLICT);
		} catch (DuplicateMailException e) {
			Message message = new Message();
			message.setMensaje("El correo ya registrado");
			return new ResponseEntity<Message>(message, HttpStatus.FOUND);
		} catch (Exception e) {
			Message message = new Message();
			message.setMensaje("error encontrado");
			return new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
