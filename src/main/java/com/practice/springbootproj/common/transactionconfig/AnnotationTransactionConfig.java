package com.practice.springbootproj.common.transactionconfig;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * <pre>
 *   어노테이션기반 Transaction 설정을 위한 Configuration
 * </pre>
 */

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class AnnotationTransactionConfig {
  //@EnableTransactionManagement 어노테이션을 등록하면 @Transactional 로 설정이 가능하다.

  private final DataSource dataSource;

  @Bean(name = "txManager")
  public PlatformTransactionManager txManager(){
    return new DataSourceTransactionManager(dataSource);
  }
}
