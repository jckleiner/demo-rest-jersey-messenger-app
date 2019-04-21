package com.greydev.messenger.database;

import java.util.Calendar;

import javax.ws.rs.ext.ParamConverter;

public class MyDateConverter implements ParamConverter<MyDate> {

	@Override
	public MyDate fromString(String value) {
		MyDate myDate = new MyDate();

		if ("today".equalsIgnoreCase(value)) {
			// Field number for get and set indicating the day of the month.
			// This is a synonym for DAY_OF_MONTH.
			myDate.setDay(Calendar.getInstance().get(Calendar.DATE));
			myDate.setMonth(Calendar.getInstance().get(Calendar.MONTH));
			myDate.setYear(Calendar.getInstance().get(Calendar.YEAR));
		}
		else if ("yesterday".equalsIgnoreCase(value)) {
			myDate.setDay(Calendar.getInstance().get(Calendar.DATE) - 1);
			myDate.setMonth(Calendar.getInstance().get(Calendar.MONTH));
			myDate.setYear(Calendar.getInstance().get(Calendar.YEAR));
		}
		else if ("tomorrow".equalsIgnoreCase(value)) {
			myDate.setDay(Calendar.getInstance().get(Calendar.DATE) + 1);
			myDate.setMonth(Calendar.getInstance().get(Calendar.MONTH));
			myDate.setYear(Calendar.getInstance().get(Calendar.YEAR));
		}
		return myDate;
	}

	@Override
	public String toString(MyDate value) {
		// TODO Auto-generated method stub
		return null;
	}

}
