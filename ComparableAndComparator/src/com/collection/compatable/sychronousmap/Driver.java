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

		
//		 // Retrieve employee using empId
//	Organization org  = empData.get("org635");
//	//getting details of the employee on behalf on empID(details on perticular empid)0
//	 System.out.println("emp Name       : " + org.getEmpName() );
//	 System.out.println("emp ID         : " + org.getEmpID() );	
//	 System.out.println("emp dapartment : " + org.getDepartment() );	
//	 System.out.println("emp  salary    : " + org.getSalary() );	
//			
	 
	 
	Iterator<Map.Entry <String, Organization>> itr = empData.entrySet().iterator();
		
	while(itr.hasNext())
	{
		
		  Map.Entry<String, Organization> org =  itr.next();
		  
		  //getting key from here 
		  System.out.println("Employee ID    : " + org.getKey());
		  
		  
		  //getting values  from here
		  Organization emp = org.getValue();
		  
		  System.out.println("Emp Name       : " + emp.getEmpName());
		  System.out.println("Emp Deaprtment : " + emp.getDepartment());
		  System.out.println("Emp Salary     : " + emp.getSalary());
		 
		  System.out.println("-------------------------------------------------------------------------");
		
	}
	}
	
	

}
