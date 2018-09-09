package com.framework.core.utils.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

	public static final String DATE_FORMAT_CHINESE_DAY = "yyyy年MM月dd日";

	public static final String DATE_FORMAT_CHINESE_MINUTE = "yyyy年MM月dd日 HH:mm";

	public static final String DATE_FORMAT_CHINESE_SECOND = "yyyy年MM月dd日 HH:mm:ss";
	
	public static final String DATE_FORMAT_NORMAL_YEAR = "yyyy";

	public static final String DATE_FORMAT_NORMAL_MONTH = "yyyy-MM";

	public static final String DATE_FORMAT_NORMAL_DAY = "yyyy-MM-dd";

	public static final String DATE_FORMAT_NORMAL_MINUTE = "yyyy-MM-dd HH:mm";

	public static final String DATE_FORMAT_NORMAL_SECOND = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT_NORMAL_SECOND_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String TIME_FORMAT_NORMAL = "HH:mm";

	public static final String TIME_FORMAT_NORMAL_HH = "HH";

	public static final String TIME_FORMAT_NORMAL_MM = "mm";

	public static final String TIME_FORMAT_NORMAL_YYYYMM = "yyyyMM";
	
	public static final String TIME_FORMAT_NORMAL_YYYYMMDD = "yyyyMMdd";
	
	public static final String TIME_FORMAT_NORMAL_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 功能：获得当前web服务器日期 格式：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return getDateString(new java.util.Date(), DATE_FORMAT_NORMAL_DAY);
	}

	/**
	 * 功能：获得当前web服务器日期 自定义格式
	 * 
	 * @return
	 */
	public static String getCurrentDateByFormat(String format) {
		return getDateString(new java.util.Date(), format);
	}

	/**
	 * 功能：获得当前web服务器日期 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getDateString(new java.util.Date(), DATE_FORMAT_CHINESE_SECOND);
	}
	
	/**
	 * 功能：获得当前web服务器日期 格式：yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeSSS() {
		return getDateString(new java.util.Date(), DATE_FORMAT_NORMAL_SECOND_SSS);
	}
	

	/**
	 * 功能：时间转换成yyyy-MM-dd格式字符串
	 * 
	 * @return
	 */
	public static String getDateString(Date date) {
		return getDateString(date, DATE_FORMAT_NORMAL_DAY);
	}

	/**
	 * 功能：时间转换成yyyy-MM-dd HH:mm:ss格式字符串
	 * 
	 * @return
	 */
	public static String getDateTimeString(Date date) {
		return getDateString(date, DATE_FORMAT_CHINESE_SECOND);
	}

	/**
	 * 根据format格式格式化Date 返回String
	 * 
	 * @param date
	 * @param format
	 *            DateUtil.DATE_FORMAT_CHINESE_DAY = "yyyy年MM月dd日"; DateUtil.DATE_FORMAT_CHINESE_MINUTE =
	 *            "yyyy年MM月dd日 HH:mm"; DateUtil.DATE_FORMAT_CHINESE_SECOND = "yyyy年MM月dd日 HH:mm:ss";
	 *            DateUtil.DATE_FORMAT_NORMAL_YEAR = "yyyy"; DateUtil.DATE_FORMAT_NORMAL_MONTH = "yyyy-MM";
	 *            DateUtil.DATE_FORMAT_NORMAL_DAY = "yyyy-MM-dd"; DateUtil.DATE_FORMAT_NORMAL_MINUTE =
	 *            "yyyy-MM-dd HH:mm"; DateUtil.DATE_FORMAT_NORMAL_SECOND = "yyyy-MM-dd HH:mm:ss";
	 *            DateUtil.TIME_FORMAT_NORMAL = "HH:mm"; DateUtil.TIME_FORMAT_NORMAL_HH = "HH";
	 *            DateUtil.TIME_FORMAT_NORMAL_MM = "mm";
	 * @return
	 */
	public static String getDateString(Date date, String format) {
		String ret = "";
		if (format != null) {
			ret = new java.text.SimpleDateFormat(format).format(date);
		} else {
			ret = new java.text.SimpleDateFormat(DATE_FORMAT_NORMAL_MINUTE).format(date);
		}
		return ret;
	}

	/**
	 * 根据format格式化String，返回Date
	 * 
	 * @param datastr
	 * @param format
	 * @return Date
	 */
	public static Date stringToDate(String datestr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			if (datestr == null) {
				return null;
			}
			Date date = sdf.parse(datestr);
			return date;
		} catch (ParseException e) {
			// Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取接下来的多少天。next可以为负数
	 * @param d
	 * @param next
	 * @return
	 */
	public static Date nextDay(Date d,int next){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+next);
		return new Timestamp(c.getTimeInMillis());
	}
	
	/**
	 * 获取接下来的多少月。next可以为负数
	 * @param d
	 * @param next
	 * @return
	 */
	public static Date nextMonth(Date d, int next){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+next);
		return c.getTime();
	}
	/**
	 * 获取接下来的多少年。next可以为负数
	 * @param d
	 * @param next
	 * @return
	 */
	public static Date nextYear(Date d, int next){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR)+next);
		return c.getTime();
	}
	/**
	 * 获取接下来的多少小时。next可以为负数
	 * @param d
	 * @param next
	 * @return
	 */
	public static Date nextHour(Date d, int next){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)+next);
		return c.getTime();
	}
	/**
	 * 获取接下来的多少分钟。next可以为负数
	 * @param d
	 * @param next
	 * @return
	 */
	public static Date nextMinute(Date d, int next){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+next);
		return c.getTime();
	}
	/**
	 * 获取接下来的多少秒。next可以为负数
	 * @param d
	 * @param next
	 * @return
	 */
	public static Date nextSecond(Date d, int next){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.SECOND, c.get(Calendar.SECOND)+next);
		return c.getTime();
	}
	
	
	/**
	 * 两天之间的所有天,不分前后
	 * @param begin
	 * @param end
	 * @return
	 */
	public static List<Date> getBetweenDate(Date date1,Date date2){
		List<Date> ret = new ArrayList<Date>();
		
		long num = getQuot(date1, date2);
		
		Calendar c = Calendar.getInstance();
		c.setTime(date1.getTime()<date2.getTime()?date1:date2);
		ret.add(new Timestamp(c.getTimeInMillis()));
		while(num>0){
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
			ret.add(new Timestamp(c.getTimeInMillis()));
			num--;
		}
		return ret;
	}
	
	/**
	 * 两天之间的天数, 不分前后
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getQuot(Date date1,Date date2){
		long quot = 0;
		Date b1 = Timestamp.valueOf(getDate2Str(date1, "yyyy-MM-dd")+" 00:00:00.000");
		Date e1 = Timestamp.valueOf(getDate2Str(date2, "yyyy-MM-dd")+" 00:00:00.000");
		
		quot = b1.getTime() - e1.getTime();
		quot = quot>0?quot:-1*quot;
		quot = quot / 1000 / 60 / 60 / 24;
		return quot;
	}
	/**
	 * 获取下周的当天（即加7）
	 * @param dateStart
	 * @return
	 */
	public static  Date getNextWeek(Date dateStart) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateStart);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+7);
		return new Timestamp(c.getTimeInMillis());
	}
	
	/**
	 * 获取上周的当天（即加7）
	 * @param dateStart
	 * @return
	 */
	public static  Date getUpWeek(Date dateStart) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateStart);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-7);
		return new Timestamp(c.getTimeInMillis());
	}
	
	
	/**
	 * 日期转让成字符串格式
	 * @param dateEnd
	 * @return
	 */
	public static  String getDate2Str(Date dateEnd,String format) {
		if(format==null) format="yyyy-MM-dd HH:mm:ss";
		DateFormat format1 = new SimpleDateFormat(format);         
		return format1.format(dateEnd); 
	}
	/**
	 * 字符串 到 日期
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date getStr2Date(String str,String format){
		if(format==null) format="yyyy-MM-dd HH:mm:ss";
		DateFormat f1 = new SimpleDateFormat(format);         
		Date date = null;    
	    try {
			date = f1.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("字符串转化为日期出错");
		}   
		return date;
	}
	/**
	 * 根据数组生成时间
	 * @param arr 年 月 日 时 分 秒 by getDayTimeArray
	 * @return
	 */
	public static Date getDateByArray(Integer []arr){
		Calendar c = Calendar.getInstance();
		c.set(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
		return c.getTime();
	}

	/**
	 * 获取月份
	 * @param dateStart
	 * @return
	 */
	public static  Integer getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m = c.get(Calendar.MONTH);
		return m+1;
	}

	/**
	 * 获取年份
	 * @param dateStart
	 * @return
	 */
	public static  Integer getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m = c.get(Calendar.YEAR);
		return m;
	}
	/**
	 * 获取日期
	 * @param dateStart
	 * @return
	 */
	public static  Integer getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int d = c.get(Calendar.DAY_OF_MONTH);
		return d;
	}
	
	/**
	 * 获取{ 年 月 日 时 分 秒 }
	 * @param date
	 * @return
	 */
	public static Integer[] getArrayByDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DAY_OF_MONTH);
		int hh = c.get(Calendar.HOUR_OF_DAY);
		int mm = c.get(Calendar.MINUTE);
		int ss = c.get(Calendar.SECOND);
		Integer[] ret = {y,m,d,hh,mm,ss};
		return ret;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static  Date getNowTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取星期数,从dateStart开始，dateEnd是第几周
	 * @param planDateStart
	 * @param planDateEnd
	 * @return
	 */
	public static  Integer getWeek(Date dateStart, Date dateEnd) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateStart);
		int week = c.get(Calendar.WEEK_OF_YEAR);//获取是本年的第几周
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(dateEnd);
		int week2 = c2.get(Calendar.WEEK_OF_YEAR);//获取是本年的第几周
		
		return week2-week+1;
	}
	
	/**
	 * 获取星期数,date是今年的第几周
	 * @param date
	 * @return
	 */
	public static  Integer getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week = c.get(Calendar.WEEK_OF_YEAR);//获取是本年的第几周
		return week;
	}

	/**
	 * 获取当前的星期
	 * @param date 1~7 日～六
	 * @return
	 */
	public static Integer getWeekNum(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int week = c.get(Calendar.DAY_OF_WEEK);
		return week;
	}
	/**
	 * 获取当前的星期 
	 * @param date
	 * @return
	 */
	public static String getWeekNameCN(int week) {
		String str[]={"日","一","二","三","四","五","六"};
		return str[week-1];
	}
	/**
	 * 获取这周的日期数组
	 * i[0~6] - (w[1~7]-1)
	 * @param date ,日～六
	 * @return
	 */
	public static Date[] getNowWeekDate(Date date){
		return getWeekDate(date, 0);
	}
	/**
	 * 获取上周的日期数组
	 * i[0~6] - (w[1~7]-1)
	 * @param date ,日～六
	 * @return
	 */
	public static Date[] getBeforeWeekDate(Date date){
		return getWeekDate(date, -7);
	}
	/**
	 * 获取下周的日期数组
	 * i[0~6] - (w[1~7]-1)
	 * @param date ,日～六
	 * @return
	 */
	public static Date[] getNextWeekDate(Date date){
		return getWeekDate(date, 7);
	}
	/**
	 * 获取周的日期数组
	 * i[0~6] - (w[1~7]-1)
	 * @param date ,日～六
	 * @return
	 */
	private static Date[] getWeekDate(Date date,int setp){
		Date[] retd = new Date[7];
		int w = getWeekNum(date);
		for (int i = 0; i < 7; i++) {
			int t = i - w + 1 + setp;
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+t);
			retd[i] = new Timestamp(c.getTimeInMillis());
		}
		return retd;
	}
	/**
	 * 获取这周边界  这周日和下周日
	 * @param date [2]
	 * @return
	 */
	public static Date[] getNowWeekBoundary(Date date){
		return getWeekBoundary(date, 0);
	}
	/**
	 * 获取上周边界  上周日和这周日
	 * @param date [2]
	 * @return
	 */
	public static Date[] getBeforeWeekBoundary(Date date){
		return getWeekBoundary(date, -7);
	}
	/**
	 * 获取下周边界  下周日和下下周日
	 * @param date [2]
	 * @return
	 */
	public static Date[] getNextWeekBoundary(Date date){
		return getWeekBoundary(date, 7);
	}
	/**
	 * 获取本周周一
	 * @param date
	 * @return
	 */
	public static Date getNowWeekMonday(Date date){
		return getWeekBoundary(date, 1)[0];
	}
	/**
	 * 获取周边界  
	 * @param date [2]
	 * @param step
	 * @return
	 */
	private static Date[] getWeekBoundary(Date date, int step){
		Date[] retd= new Date[2];
		int w = getWeekNum(date);
		{
			int t = 0 - w + 1 + step ;
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+t);
			retd[0] = new Timestamp(c.getTimeInMillis());
		}
		{
			int t = 7 - w + 1 + step ;
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+t);
			retd[1] = new Timestamp(c.getTimeInMillis());
		}
		return retd;
	}
	
	/**
	 * 取下一个时间撮
	 * @param now
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date getNextTimeStamp(Date now, int hour , int minute){
		Integer[] arr = getArrayByDate(now);
		arr[3]=hour;
		arr[4]=minute;
		Date after = getDateByArray(arr);
		if(after.getTime() <= now.getTime()){
			Date newnow = nextDay(now, 1);
			Integer[] arr2 = getArrayByDate(newnow);
			arr2[3]=hour;
			arr2[4]=minute;
			return getDateByArray(arr2);
		}else{
			return after;
		}
	}
	
	public static void main(String[] args) {
		Date now = getNowTime();
		Date date = getNextTimeStamp(now, 0, 40);
//		System.out.println(getDate2Str(date, null));
//		System.out.println(date.getTime()-now.getTime());
		
		Date week = getNowWeekMonday(now);
			
			System.out.println(week);
	}
}
