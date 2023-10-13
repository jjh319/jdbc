package org.zerock.myapp.domain;



import java.sql.Timestamp;

import lombok.Value;

//@Data
@Value   // Value Object(값 객체)
public class Employee { // POJO
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	
//	private Date hireDate;
	private Timestamp hireDate;
	
	private String jobId;
	private Double salary;
	private Double commissionPct;
	private Integer managerId;
	private Integer departmentId;
	
} // end class
