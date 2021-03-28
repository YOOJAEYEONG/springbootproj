package com.practice.springbootproj.common.exception.handler;

import com.practice.springbootproj.common.util.AES256Helper;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.syntax.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.ErrorManager;


/**
 * <pre>
 *   DispatcherServlet 이후의 영역에서 공통 Exception 처리를 구현한다.
 *   Exception 별 구분해서 처리 가능하다.
 * </pre>
 * @author yoojaeyeong
 * @since 2021-03-27
 */
@Slf4j(topic = "GlobalExceptionHandler")
@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  AES256Helper AES256Helper;

  /**
   * default 처리 로직이 들어간다.
   * @param e
   * @return {String} 페이지경로
   */
  @ExceptionHandler
  protected String handlerDefault(Exception e){
    log.error("handlerDefault : {}",e);
//    fianl ErrorResponse response = ErrorResponse.of(ErrorCode.INTERVAL_SERVER_ERROR);
    ErrorManager errorManager = new ErrorManager();
//    errorManager.error("exception",e, CefLoadHandler.ErrorCode.ERR_ADDRESS_UNREACHABLE);
    return "/template/common_error";
  }

//  /**
//   * BusinessException 발생시 처리 로직이 들어간다.
//   * @param e
//   * @return ResponseEntity
//   */
//  @ExceptionHandler(BusinessException.class)
//  protected ResponseEntity<ErrorResponse> handlerBusinessException(BusinessException e){
//    log.error("handlerBusinessException:{}",e);
//    final ErrorResponse response =
//        ErrorResponse.of(500,"Invalid Error");
//    return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
//  }

  /**
   *
   * @return ResponseEntity
   */
  @ExceptionHandler(TokenException.class)
  protected ResponseEntity<Object> handlerTokenException(){
    try {

    }catch (Exception e){
      log.error("handlerTokenException",e);
    }

    //암호화 해서 Response 해줘지만 일단 보기 힘드니까
//    return new ResponseEntity<>(AES256Helper.encode(json.toString()),HttpStatus.OK);

    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
