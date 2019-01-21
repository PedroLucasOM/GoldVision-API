package com.system.algamoney.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ResponseUtil {

	public String gerarSenhaCriptografada(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
}
