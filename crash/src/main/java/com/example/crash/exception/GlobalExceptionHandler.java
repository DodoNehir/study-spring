package com.example.crash.exception;

import com.example.crash.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 응답값을 @ResponseBody로 내려준다!
@RestControllerAdvice
public class GlobalExceptionHandler {

  // ClientErrorException 이 발생하면 handleClientErrorException 이 호출된다.
  @ExceptionHandler(ClientErrorException.class)
  public ResponseEntity<ErrorResponse> handleClientErrorException(
      ClientErrorException e) {
    return new ResponseEntity<>(
        new ErrorResponse(e.getHttpStatus(), e.getMessage()),
        e.getHttpStatus()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    // e.getMessage()가 너무 자세하고 길어서 다듬어준다.
    String errorMessage = e.getFieldErrors().stream()
        .map(fieldError ->
            (fieldError.getField() + ": " + fieldError.getDefaultMessage()))
        .toList()
        .toString();

    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.BAD_REQUEST, "Required request body is missing"), HttpStatus.BAD_REQUEST);
  }


  // 500 서버 에러 라고만 클라이언트에게 알려준다. 너무 세세히 알려주면 보안적으로도 좋지 않다.
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.internalServerError().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    return ResponseEntity.internalServerError().build();
  }


}
