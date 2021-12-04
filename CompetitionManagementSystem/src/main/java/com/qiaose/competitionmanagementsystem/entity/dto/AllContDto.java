package com.qiaose.competitionmanagementsystem.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
public class AllContDto implements Serializable {
    private Integer id;

    /**
     * 标题
     */
    private String title;


    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;


    @JsonIgnore
    @JSONField(serialize = false)
    private Integer authorId;

    /**
     * 状态
     */
    private String status;

    @JsonIgnore
    @JSONField(serialize = false)
    private String tags;
    @JsonIgnore
    @JSONField(serialize = false)
    private String categories;

    private Integer hits;

    private static final long serialVersionUID = 1L;
}
