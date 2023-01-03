package com.flyme.tcss.common.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试用例实体类，规定了测试接口的输入输出，用于检测测试接口是否能正常工作
 *
 * @author xiaodao
 * @date 2022/12/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCase implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String url;

    private String input;

    private String output;

    private Long casePre;

    private String mutualTag;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private Short isDeleted;

}
