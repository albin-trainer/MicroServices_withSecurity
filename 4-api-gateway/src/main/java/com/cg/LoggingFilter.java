package com.cg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
@Component
public class LoggingFilter implements GlobalFilter{
	
	Logger log=LoggerFactory.getLogger(LoggingFilter.class);
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	   log.info("***** In logging filter****");
		return chain.filter(exchange);
	}


	
}
