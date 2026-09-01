package com.collection.compatable.sychronousmap;

class Organization {
	private String empName;
	private String empID;
	private String department;
	private double salary;

	public Organization(String empName, String empID, String department, double salary)

	{

		this.empName = empName;
		this.empID = empID;
		this.department = department;
		this.salary = salary;

	}

	public String getEmpName() {

		return empName;
	}

	public String getEmpID() {

		return empID;
	}

	public String getDepartment() {

		return department;

	}

	public double getSalary() {

		return salary;

	}

	public String toString() 
	{
		return "EmpName: " + empName + ", EmployeeId: " + empID + ", Emp Department: "+ department + ", Emp salary: " + salary;

	}

}
