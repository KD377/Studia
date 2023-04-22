package com.dsa.dsa;

import java.math.BigInteger;
public class Utilities {
    public static String toHexString(BigInteger n) {
        return n.toString(16);
    }

    public static byte[] stringToByteArray(String s){
        return s.getBytes();
    }
}
