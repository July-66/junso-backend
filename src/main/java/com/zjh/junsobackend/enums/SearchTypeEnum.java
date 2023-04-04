package com.zjh.junsobackend.enums;

public enum SearchTypeEnum {
    USER("用户", "user"),
    POST("帖子", "post"),
    PICTURE("图片", "picture");

    private String name;

    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    SearchTypeEnum(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public static SearchTypeEnum getEnumByType(String type) {
        if (type == null) {
            return null;
        }
        for (SearchTypeEnum searchTypeEnum : SearchTypeEnum.values()) {
            if (searchTypeEnum.type.equals(type)) {
                return searchTypeEnum;
            }
        }
        return null;
    }
}
