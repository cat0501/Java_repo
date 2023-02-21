package com.itheima;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Component
public class User {
    //public static void main(String[] args) throws ParseException {
    //
    //    String day = "202208024";
    //    ////1、定义转换格式
    //    //SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
    //    //SimpleDateFormat formatter2  = new SimpleDateFormat("yyyyMMdd");
    //    //
    //    ////2、调用formatter2.parse(),将"20211011"转化为date类型  输出为：Mon Oct 11 00:00:00 CST 2021
    //    //Date date = formatter2.parse(day);
    //    ////3、将date类型  (Mon Oct 11 00:00:00 CST 2021)转化为String类型
    //    ////注意现在用的是formatter来做转换,输出为String类型的："2021-10-11"
    //    //String  dString = formatter.format(date);
    //    ////4、将String转化为date，需要注意java.sql.Date.valueOf()函数只能接受参数类型为yyyy-MM-dd类型的
    //    //Date data = java.sql.Date.valueOf(dString);
    //    //System.out.println(dString);
    //    //System.out.println(data);
    //
    //
    //    Calendar calendarEnd = Calendar.getInstance();
    //    calendarEnd.setTime(new Date());
    //
    //    Calendar calendarBeg = Calendar.getInstance();
    //    SimpleDateFormat formatter  = new SimpleDateFormat("yyyyMMdd");
    //    Date data = formatter.parse(day);
    //    calendarBeg.setTime(data);
    //
    //
    //    long milliSecondsEnd = calendarEnd.getTimeInMillis();
    //    long milliSecondsBegin = calendarBeg.getTimeInMillis();
    //    long diff = milliSecondsEnd - milliSecondsBegin;
    //
    //    //两个日期间所差天数
    //    long diffDays = diff / (24 * 60 * 60 * 1000);
    //    System.out.println(diffDays + "--------------------------------------<");
    //    if (diffDays > 90){
    //        System.out.println("时间段需要在三个月以内");
    //    }
    //    //两个日期间所差小时数
    //    //long diffHours = diff / (60 * 60 * 1000);
    //}

    public static void main(String[] args) {
        long intervalDays2 = intervalDays2("20xxx2208250xx");
        System.out.println(intervalDays2);
    }

    private static long intervalDays2(String beginTime) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date());

        Calendar calendarBeg = Calendar.getInstance();
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyyMMdd");
        Date data = null;
        try {
            data = formatter.parse(beginTime);
            calendarBeg.setTime(data);

            long diff = calendarEnd.getTimeInMillis() - calendarBeg.getTimeInMillis();
            return diff / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误");
        }
    }

}
