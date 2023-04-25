package com.dsa.dsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Utilities {
    public static String toHexString(BigInteger n) {
        return n.toString(16);
    }

    public static byte[] stringToByteArray(String s){
        return s.getBytes();
    }

    public static String byteArrayToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
