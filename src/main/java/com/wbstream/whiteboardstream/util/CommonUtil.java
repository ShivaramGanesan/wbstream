package com.wbstream.whiteboardstream.util;

import java.util.HashMap;

public class CommonUtil {

    public static HashMap<String, String> getParamMap(String[] keyValue){
        HashMap<String, String> map = new HashMap<>();
        for(String kv : keyValue){
            String[] data = kv.split("=");
            map.put(data[0], data[1]);
        }
        return map;
    }
}
