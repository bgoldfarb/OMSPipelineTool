package com.omspipeline.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OMSPipelineApplication {
    public static void main(String[] args) {
    	ApplicationContext ctx = SpringApplication.run(OMSPipelineApplication.class, args);
    	System.out.println("######################################");
    	System.out.println("Spring boot app for OMS Pipeline is now running. Visit http://omspipelinestatus.gapinc.dev/ to find the index.");
    	System.out.println("######################################");
    }
}
