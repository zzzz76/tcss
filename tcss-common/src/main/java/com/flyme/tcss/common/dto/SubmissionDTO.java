package com.flyme.tcss.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xiaodao
 * @date 2023/1/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SubmissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String url;

    private String input;

    private String output;

    private Long recordId;
}
