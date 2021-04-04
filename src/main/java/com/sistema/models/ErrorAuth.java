package com.sistema.models;

public class ErrorAuth {

	private String message;

	private static boolean erro;

	public ErrorAuth() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static boolean isErro() {
		return erro;
	}

	public static void setErro(boolean erro) {
		ErrorAuth.erro = erro;
	}

}
