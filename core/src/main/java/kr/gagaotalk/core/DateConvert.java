package kr.gagaotalk.core;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    public static String DateToString(Date date) {
        return format.format(date);
    }
    public static Date StringToDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException ignored) {
            // TODO: this must be unreachable
            return null;
        }
    }
}
