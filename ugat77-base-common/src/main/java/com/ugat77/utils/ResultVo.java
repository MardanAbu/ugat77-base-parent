package com.ugat77.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data  //generate getter and setter automatically
@AllArgsConstructor
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}
