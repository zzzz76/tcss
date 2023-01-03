package com.flyme.tcss.backend.enums;

/**
 * @author xiaodao
 * @date 2022/12/22
 */
public enum RecordStatusEnum {
    PENDING("00", "待测试"),
    RUNNING("01", "测试中"),
    SUCCEED("02", "测试成功"),
    FAILED("03", "测试失败"),
    EXCEPTION("04", "测试异常"),
    ;
    private final String code;
    private final String desc;

    RecordStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RecordStatusEnum getItem(String code) {
        for (RecordStatusEnum item: values()) {
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