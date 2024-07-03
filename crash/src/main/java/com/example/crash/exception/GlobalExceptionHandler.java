package com.example.crash.exception;

import com.example.crash.model.error.ClientErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 응답값을 @ResponseBody로 내려준다!
@RestControllerAdvice
public class GlobalExceptionHandler {

  // ClientErrorException 이 발생하면 handleClientErrorException 이 호출된다.
  @ExceptionHandler(ClientErrorException.class)
  public ResponseEntity<ClientErrorResponse> handleClientErrorException(
      ClientErrorException exception) {
    return new ResponseEntity<>(
        new ClientErrorResponse(exception.getHttpStatus(), exception.getMessage()),
        exception.getHttpStatus()
    );
  }

  // 500 서버 에러 라고만 클라이언트에게 알려준다. 너무 세세히 알려주면 보안적으로도 좋지 않다.
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ClientErrorResponse> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.internalServerError().build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ClientErrorResponse> handleException(Exception e) {
    return ResponseEntity.internalServerError().build();
  }




}
