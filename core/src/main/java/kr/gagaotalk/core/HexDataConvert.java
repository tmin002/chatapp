package kr.gagaotalk.core;

import javax.xml.bind.DatatypeConverter;

public class HexDataConvert {
    public static byte[] HexStringToBytes(String hexString) {
        return DatatypeConverter.parseHexBinary(hexString);
    }
    public static String BytesToHexString(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }
}
