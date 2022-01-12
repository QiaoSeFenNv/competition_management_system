package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageDto<T> implements Serializable {
    List<T> items;
    Integer total;
}
