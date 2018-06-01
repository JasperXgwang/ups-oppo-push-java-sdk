package com.ups.oppo.push.server.sdk.exception;


public class RespException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2147516542125786933L;

	private final int status;

	private final String description;

	public RespException(int status) {
		this(status, (String) null);
	}

	public RespException(int status, String description) {
		super(getMessage(status, description));
		this.status = status;
		this.description = description;
	}

	private static String getMessage(int status, String description) {
		StringBuilder base = (new StringBuilder("NSP Status Code: "))
				.append(status);
		if (description != null) {
			base.append("(").append(description).append(")");
		}

		return base.toString();
	}

	public int getNspStatusCode() {
		return this.status;
	}

	public String getDescription() {
		return this.description;
	}

}
