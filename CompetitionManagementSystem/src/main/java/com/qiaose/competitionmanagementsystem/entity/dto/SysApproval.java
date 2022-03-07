package com.qiaose.competitionmanagementsystem.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;
import com.qiaose.competitionmanagementsystem.entity.CompetitionCoefficient;
import com.qiaose.competitionmanagementsystem.entity.CompetitionProgram;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysApproval<T> implements Serializable {


    private Long todoId;

    private CompetitionApproval competitionApproval;

    private T Content;

    private Byte TodoState;

    @JsonIgnore
    private List<CompetitionProgram> competitionProgram;

    private String advice;

    private CompetitionCoefficient competitionCoefficient;

    private static final long serialVersionUID = 1L;
}
