package com.emrekoca.motd;

public class MessageOfTheDayBasicImpl implements MessageOfTheDayService {

	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getTodaysMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}

}
