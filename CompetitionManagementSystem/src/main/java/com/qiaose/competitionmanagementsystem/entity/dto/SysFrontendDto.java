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

    // path
    private String path;
    // Label
    private String name;
    // component
    private String component;
    // meta describe icon
    private List<String> meta;
    // redirect
    private String redirect;
    //二级
    private List<SysFrontendDto> children;

}
