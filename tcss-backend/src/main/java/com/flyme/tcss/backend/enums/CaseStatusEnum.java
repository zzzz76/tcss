package com.flyme.tcss.backend.enums;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
public enum CaseStatusEnum {
    WAITING("00", "待测试"),
    TESTING("01", "测试中"),
    SUCCEED("02", "测试成功"),
    FAILED("03", "测试失败"),
    EXCEPTION("04", "测试异常"),
    ;
    private final String code;
    private final String desc;

    CaseStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CaseStatusEnum getItem(String code) {
        for (CaseStatusEnum item: values()) {
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