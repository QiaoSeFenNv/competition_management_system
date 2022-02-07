package com.qiaose.competitionmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.exception.TipException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TipException.class)
    public R tipException(Exception e) {
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public R exception(Exception e){

        return R.failed(String.valueOf(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        System.out.println(ex.getMessage());
        return R.failed(ex.getMessage());
    }

}
