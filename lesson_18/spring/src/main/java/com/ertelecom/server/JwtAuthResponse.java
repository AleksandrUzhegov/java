package com.ertelecom.server;

public class JwtAuthResponse {
	private final String jwtToken;

	public JwtAuthResponse(String jwttoken) {
		this.jwtToken = jwttoken;
	}

	public String getToken() {
		return this.jwtToken;
	}
}