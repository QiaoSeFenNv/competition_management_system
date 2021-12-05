package com.qiaose.competitionmanagementsystem.entity.dto;

import com.qiaose.competitionmanagementsystem.entity.CompetitionApproval;
import com.qiaose.competitionmanagementsystem.entity.CompetitionRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysApproval implements Serializable {

    private String rejectReason;

    private Long todoId;

    private CompetitionApproval competitionApproval;

    private CompetitionRecord competitionRecord;

    private static final long serialVersionUID = 1L;
}
