package com.qiaose.competitionmanagementsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysChangePass {

    private String newPas;
    private String email;
    private String verification;
    private String originPas;
}
