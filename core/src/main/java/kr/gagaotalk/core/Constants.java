package kr.gagaotalk.core;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public final static byte[] HEADER_STRING_BYTES = "gagaotalk!".getBytes(StandardCharsets.UTF_8);
    public final static Map<String, Object> EMPTY_MAP = new HashMap<>();
}
