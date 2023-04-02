package com.example.aes;

import java.nio.charset.StandardCharsets;

public class Other {
     public static boolean checkKeyString(String key){
         if(key.length() != 32){
             return false;
         }
         else{
             return true;
         }
     }

    public static String byteArrayToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }

}
