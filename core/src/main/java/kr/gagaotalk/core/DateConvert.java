package kr.gagaotalk.core;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert {
    private static SimpleDateFormat format;

    public static String DateToString(Date date) {
        return format.format(date);
    }
    public static Date StringToDate(String date) throws ParseException {
        return format.parse(date);
    }
}
