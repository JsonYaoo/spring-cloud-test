package com.jsonyao.springcloud.chainAsync;

/**
 * 责任链实现类类型
 */
public enum TypeEnum {

    ICON("1", "图标"),
    CO("2", "合作单位"),
    COURSE("3", "历程");

    private String type;
    private String name;

    TypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }
}
