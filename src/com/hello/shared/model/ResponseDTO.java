package com.hello.shared.model;

public class ResponseDTO {
    private String statusCode;

    private String statusMsg;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public ResponseDTO(String statusCode, String statusMsg) {
		super();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}

	public ResponseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "ResponseDTO [statusCode=" + statusCode + ", statusMsg=" + statusMsg + "]";
	}
	
	
	
	
	

    
    

}