package net.jin.common.exception;

public class ApiErrorDetailInfo {

	private String target;

	private String message;

	public ApiErrorDetailInfo(String target, String message) {
		this.target = target;
		this.message = message;
	}

	public String getTarget() {
		return target;
	}

	public String getMessage() {
		return message;
	}

}
