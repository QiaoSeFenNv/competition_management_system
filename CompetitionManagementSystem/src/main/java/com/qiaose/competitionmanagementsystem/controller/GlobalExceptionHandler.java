package com.qiaose.competitionmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.qiaose.competitionmanagementsystem.exception.TipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(value = TipException.class)
//    public R tipException(Exception e) {
//        String msg = e.getMessage();
//        String substring = msg.substring(1, 100);
//        return R.failed(substring);
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    public R exception(Exception e){
//        String msg = e.getMessage();
////        String substring = msg.substring(1, 100);
//        return R.failed(msg);
//    }
//}
