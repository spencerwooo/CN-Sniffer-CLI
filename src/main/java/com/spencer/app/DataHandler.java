package com.spencer.app;

import java.nio.charset.StandardCharsets;

class DataHandler {
    String byteStreamToString(byte[] buf) {
//        if (buf == null) {
//            return null;
//        }
//        StringBuilder data = new StringBuilder();
//        for (byte b : buf) {
//            data.append((char) b);
//        }
//        return data.toString();
        return new String(buf, StandardCharsets.UTF_8);
    }
}
