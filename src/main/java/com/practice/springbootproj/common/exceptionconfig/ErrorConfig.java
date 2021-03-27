package com.practice.springbootproj.common.exceptionconfig;

import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;


/**
 * <pre>
 *   WebServerFactoryCustomizer 를 구현하여 서버설정을 할 수 있다.
 *   web.xml 설정에 error page 형태의 설정 뿐만아니라, 서버포트 등 서버 설정관련 구현도 가능하다.
 * </pre>
 */
@Configuration
//web.xml 설정의 error page 형태로 표현하고 싶다면 아래 코드처럼 구현한다.
public class ErrorConfig implements WebServerFactoryCustomizer<ConfigurableJettyWebServerFactory> {


  /**
   * <pre>
   *   ConfigurableJettyWebServerFactory 를 활용해서 WAS 설정을 변경할 수 있다.
   *   factory.addErrorPages() 를 활용해서 ErrorPage 를 생성하거나
   *   factory.setPort() 를 활용해서 서버포트도 설정할 수 있다.
   * </pre>
   * @param factory ConfigurableJettyWebServerFactory
   */
  @Override
  public void customize(ConfigurableJettyWebServerFactory factory) {

    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/common/defaultErrorPage"));//404
    factory.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/common/defaultErrorPage"));//405
    factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/common/defaultErrorPage"));//500
    factory.addErrorPages(new ErrorPage(Exception.class, "/common/defaultErrorPage"));//500
//    factory.setPort(90);

  }
}
