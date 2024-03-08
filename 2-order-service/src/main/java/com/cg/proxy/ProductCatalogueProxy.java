package com.cg.proxy;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cg.OrderDetails;
@FeignClient(name = "productcatalogue", url = "http://localhost:8000")
//@FeignClient(name = "product-catalouge")
public interface ProductCatalogueProxy {
	@GetMapping("/products/{pid}")
  public OrderDetails retriveProduct( @PathVariable("pid") int pid);
}
