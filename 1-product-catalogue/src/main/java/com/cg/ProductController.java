package com.cg;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ProductController {
	@Autowired
	private Environment env;
	
	@GetMapping("/products/{pid}")
	public Product searchProduct(@PathVariable("pid") int productId) {
		Product p1=new Product(101, "office Chair",10000);
		Product p2=new Product(102, "Bag",1000);
		Product p3=new Product(103, "Laptop",50000);
		Product p4=new Product(104, "Mobile",15000);
		Product p5=new Product(105, "Watch",5000);
		List<Product> plist=Arrays.asList(p1,p2,p3,p4,p5);
	Product product=	plist.stream().filter(p->p.getProductId()==productId).
		findFirst().get();
	int port= Integer.parseInt(env.getProperty("local.server.port"));
	product.setPortNumber(port);
	return product;
	}
	
	@PostMapping("/products")
	public void addProduct(@RequestBody Product p) {
		//service==>DAO==> store in DB
	}
}
