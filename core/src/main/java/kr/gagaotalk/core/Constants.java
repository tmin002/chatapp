package kr.gagaotalk.core;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public final static byte[] HEADER_STRING_BYTES = "gagaotalk!".getBytes(StandardCharsets.US_ASCII);
    public final static Map<String, Object> EMPTY_MAP = new HashMap<>();
    public final static Type JSON_PARSE_TYPE = new TypeToken<Map<String,Object>>(){}.getType();
}
