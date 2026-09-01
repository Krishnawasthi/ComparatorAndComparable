package com.collection.compatable.sychronousmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Driver {

	public static void main(String[] args) {
		Organization emp1 = new Organization("Krishna", "org123", "IT", 55000.00);
		Organization emp2 = new Organization("Vishnu", "org424", "Sales", 199000.00);
		Organization emp3 = new Organization("Mahesh", "org644", "HR", 32000.00);
		Organization emp4 = new Organization("Mohit", "org345", "R&D", 212034.00);
		Organization emp5 = new Organization("tirupati", "org635", "IT", 46774.00);

		// empId = Key
		// Organization OBJECT == VALUE

		Map<String, Organization> empData = new HashMap<>();
		empData.put(emp1.getEmpID(), emp1);
		empData.put(emp2.getEmpID(), emp2);
		empData.put(emp3.getEmpID(), emp3);
		empData.put(emp4.getEmpID(), emp4);
		empData.put(emp5.getEmpID(), emp5);

		
		 // Retrieve employee using empId
	Organization org  = empData.get("org635");
	
			//System.out.println(org);
			
			
	  //getting details of the employee on behalf on empID
			
	 	
	 System.out.println("emp Name       : " + org.getEmpName() );
	 System.out.println("emp ID         : " + org.getEmpID() );	
	 System.out.println("emp dapartment : " + org.getDepartment() );	
	 System.out.println("emp  salary    : " + org.getSalary() );	
	 
	
		
	}
	
	

}
