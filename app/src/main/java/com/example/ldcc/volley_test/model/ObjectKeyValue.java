package com.example.ldcc.volley_test.model;

public class ObjectKeyValue {
    private String Key;
    private String Value;

    public ObjectKeyValue(String key, String value){
        Key = key;
        Value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
