package org.dows.print.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PandDateUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PandDateUtils.class);
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	private static SimpleDateFormat sdf_second = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    private static Calendar startDate = Calendar.getInstance();  
    private static Calendar endDate = Calendar.getInstance();  
//    private static DateFormat df = DateFormat.getDateInstance();  
    private static Date earlydate = new Date();  
    private static Date latedate = new Date();
	
    public static final String format_day = "yyyy-MM-dd";
	
    public static final String format_senconde = "yyyy-MM-dd HH:mm:ss";
    
	public static final String format_minute = "yyyy-MM-dd HH:mm";
	
	/**
	 * 字符串转日期
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parseStrToDate(String strDate,String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date strtodate = null;
		try {
			strtodate = formatter.parse(strDate);
		} catch (ParseException e) {
			logger.error("日期获取异常"+e.getMessage(),e);
		}
		return strtodate;
	}
	/**
	 * 字符串转日期
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(Date date,String pattern) {
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	    Date strtodate = null;
		try {
			String dateStr = formatter.format(date);
			strtodate = formatter.parse(dateStr);
		} catch (ParseException e) {
			logger.error("日期获取异常"+e.getMessage(),e);
		}
	    return strtodate;
	}

	/**
	 * 日期转字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStr(Date date,String pattern) {
	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	    String dateString = formatter.format(date);
	    return dateString;
	}
	
	/** 
     * 计算两个时间相差多少个年 
     *  
     * @param early 
     * @param late 
     * @return 
     * @throws ParseException 
     */  
    public static int yearsBetween(String start, String end) throws ParseException {  
        startDate.setTime(sdf.parse(start));  
        endDate.setTime(sdf.parse(end));  
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));  
    } 
	 /** 
     * 计算两个时间相差多少个月 
     *  
     * @param date1 
     *            <String> 
     * @param date2 
     *            <String> 
     * @return int 
     * @throws ParseException 
     */  
    public static int getMonthSpace(String start, String end) throws ParseException { 
    	int result =  0;
    	try {
    		startDate.setTime(sdf.parse(start));  
    		endDate.setTime(sdf.parse(end));  
    		result = yearsBetween(start, end) * 12 + endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);  
		} catch (Exception e) {
			logger.error(""+e.getMessage(),e);
		}
        return result == 0 ? 1 : Math.abs(result);  
  
    } 
    /** 
     * 计算两个时间相差多少个天 
     *  
     * @param early 
     * @param late 
     * @return 
     * @throws ParseException 
     */  
    public static int daysBetween(String start, String end) throws ParseException {  
    	// 得到两个日期相差多少天  
    	return hoursBetween(start, end) / 24;  
    } 
    public static int daysBetweenSecond(String start, String end) throws ParseException {  
        // 得到两个日期相差多少天  
        return hoursBetweenSecond(start, end) / 24;  
    } 
    /** 
     * 计算两个时间相差多少小时 
     *  
     * @param early 
     * @param late 
     * @return 
     * @throws ParseException 
     */  
    public static int hoursBetween(String start, String end) throws ParseException {  
    	// 得到两个日期相差多少小时  
    	return minutesBetween(start, end) / 60;  
    }  
    public static int hoursBetweenSecond(String start, String end) throws ParseException {  
        // 得到两个日期相差多少小时  
        return minutesBetweenSecond(start, end) / 60;  
    }  
    /** 
     * 计算两个时间相差多少分 
     *  
     * @param early 
     * @param late 
     * @return 
     * @throws ParseException 
     */  
    public static int minutesBetween(String start, String end) throws ParseException {  
    	// 得到两个日期相差多少分  
    	return secondesBetween(start, end) / 60;  
    }  
    public static int minutesBetweenSecond(String start, String end) throws ParseException {  
        // 得到两个日期相差多少分  
        return secondesBetweenSecond(start, end) / 60;  
    }  
  
    /** 
     * 计算两个时间相差多少秒 
     *  
     * @param early 
     * @param late 
     * @return 
     * @throws ParseException 
     */  
    public static int secondesBetween(String start, String end) throws ParseException {  
    	earlydate = sdf.parse(start.toString());  
    	latedate = sdf.parse(end.toString());  
    	startDate.setTime(earlydate);  
    	endDate.setTime(latedate);  
    	// 设置时间为0时  
    	startDate.set(Calendar.HOUR_OF_DAY, 0);  
    	startDate.set(Calendar.MINUTE, 0);  
    	startDate.set(Calendar.SECOND, 0);  
    	endDate.set(Calendar.HOUR_OF_DAY, 0);  
    	endDate.set(Calendar.MINUTE, 0);  
    	endDate.set(Calendar.SECOND, 0);  
    	// 得到两个日期相差多少秒  
    	return ((int) (endDate.getTime().getTime() / 1000) - (int) (startDate.getTime().getTime() / 1000));  
    }  
    public static int secondesBetweenSecond(String start, String end) throws ParseException {  
        earlydate = sdf_second.parse(start.toString());  
        latedate = sdf_second.parse(end.toString());  
        startDate.setTime(earlydate);  
        endDate.setTime(latedate);  
        // 得到两个日期相差多少秒  
        return ((int) (endDate.getTime().getTime() / 1000) - (int) (startDate.getTime().getTime() / 1000));  
    }  

	/**
	 *@param date是为则默认今天日期、可自行设置“2013-06-03”格式的日期
	 *@return  返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
	 */
	public static int getDayofweek(String date){
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(parseStrToDate(date, format_day));
			int weekday = cal.get(Calendar.DAY_OF_WEEK)-1;
			if(weekday==0){
				weekday=7;
			}
			return weekday;
		} catch (Exception e) {
			logger.error("获取周几异常"+e.getMessage(),e);
		}
		return -1;
	 }
	
	/**
	 * 前天,昨天,n小时前,n分钟前, 刚刚     
	 * 相差两天（前天）：2880    相差一天（昨天）：1440   n小时前：    n分钟前：       刚刚：1分钟内   
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getPushishTime(Date startDate,Date endDate){
		String startTime = dateToStr(startDate, format_senconde);
		String endTime = dateToStr(endDate, format_senconde);
		String res = dateToStr(startDate, format_minute);
		try {
			int minutesBetween = minutesBetweenSecond(startTime, endTime);
			System.out.println(minutesBetween);
			if(minutesBetween<1){
				res = "刚刚";
			}else if(minutesBetween>=1 && minutesBetween<60){
				res = minutesBetween+"分钟前";
			}else if(minutesBetween>=60 && minutesBetween<1440){
				res = minutesBetween/60 + "小时前";
			}else if(minutesBetween>=1440 && minutesBetween<2880){
				res = "昨天";
			}else  if(minutesBetween>=2880 && minutesBetween<4320){
				res = "前天";
			}
		} catch (Exception e) {
			logger.error("计算发布时间异常"+e.getMessage(),e);
		}
		return res;
	}
	/**
	 * 前天,昨天,n小时前,n分钟前, 刚刚     --返回不带后缀
	 * 相差两天（前天）：2880    相差一天（昨天）：1440   n小时前：    n分钟前：       刚刚：1分钟内   
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getPushishTimeNoSuffix(Date startDate,Date endDate){
		String startTime = dateToStr(startDate, format_senconde);
		String endTime = dateToStr(endDate, format_senconde);
		String res = dateToStr(startDate, format_minute);
		try {
			int minutesBetween = minutesBetweenSecond(startTime, endTime);
			System.out.println(minutesBetween);
			if(minutesBetween<1){
				res = "0";
			}else if(minutesBetween>=1 && minutesBetween<60){
				res = minutesBetween+"分钟";
			}else if(minutesBetween>=60){
				res = minutesBetween/60 + "小时";
			}
		} catch (Exception e) {
			logger.error("计算发布时间异常"+e.getMessage(),e);
		}
		return res;
	}
	
	/**
     * 将秒数转换为日时分秒，
     * @param second
     * @return
     */
    public static String secondToTime(long second){
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        if(days>0){
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        }else{
            return hours + "小时" + minutes + "分" + second + "秒";
        }
    }
    /**
     * 将日期转换为日时分秒
     * @param date
     * @return
     */
    public static String dateToDayHouMinuteSecondTime(String date){
        SimpleDateFormat format = new SimpleDateFormat(format_senconde);
        try {
            Date oldDate = format.parse(date);
            if(oldDate.before(new Date())){
            	return "已过期";
            }
            long time = oldDate.getTime();                    //输入日期转换为毫秒数
            long nowTime = System.currentTimeMillis();        //当前时间毫秒数
            long second = time-nowTime;                    //二者相差多少毫秒
            second = second / 1000;                            //毫秒转换为妙
            long days = second / 86400;
            second = second % 86400;
            long hours = second / 3600;
            second = second % 3600;
            long minutes = second /60;
            second = second % 60;
            if(days>0){
                return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
            }else{
                return hours + "小时" + minutes + "分" + second + "秒";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据出生日期计算年龄
     * @param birthDayStr
     * @return
     * @throws Exception
     */
    public static  int getAge(String birthDayStr) throws Exception {
    	int age = 0;
    	try {
    		Date birthDay = parseStrToDate(birthDayStr, "yyyy-MM-dd");
    		Calendar cal = Calendar.getInstance(); 
            if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            cal.setTime(birthDay); 
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
            age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                }else{
                    age--;//当前月份在生日之前，年龄减一
                } 
             }
		} catch (Exception e) {
			e.printStackTrace();
		}
         return age; 
    }
    
	/***
     * 判断两个日期时间段是否有交叉
     * @param startDateOne 第一个时间段的开始时间
     * @param endDateOne 第一个时间段的结束时间
     * @param startDateTwo 第二个时间段的开始时间
     * @param endDateTwo 第二个时间段的结束时间
     * @return
     */
    public static Boolean isInterSection(Date startDateOne,Date endDateOne,Date startDateTwo,Date endDateTwo){
    	try {
    		Date maxStartDate = startDateOne;
    		if(maxStartDate.before(startDateTwo)){
    			maxStartDate = startDateTwo;
    		}
    		Date minEndDate = endDateOne;
    		if(endDateTwo.before(minEndDate)){
    			minEndDate = endDateTwo;
    		}
    		if(maxStartDate.before(minEndDate) || (maxStartDate.getTime() == minEndDate.getTime())){
    			return true;
    		} else {
    			return false;
    		}
		} catch (Exception e) {
			logger.error(""+e.getMessage(),e);
		}
    	return false;
    }
	
	public static void main(String[] args) throws Exception{
//		Date date = DateUtils.addHours(new Date(), 3);
//		System.out.println(dateToStr(date, format_senconde));
//		System.out.println(getMonthSpace("2013-11-05 12:22:24","2016-04-30 09:57:01"));
//		System.out.println(daysBetween("2021-08-18","2021-10-08"));
		
		/*String start="2019-11-05 09:21:23";
		String end="2019-11-05 12:22:24";
		Date startDate = parseStrToDate(start, format_senconde); 
		Date endDate = parseStrToDate(end, format_senconde); 
		String res = getPushishTime(startDate, endDate);
		System.out.println(res);// 相差两天（前天）：2880    相差一天（昨天）：1440   n小时前：    n分钟前：       刚刚：     
*/		
		
		//倒计时计算
		String start="2025-06-05 21:42:00";
		String secondToTime = dateToDayHouMinuteSecondTime(start);
		System.out.println(secondToTime);
		
	}
}
