package com.leis;

import com.leis.common.log.annotation.EnableLogAspect;
import com.leis.properties.MinioProperties;
import com.leis.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
@EnableLogAspect
@EnableScheduling
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

}
