package org.zerock.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zerock.myapp.domain.Employee;

import lombok.extern.log4j.Log4j2;



@Log4j2
public class JDBCExamples {
//	 private static final String jdbcUrl = "jdbc:oracle:thin:@OCIDB20230830_HIGH?TNS_ADMIN=C:/opt/OracleCloudWallet/OCIDB";
	
	 
	 private static final String jdbcUrl = "jdbc:log4jdbc:oracle:thin:@OCIDB20230830_HIGH?TNS_ADMIN=C:/opt/OracleCloudWallet/OCIDB";
	 private static final String driverClass = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";
	 private static final String user = "HR";
	 private static final String pass = "Oracle12345678";
	
	public static void main(String[] args) {
		
		
		String sql = """
				SELECT employee_id, last_name, salary
				FROM employees
				WHERE salary > ?
				ORDER BY salary DESC
				""";
		
		try {
			int criteriaSalary = 5000;
			
			Class.forName(driverClass);
			
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, criteriaSalary);
			
			ResultSet rs = pstmt.executeQuery();
			
			List<Employee> list = new ArrayList<>();
			try ( conn; pstmt; rs;) {
				
				while(rs.next()) {
					Integer empId = rs.getInt("employee_id");
					String lastName = rs.getString("last_name");
					Double salary = rs.getDouble("salary");
					
					Employee emp = new Employee(
							empId,
							lastName,
							null,
		       				null,
		       				null,
		       				null,
		       				null,
		       				salary,
		       				null,
		       				null,
		       				null
							);
					list.add(emp);
				} // while
			} // inner try
			
			list.forEach(log::info);
		} catch(SQLException | ClassNotFoundException e) {
			
		}

	} // main

} // end class
