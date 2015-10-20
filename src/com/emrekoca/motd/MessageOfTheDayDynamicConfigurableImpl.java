package com.emrekoca.motd;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MessageOfTheDayDynamicConfigurableImpl implements
		MessageOfTheDayService {

	private String[] messageList;
	
	public void setMessageList(String[] messageList)
	{
		this.messageList = messageList;
	}
	
	@Override
	public String getTodaysMessage() 
	{
		// What day is it?
		int day = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);

		// days are 1 based in Java!
		String message = messageList[day-1];

		return message;
	}

}
