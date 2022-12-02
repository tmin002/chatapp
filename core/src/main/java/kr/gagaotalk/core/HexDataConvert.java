package kr.gagaotalk.core;
import java.math.BigInteger;

public class HexDataConvert {
    public static byte[] HexStringToBytes(String str) {
        return new BigInteger(str, 16).toByteArray();
    }
    public static String BytesToHexString(byte[] bytes) {
        return new BigInteger(bytes).toString();
    }
}
