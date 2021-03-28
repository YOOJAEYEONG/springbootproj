package com.practice.springbootproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.ASPECTJ)
//@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)

//최상위 패키지에 있는 클래스에 Annotation을 적용해서 AOP를 찾을 수 있게 해준다.
@EnableAspectJAutoProxy

@SpringBootApplication(scanBasePackages = "com.practice.springbootproj.*")
public class SpringbootprojApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootprojApplication.class, args);
	}

}
