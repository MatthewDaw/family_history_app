package com.example.sharedcodemodule.hanlders;

import com.google.gson.Gson;

public class deserializer {
    public static <T> T deserialize(String value, Class<T> returnType){
        return (new Gson()).fromJson(value, returnType);
    }
    public static String serialize(Object inputType) {return (new Gson().toJson(inputType));}
}
