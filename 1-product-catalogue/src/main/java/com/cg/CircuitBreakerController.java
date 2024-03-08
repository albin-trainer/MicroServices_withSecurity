package com.cg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
@RestController
public class CircuitBreakerController {
	Logger log=LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/hello")
   // @Retry( name = "default" ) //in default it will for retry 3 times
   // @Retry(name="default",fallbackMethod = "fallBackMethodAnyName")
     @Retry(name="my-api",fallbackMethod = "fallBackMethodAnyName")
    
    //CircuitBreaker ==> keeps try for many times if not worked
    //directly goes to fallback method
    //@CircuitBreaker(name = "my-api",fallbackMethod = "fallBackMethodAnyName")
	public String getData() {
    	log.info("i m trying ....");
    	ResponseEntity<String> resp=
		  new RestTemplate().getForEntity("http://xxxxxxsomefake.com", String.class);
    	 String s=resp.getBody();
		return s;
	}
    public String fallBackMethodAnyName(Exception e) {
    	log.info("fall back method");
    	return "Server down try after some time !!!!";
    }
    }

