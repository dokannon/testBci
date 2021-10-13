package com.bci.demo.exceptions;

import org.springframework.stereotype.Component;

@Component
public class Message {

	public String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
