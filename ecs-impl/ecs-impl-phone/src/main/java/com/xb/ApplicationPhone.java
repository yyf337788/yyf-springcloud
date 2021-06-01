package com.xb;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//会自动进行数据源加载，因为没有配置数据源，所以需要剔除加载
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
public class ApplicationPhone {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationPhone.class, args);
    }
}
