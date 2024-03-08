package com.cg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.proxy.ProductCatalogueProxy;

@RestController
public class OrderController {
	//For make a order use Http Post method
	//here for simplicity i used GET method
	
   @GetMapping("/order/pid/{pid}/quantity/{q}")
	public OrderDetails orderProduct(
			@PathVariable("pid")int pid,
			@PathVariable("q")int quantity) {
	//here make a call to product catalogue api
	   String url="http://localhost:8000/products/"+pid;
	   //ResponseEntity-->Encapsulate Http header & body
	   ResponseEntity<OrderDetails> orderDetails=
	   new RestTemplate().getForEntity(url, OrderDetails.class);
	   OrderDetails order=orderDetails.getBody();
	   
	   order.setPrice((int)order.getPrice()*quantity);
	   order.setQuantity(quantity);
	   return order;
	}
  
   @Autowired
   private RestTemplate restTemplate;
 
   //client side loading ....
   @GetMapping("/orderwithloadbalancer/pid/{pid}/quantity/{q}")
	public OrderDetails orderProductWithLoadBalancing(
			@PathVariable("pid")int pid,
			@PathVariable("q")int quantity) {
	//here make a call to product catalogue api
	   String url="http://product-catalouge/products/"+pid;
	   //ResponseEntity-->Encapsulate Http header & body
	   ResponseEntity<OrderDetails> orderDetails=
	  restTemplate.getForEntity(url, OrderDetails.class);
	   
	   OrderDetails order=orderDetails.getBody();
	  
	   order.setPrice((int)order.getPrice()*quantity);
	   order.setQuantity(quantity);
	   return order;
	}
   @Autowired
   private ProductCatalogueProxy proxy;
   @GetMapping("/feignclient/pid/{pid}/quantity/{q}")
	public OrderDetails feignClient(
			@PathVariable("pid")int pid,
			@PathVariable("q")int quantity ) {
	   OrderDetails order=proxy.retriveProduct(pid);
	   order.setPrice((int)order.getPrice()*quantity);
	   order.setQuantity(quantity);
	   return order;
	}
   @GetMapping("/feignclientForSecurityTest/pid/{pid}/quantity/{q}")
	public OrderDetails feignClientForSecurityTest(
			@PathVariable("pid")int pid,
			@PathVariable("q")int quantity,@RequestHeader("loggedInUser") String username ) {
	   OrderDetails order=proxy.retriveProduct(pid);
	   order.setPrice((int)order.getPrice()*quantity);
	   order.setQuantity(quantity);
	   return order;
	}
}
