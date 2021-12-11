package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;

/**
 * 前端需要显示父级pid、名称
 */

@Data
public class SysFrontendDto{

    private Long id;

    private String path;

    private String describe;

    private String component;

    private String label;

    private String state;

    private Integer sortValue;

    private String icon;

    private Long parentId;

    private Boolean readOnly;
    //isGeneral
    private Boolean keepAlive;

}
