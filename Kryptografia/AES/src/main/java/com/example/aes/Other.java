package com.example.aes;

import java.nio.charset.StandardCharsets;

public class Other {
    public static boolean checkKeyString(String key) {
        if (key.length() != 32) {
            return false;
        } else {
            return true;
        }
    }

    public static String byteArrayToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }


    public static byte[] stringToByteArray(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static String printByteArrayInHex(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xFF) < 16) {
                String hexString = "0" + Integer.toHexString(byteArray[i] & 0xFF);
                sb.append(hexString.toUpperCase());
            } else {
                String hexString = Integer.toHexString(byteArray[i] & 0xFF);
                sb.append(hexString.toUpperCase());
            }

        }
        return sb.toString();
    }


    public static byte[] convertKeyStringToHexByteArray(String inputString) {
        inputString = inputString.toLowerCase(); // Convert input to lowercase
        byte[] byteArray = new byte[16]; // Create byte array of 16 elements

        // Convert string to byte array
        for (int i = 0; i < inputString.length() && i < 32; i += 2) {
            int hexValue = Integer.parseInt(inputString.substring(i, i + 2), 16);
            byteArray[i / 2] = (byte) hexValue;
        }
        return byteArray;
    }

    public static byte[] convertStringToHexByteArray(String inputString) {
        inputString = inputString.toLowerCase(); // Convert input to lowercase
        int inputLength = inputString.length();
        int byteArrayLength = (int) Math.ceil(inputLength / 2.0);
        byte[] byteArray = new byte[byteArrayLength];

        // Convert string to byte array
        for (int i = 0; i < inputLength; i += 2) {
            int endIndex = Math.min(i + 2, inputLength);
            int hexValue = Integer.parseInt(inputString.substring(i, endIndex), 16);
            byteArray[i / 2] = (byte) hexValue;
        }
        return byteArray;
    }
}