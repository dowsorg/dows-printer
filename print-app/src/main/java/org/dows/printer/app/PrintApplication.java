package org.dows.printer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @description
*
* @author 
* @date 2023年1月2日 下午2:04:57
*/
@SpringBootApplication(scanBasePackages = {"org.dows.*"})
public class PrintApplication{
    public static void main(String[] args) {
        SpringApplication.run(PrintApplication.class, args);
    }
}

