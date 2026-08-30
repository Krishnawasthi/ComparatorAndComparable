package com.collection.compatable.sortById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

class Product implements Comparable <Product>{

	int productId;
	String ProductName;
	
	Product(int productId, String productName)
	{
		this.productId = productId;
		this.ProductName = productName;
	}

	@Override
	public int compareTo(Product p) {
	int result = Integer.compare(this.productId, p.productId);
		return result;
	}
	
}

public class Ecom {

	public static void main(String[] args) {
		
		Product p1 = new Product(1001, "Iphone19");
		Product p2 = new Product(100, "SamsungS23");
		Product p3 = new Product(99, "GoPro");
		Product p4 = new Product(1230, "DJI mini3");
		Product p5 = new Product(1922, "OShMO Ultra");
		
		List<Product> list = new ArrayList<Product>();
		
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5); 
		
		
		//sorting the list
		
		Collections.sort(list);
		
		
		//iterating the list
		
//		for(Product p : list) 
//		{
//			
//			System.out.println("product Id : " + p.productId + " "+ "product Name : " + p.ProductName );
//		}
				
				
//		for(int i = 0; i<list.size(); i++) {
//			
//			Product p = list.get(i);
//			System.out.println("product Id : " + p.productId + " "+ "product Name : " + p.ProductName );
//			
//		}
		
		ListIterator<Product> ls = list.listIterator();
		
		while(ls.hasNext()) {
			
			Product p = ls.next();
			
			System.out.println("product Id : " + p.productId + " "+ "product Name : " + p.ProductName );
			
		}
		
	

	}

}
