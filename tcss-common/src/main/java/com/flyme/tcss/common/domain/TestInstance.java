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
 * 测试实例实体类，用于维护各个服务实例的并发资源
 *
 * @author xiaodao
 * @date 2022/12/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestInstance implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String url;

    private Integer taskNum;

    private Integer maxTaskNum;

    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private Short isDeleted;

}
