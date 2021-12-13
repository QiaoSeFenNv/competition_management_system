package com.qiaose.competitionmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.exception.TipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TipException.class)
    public R tipException(Exception e) {
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public R exception(Exception e){

        return R.failed(String.valueOf(e));
    }
}
