package com.qiaose.competitionmanagementsystem.entity.dto;

import com.qiaose.competitionmanagementsystem.entity.SysFrontendMenuTable;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 前端需要显示父级pid、名称
 */

@Data
public class SysFrontendDto{


    private String path;

    private String describe;

    private String label;

    private String state;

    private Integer sortValue;

    private String icon;

    private Long parentId;

    private Boolean readOnly;
    //isGeneral
    private Boolean keepAlive;

}
