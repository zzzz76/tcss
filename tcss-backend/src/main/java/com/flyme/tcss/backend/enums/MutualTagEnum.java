package com.flyme.tcss.backend.enums;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
public enum MutualTagEnum {
    SOUTH("00", "南"),
    NORTH("01", "北"),
    WEST("02", "西"),
    EAST("03", "东"),
            ;
    private final String code;
    private final String desc;

    MutualTagEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MutualTagEnum getItem(String code) {
        for (MutualTagEnum item: values()) {
            if (item.code.equals(code)) {
                return item;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return code;
    }
}
