package org.dows.printer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @description
*
* @author 
* @date 2023年2月6日 下午4:07:23
*/
@SpringBootApplication(scanBasePackages = {"org.dows.*"})
public class PrinterApplication{
    public static void main(String[] args) {
        SpringApplication.run(PrinterApplication.class, args);
    }
}

