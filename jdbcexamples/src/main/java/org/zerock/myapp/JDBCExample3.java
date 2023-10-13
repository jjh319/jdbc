package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class JDBCExample3 {
	
	static final String driver = "oracle.jdbc.OracleDriver";
	
	
	// 1. EZCONNECT
	//static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/seoul";
	
	// 2. TNS Alias
	//static final String jdbcUrl = "jdbc:oracle:thin:@seoul";
	//static final String jdbcUrl = "jdbc:oracle:thin:@seoul?TNS_ADMIN=D:/u01/oracle/product/19.3.0/dbhome/network/admin";
	
	// 3. TNS Alias using Oracle Cloud Wallet
	static final String jdbcUrl = "jdbc:oracle:thin:@atp20191201_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	
	static final String user = "HR";
	static final String pass = "Oracle12345678";

	
	// try-with-resources 구문을 이용해서, 자동으로 자원객체를 반드시 닫아주도록 하자!!!
	public static void main(String[] args) {
				
		String sql = "SELECT * FROM employees WHERE salary > ? ORDER BY salary DESC";
		
		try ( /* JAVA 8 기준: 이 소괄호 안에서 자원객체를 생성해야 합니다. */  
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			log.info("- conn: " + conn);
			log.info("- pstmt: " + pstmt);
			
			pstmt.setDouble(1, 3000);
			
			try (ResultSet rs = pstmt.executeQuery();) {
				log.info("- rs: " + rs);
				
				while(rs.next()) {
					int employeeId = rs.getInt("EMPLOYEE_ID");
					String firstName = rs.getString("FIRST_NAME");
					String lastName = rs.getString("LAST_NAME");
					String email = rs.getString("EMAIL");
					String phoneNumber = rs.getString("PHONE_NUMBER");
					Timestamp hiteDate = rs.getTimestamp("HIRE_DATE");
					String jobId = rs.getString("JOB_ID");
					double salary = rs.getDouble("SALARY");
					double commissionPct = rs.getDouble("COMMISSION_PCT");
					int managerId = rs.getInt("MANAGER_ID");
					int departmentId = rs.getInt("DEPARTMENT_ID");
					
					//------------------------------------//
					
					String employee = String.format(
								"%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
								employeeId, firstName, lastName, email, phoneNumber,
								hiteDate, jobId, salary, commissionPct, managerId, departmentId);
					
					log.info(employee);
				} // while
				
			} // try-with-resources
		
		} catch(SQLException e) { 
			e.printStackTrace();
		} // try-with-resources
	} // main

} // end class
