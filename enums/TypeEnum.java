package com.example.singleton.enums;

/**
 * @author memorykghs
 * @date 2025/4/6
 */
public enum TypeEnum {

    SYNC("sync"),
    DCL("dcl"),
    STATIC("static"),
    ENUMS("enums");

    private final String value;
    TypeEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
