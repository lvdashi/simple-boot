package com.ljh.api;

import lombok.Data;

import java.util.HashMap;

/**
 * 公用返回
 * @Author: lvjinhui
 */
@Data
public final class ApiResponse<T> {

  private Integer code;
  private String errorMsg;
  private T data;

  private ApiResponse(int code, String errorMsg, T data) {
    this.code = code;
    this.errorMsg = errorMsg;
    this.data = data;
  }
  
  public static ApiResponse ok() {
    return new ApiResponse(0, "", new HashMap<>());
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse(0, "", data);
  }

  public static ApiResponse error(String errorMsg) {
    return new ApiResponse(500, errorMsg, new HashMap<>());
  }

  public static ApiResponse tokenErr(String errorMsg) {
    return new ApiResponse(415, errorMsg, new HashMap<>());
  }
}
