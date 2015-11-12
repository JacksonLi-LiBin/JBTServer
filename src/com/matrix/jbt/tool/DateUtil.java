package com.matrix.jbt.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

	public static TimeZone defaultTimeZone;

	public static Date toGMT(String time) throws ParseException {

		DateFormat formatY = new SimpleDateFormat("yyyy-MM-dd");
		String ymd = formatY.format(new Date());

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
		sdf1.setTimeZone(DateUtil.defaultTimeZone);
		
		Date date = sdf1.parse(ymd + " " + time);

		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String result = format.format(date);
		return format.parse(result);
	}

}
