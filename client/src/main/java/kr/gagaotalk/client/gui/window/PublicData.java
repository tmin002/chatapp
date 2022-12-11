package kr.gagaotalk.client.gui.window;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.gagaotalk.core.Constants;


public class PublicData {
    private static String date;
    private static String time;
    private static String data[] = new String[3];
//    public static Date void setCurrentTime() {
//        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
//        SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
//        SimpleDateFormat time_format = new SimpleDateFormat("HH");
//
//        date = date_format.format(date_now).toString();
//        time = time_format.format(date_now).toString();
//
//        int timeSet = Integer.parseInt(time) - 1;
//        time = Integer.toString(timeSet);
//        time = String.format("%02d", timeSet);
//        time = time + "00";
//    }
    
    public PublicData() throws IOException {
        // 1. URL을 만들기 위한 StringBuilder.
        Date date_now = new Date(System.currentTimeMillis()); // 현재시간을 가져와 Date형으로 저장한다
        SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat time_format = new SimpleDateFormat("HH");

        date = date_format.format(date_now).toString();
        time = time_format.format(date_now).toString();

        int timeSet = Integer.parseInt(time) - 1;
        time = Integer.toString(timeSet);
        time = String.format("%02d", timeSet);
        time = time + "00";
        String dataType = "JSON";
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
        // 2. 오픈 API의요청 규격에 맞는 파라미터 생성, 발급받은 인증키.
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=dYLEfE8lYJtPSDTpLXh02LoQit3HQbZKrnpZjreaaBVNwnTkA3nq2C%2BZIIcEo%2BPwm6LX9CyT1uhkK0vbe4hWNQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*XML 또는 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("62", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("124", "UTF-8")); /*예보지점 Y 좌표*/
//        urlBuilder.append("&" + URLEncoder.encode("cond[country_nm::EQ]","UTF-8") + "=" + URLEncoder.encode("가나", "UTF-8")); /*한글 국가명*/
//        urlBuilder.append("&" + URLEncoder.encode("cond[country_iso_alp2::EQ]","UTF-8") + "=" + URLEncoder.encode("GH", "UTF-8")); /*ISO 2자리코드*/
        // 3. URL 객체 생성.
        URL url = new URL(urlBuilder.toString());
        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 5. 통신을 위한 메소드 SET.
        conn.setRequestMethod("GET");
        // 6. 통신을 위한 Content-type SET.
        conn.setRequestProperty("Content-type", "application/json");
        // 7. 통신 응답 코드 확인.
        System.out.println("Response code: " + conn.getResponseCode());
        // 8. 전달받은 데이터를 BufferedReader 객체로 저장.
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        // 10. 객체 해제.
        rd.close();
        conn.disconnect();
//        Gson gson = new Gson();
//        Map<String, Object> parsed = gson.fromJson(sb.toString(), Constants.JSON_PARSE_TYPE);
//        System.out.println(parsed.get("response"));
        // 11. 전달받은 데이터 확인.
        String result= sb.toString();
        System.out.println(sb.toString());

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(result);
        JsonObject parse_response = (JsonObject) obj.get("response");
        JsonObject parse_body = (JsonObject) parse_response.get("body");
        JsonObject parse_items = (JsonObject) parse_body.get("items");
        JsonArray parse_item = (JsonArray) parse_items.get("item");

        JsonObject weather;
        String nextTime = null;
        System.out.println(parse_item);
        for(int i = 0 ; i < parse_item.size(); i++) {
            weather = (JsonObject) parse_item.get(i);
            if(i == 0) {
                nextTime = weather.get("fcstTime").getAsString();
            }

            if(weather.get("fcstTime").getAsString().compareTo(nextTime) == 0) {
                if(weather.get("category").getAsString().compareTo("T1H") == 0) {
                    data[0] = weather.get("fcstValue").getAsString();
                }
                else if(weather.get("category").getAsString().compareTo("SKY") == 0) {
                    data[1] = weather.get("fcstValue").getAsString();
                }
                else if(weather.get("category").getAsString().compareTo("PTY") == 0) {
                    data[2] = weather.get("fcstValue").getAsString();
                }

            }

        }
        System.out.println(data[0]);
        System.out.println(data[1]);
        System.out.println(data[2]);

    }
}
