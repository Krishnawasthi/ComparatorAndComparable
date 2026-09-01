package com.collection.compatable.sychronousmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Driver {

	public static void main(String[] args) {
		Organization emp1 = new Organization("Krishna", "org123", "IT", 55000.00);
		Organization emp2 = new Organization("Vishnu", "org424", "Sales", 199000.00);
		Organization emp3 = new Organization("Mahesh", "org644", "HR", 32000.00);
		Organization emp4 = new Organization("Mohit", "org345", "R&D", 212034.00);
		Organization emp5 = new Organization("tirupati", "org635", "IT", 46774.00);
		Organization emp6 = new Organization("Rahul", "org756", "Finance", 68500.00);
        Organization emp7 = new Organization("Ananya", "org821", "Marketing", 92500.00);
        Organization emp8 = new Organization("Rohit", "org936", "Engineering", 125000.00);
        Organization emp9 = new Organization("Sneha", "org247", "Operations", 73500.00);
        Organization emp10 = new Organization("Amit", "org518", "Management", 150000.00);
		

		
		// empId = Key
		// Organization OBJECT == VALUE
        
		Map<String, Organization> empData = new HashMap<>();
		empData.put(emp1.getEmpID(), emp1);
		empData.put(emp2.getEmpID(), emp2);
		empData.put(emp3.getEmpID(), emp3);
		empData.put(emp4.getEmpID(), emp4);
		empData.put(emp5.getEmpID(), emp5);
		empData.put(emp6.getEmpID(), emp6);
		empData.put(emp7.getEmpID(), emp7);
		empData.put(emp8.getEmpID(), emp8);
		empData.put(emp9.getEmpID(), emp9);
		empData.put(emp10.getEmpID(), emp10);

		
//		 // Retrieve employee using empId
//	Organization org  = empData.get("org635");
//	//getting details of the employee on behalf on empID(details on perticular empid)0
//	 System.out.println("emp Name       : " + org.getEmpName() );
//	 System.out.println("emp ID         : " + org.getEmpID() );	
//	 System.out.println("emp dapartment : " + org.getDepartment() );	
//	 System.out.println("emp  salary    : " + org.getSalary() );
		
		
//			/creating the list to perform comparator 
	 List<Organization> list = new ArrayList<>(empData.values());
	
	 
	 
	 list.sort(new SortById());
	 
	 //iterating the map
	 
//	Iterator<Map.Entry <String, Organization>> itr = empData.entrySet().iterator();
//		
//	while(itr.hasNext())
//	{
//		
//		  Map.Entry<String, Organization> org =  itr.next();
//		  
//		  //getting key from here 
//		  System.out.println("Employee ID    : " + org.getKey());
//		  
//		  
//		  //getting values  from here
//		  Organization emp = org.getValue();
//		  
//		  System.out.println("Emp Name       : " + emp.getEmpName());
//		  System.out.println("Emp Deaprtment : " + emp.getDepartment());
//		  System.out.println("Emp Salary     : " + emp.getSalary());
//		 
//		  System.out.println("-------------------------------------------------------------------------");
//		
//	              }
	 
	 
	 //iterating the list
	 
	 Iterator<Organization> itr = list.iterator();
	  
	 while(itr.hasNext())
	 {
		 
		Organization emp =  itr.next();
		
		 System.out.println("Emp Name       : " + emp.getEmpName());
		 System.out.println("Emp EMPID      : " + emp.getEmpID());
         System.out.println("Emp Deaprtment : " + emp.getDepartment());
	     System.out.println("Emp Salary     : " + emp.getSalary());
	     
	     System.out.println("-------------------------------------------------------------------------");
	 }
	 
	 
	}
	
	

}
