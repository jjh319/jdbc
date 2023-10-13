package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class JDBCDriverSpyExample {
	
	static final String log4jdbcDriver = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";	
	static final String driver = "oracle.jdbc.OracleDriver";
	

	// 1. EZCONNECT
//	static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@localhost:1521/seoul";
	
	// 2. TNS Alias
//	static final String jdbcUrl = "jdbc:oracle:thin:@seoul";
//	static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@seoul";	// For log4jdbc
//	static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@seoul?TNS_ADMIN=D:/u01/oracle/product/19.3.0/dbhome/network/admin";	// For log4jdbc
	
	// 3. TNS Alias using Oracle Cloud Wallet
	static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@atp20191201_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";	// For log4jdbc
	
	static final String user = "HR";
	static final String pass = "Oracle123456789";

	
	// 우리의 최적의 전략
	public static void main(String[] args) {
		String sql = "SELECT * FROM employees WHERE salary > ? ORDER BY salary DESC";
		
		//-----------------------------------------//
		// 가장 바깥쪽의 try-catch 구문에서는,
		// 자원객체를 생성하고...
		//-----------------------------------------//
		try {
//			Class.forName(driver);				// OK
//			Class.forName(log4jdbcDriver);		// OK
			
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, 7000);
			
			ResultSet rs = pstmt.executeQuery();

			//-----------------------------------------//
			// JAVA11 기준: 자원객체생성을 바깥에서 생성할 수 있게됨
		    // 안쪽에 try-with-resource 구문으로,
            // 자원객체를 자동으로 반드시 닫도록 보장해주자!
			//-----------------------------------------//
            try ( conn; pstmt; rs; ) {
            	
     			while(rs.next()) {
     				int employeeId = rs.getInt("EMPLOYEE_ID");
     				String firstName = rs.getString("FIRST_NAME");
     				String lastName = rs.getString("LAST_NAME");
     				String email = rs.getString("EMAIL");
     				String phoneNumber = rs.getString("PHONE_NUMBER");
     				Timestamp hiteDate = rs.getTimestamp("HIRE_DATE");
//     				String jobId = rs.getString("JOB_ID");
     				String jobId = null;
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
		} // try-catch
		
	} // main

} // end class
