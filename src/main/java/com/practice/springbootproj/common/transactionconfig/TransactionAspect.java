package com.practice.springbootproj.common.transactionconfig;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;
import java.util.Properties;

@Aspect
@Configuration
@RequiredArgsConstructor
public class TransactionAspect {

  private final PlatformTransactionManager txManager;

  @Bean
  public TransactionInterceptor txAdvice(){
    Properties txAttributes = new Properties();

    RuleBasedTransactionAttribute readOnlyTxAttribute = new RuleBasedTransactionAttribute();
    readOnlyTxAttribute.setReadOnly(true);
    txAttributes.setProperty("select*",readOnlyTxAttribute.toString());


    RuleBasedTransactionAttribute rollbackAttribute = new RuleBasedTransactionAttribute();
    rollbackAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
    txAttributes.setProperty("insert*",rollbackAttribute.toString());
    txAttributes.setProperty("update*",rollbackAttribute.toString());
    txAttributes.setProperty("delete*",rollbackAttribute.toString());
    txAttributes.setProperty("multi*", rollbackAttribute.toString());



    RuleBasedTransactionAttribute noRollbackAttribute = new RuleBasedTransactionAttribute();
    noRollbackAttribute.setRollbackRules(Collections.singletonList(new NoRollbackRuleAttribute(Exception.class)));
    txAttributes.setProperty("noRollback*",noRollbackAttribute.toString());


    TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
    transactionInterceptor.setTransactionManager(txManager);
    transactionInterceptor.setTransactionAttributes(txAttributes);

    return transactionInterceptor;
  }

  @Bean
  public Advisor transactionAdvisorAdvice(){
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    //모든 접근제어시지자 모든 페치지에 있는 서비스구현클래스의 하위메소드를 포인틐컷
    pointcut.setExpression("execution(* *..*.*ServiceImpl.*(..))");
    return new DefaultPointcutAdvisor(pointcut,txAdvice());
  }
}

