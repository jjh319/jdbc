package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class JDBCExample1 {
		
	static final String driver = "oracle.jdbc.OracleDriver";
	

	// 1. EZCONNECT
//	static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/seoul";
	
	// 2. TNS Alias
//	static final String jdbcUrl = "jdbc:oracle:thin:@seoul";
//	static final String jdbcUrl = "jdbc:oracle:thin:@seoul?TNS_ADMIN=D:/u01/oracle/product/19.3.0/dbhome/network/admin";
	
	// 3. TNS Alias using Oracle Cloud Wallet
	static final String jdbcUrl = "jdbc:oracle:thin:@atp20191201_high?TNS_ADMIN=C:/opt/OracleCloudWallet/ATP";
	
	static final String user = "HR";
	static final String pass = "Oracle12345678";

	
	public static void main(String[] args)
		throws ClassNotFoundException, SQLException {
		
		Connection conn = null;
//		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		// Step.1 To load the specified JDBC Driver class.
		Class.forName(driver);
		
		// Step.2 To connect to the specified Oracle Instance.
		conn = DriverManager.getConnection(jdbcUrl, user, pass);
		log.info("- conn: " + conn);
		
		// Step.3 To prepare SQL to execute.
//		stmt = conn.createStatement();			// Dynamic SQL
//		log.info("- stmt: " + stmt);
		
		// Step.4 To execute our SQL statement.
//		String sql = "SELECT * FROM employees";	// Dynamic SQL
		
		String sql = "SELECT * FROM employees WHERE salary > ? ORDER BY salary DESC";	// Prepared SQL
		
		pstmt = conn.prepareStatement(sql);		// Prepared SQL
		
		// Bind Variable(?) 의 매개변수 번호는 1부터 시작한다.
		pstmt.setDouble(1, 3000);
		
//		rs = stmt.executeQuery(sql);				// Dynamic SQL 실행
		
		rs = pstmt.executeQuery();					// Prepared SQL 실행
		log.info("- rs: " + rs);
		
		// Step.5 To extract all employees from result set.
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
		
		
		//----------------------------------------//
		// JDBC 에서는 아래의 3개 자원객체를 닫는 순서가 정해져 있음:
		//----------------------------------------//
		//  (1) ResultSet.close()
		//  (2) Statement/PreparedStatement.close()
		//  (3) Connection.close()
		//----------------------------------------//
				
		assert rs != null;
		rs.close();
		
		assert pstmt != null;
		pstmt.close();
		
		assert conn != null;
		conn.close();
	} // main

} // end class
