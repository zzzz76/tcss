package com.flyme.tcss.backend.enums;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
public enum CaseTypeEnum {
    NORMAL("00", "普通类型"),
    SOUTH("01", "南极"),
    NORTH("02", "北极"),
    WEST("03", "西极"),
    EAST("04", "东极"),
            ;
    private final String code;
    private final String desc;

    CaseTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CaseTypeEnum getItem(String code) {
        for (CaseTypeEnum item: values()) {
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
