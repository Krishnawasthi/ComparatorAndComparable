package com.collection.compatable.mapExapmle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExampleWithMap {
	public static void main(String[] args) {

		Map<String, Integer> prodPrice = new HashMap<String, Integer>();

		// Storing 10k+ Products

		prodPrice.put("iPhone14", 145500);
		prodPrice.put("samsung", 166900);
		prodPrice.put("realme pro 5", 22000);
		prodPrice.put("pixel 11", 175500);
		prodPrice.put("motorola g80", 16990);

		// System.out.println(prodPrice);
		System.out.println(
				"-------------------------------- before synchronized the map ------------------------------------");
		Iterator<Map.Entry<String, Integer>> prodP = prodPrice.entrySet().iterator();

		while (prodP.hasNext()) {

			Map.Entry<String, Integer> e = prodP.next();

			System.out.println("Product Name : " + e.getKey() + "| Price : " + e.getValue());

		}

		System.out.println(
				"-------------------------------- after synchronized the map ------------------------------------");

		// MAKING MAP synchronized

		Map<String, Integer> prodPriceSync = Collections.synchronizedMap(prodPrice);
		// after synchronizing the map it will lock the object so no thread can working
		// (Reading or Updating parallerlly).
		// modifiation --> 200 ms ----> 452342 people were trying to buy
		prodPriceSync.put("realme pro 5", 23999);

		// System.out.println(prodPriceSync);

		// iterating the sync Map

		Iterator<Map.Entry<String, Integer>> syncProd = prodPriceSync.entrySet().iterator();

		while (syncProd.hasNext()) {

			Map.Entry<String, Integer> e = syncProd.next();

			System.out.println("Product Name : " + e.getKey() + "| Price : " + e.getValue());

		}
	}

}
