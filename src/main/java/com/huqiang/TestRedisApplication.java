package com.huqiang;

import com.huqiang.util.VirtualCcyKeywordUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestRedisApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(TestRedisApplication.class, args);

//		VirtualCcyKeywordUtil test = (VirtualCcyKeywordUtil) ctx.getBean("virtualCcyKeywordUtil");
//		System.out.println(test.containKeywords("ad"));

	}


}
