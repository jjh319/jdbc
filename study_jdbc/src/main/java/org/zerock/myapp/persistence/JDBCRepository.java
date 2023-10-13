package org.zerock.myapp.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.zerock.myapp.domain.Employee;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor
public class JDBCRepository {
//   private final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/seoul";
   private final String jdbcUrl = "jdbc:oracle:thin:@OCIDB20230830_HIGH?TNS_ADMIN=C:/opt/OracleCloudWallet/OCIDB";

   private final String driverClass = "oracle.jdbc.driver.OracleDriver";
   private final String user = "HR";
   private final String pass = "Oracle12345678";
   
   public List<Employee> selectAllEmployees() throws ClassNotFoundException, SQLException {
      log.trace("printAllEmployees() invoked.");
      
//      Step.1 : JDBC에 필수적인 3대 인터페이스 타입의 변수 선언
      @Cleanup Connection conn = null;
      @Cleanup Statement stmt = null;
      @Cleanup ResultSet rs = null;
      
//      Step.2 Target DB에 대한 Connection 생성
//      Class.forName(this.driverClass);
      conn = DriverManager.getConnection(jdbcUrl, user, pass);
      
//      TCL
//      conn.setAutoCommit(false);
//      conn.commit();
//      conn.rollback();
      
//      Step.3 모든 사원정보를 출력하는 SQL문장 준비 및 수행
      String sql = """
            SELECT *
            FROM employees
            WHERE salary > 5000
            ORDER BY employee_id ASC
            """;
      
//      Step.4 검증된 SQL 문장을 전송 및 수행시키고, 이 SQL문장의 결과집합(ResultSet)을 수신
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      
//      Step.5 얻어낸 결과 Set 으로부터 실제 데이터를 추출/출력
//      rs.next()를 통해, cursor의 위치를 이동시킴, 반복적으로 커서를 그 다음/ 다음/... 행으로 이동시키다가,
//      더이상 이동시킬 행이 없으면, false를 반환
      
      List<Employee> list = new Vector<Employee>();
      
      while(rs.next()) { 
         int employeeId = rs.getInt("EMPLOYEE_ID");
         
         String firstName = rs.getString("FIRST_NAME");
         String lastName = rs.getString("LAST_NAME");
         String email = rs.getString("EMAIL");
         String phoneNumber = rs.getString("PHONE_NUMBER");
         
         Timestamp hireDate = rs.getTimestamp("HIRE_DATE");
//         Timestamp hireDate1 = rs.getTimestamp("HIRE_DATE");
         
         String jobId = rs.getString("JOB_ID");
         
         Double salary = rs.getDouble("SALARY");
         Double commissionPct = rs.getDouble("COMMISSION_PCT");
         
         int managerId = rs.getInt("MANAGER_ID");
         int departmentId = rs.getInt("DEPARTMENT_ID");
         
//         -----
         
         Employee employee =
        		 new Employee(
        		     employeeId,
        		     firstName,
        		     lastName,
        		     email,
        		     phoneNumber,
        		     hireDate,
        		     jobId,
        		     salary,
        		     commissionPct,
        		     managerId,
        		     departmentId
        				 );
        
         
         list.add(employee);
         
         
      } // while
      
      return list;
   } // printAllEmployees
   
   public Integer removeEmployee(Integer employeeId) throws SQLException {
	   
	   @Cleanup
	   Connection conn = DriverManager.getConnection(jdbcUrl,user,pass);
	   conn.setAutoCommit(false);
	   
	   String sql = "DELETE FROM emp WHERE employee_id = ?";
	   
	   @Cleanup
	   PreparedStatement pstmt = conn.prepareStatement(sql);
	   
	   pstmt.setInt(1, employeeId);
	   try {
	   int affectedRows = pstmt.executeUpdate();
	   log.info("\t affectedRows: {}", affectedRows);
	   
	   conn.commit();
	   return affectedRows;
	   } catch(Exception e) {
		   conn.rollback();
		   
		   throw e;
	   }
   } // removeEmployee
   
   
   public Integer insertEmployee(Employee employee) throws SQLException {
	   log.trace("insertEmployee({}) invoked.", employee);
	   
	   Connection conn = DriverManager.getConnection(jdbcUrl,user,pass);
	   
	   String sql = """
	   		INSERT INTO employees
	   		VALUES(?,?,?,?,?,?,?,?,?,?,?)
	   		""";
	   
	   PreparedStatement pstmt = conn.prepareStatement(sql);
	   
	   pstmt.setInt( 1, employee.getEmployeeId());
	   pstmt.setString( 2, employee.getFirstName());
	   pstmt.setString( 3, employee.getLastName());
	   pstmt.setString( 4, employee.getEmail());
	   pstmt.setString( 5, employee.getPhoneNumber());
	   pstmt.setTimestamp( 6, employee.getHireDate());
	   pstmt.setString( 7, employee.getJobId());
	   pstmt.setDouble( 8, employee.getSalary());
	   
	   if(employee.getCommissionPct() != null) {
		   pstmt.setDouble(9, employee.getCommissionPct());
	   } else {
		   pstmt.setNull(9, Types.DOUBLE);
	   } // if-else
	   
	   pstmt.setInt( 10, employee.getManagerId());
	   pstmt.setInt( 11, employee.getDepartmentId());
	   
	   return pstmt.executeUpdate();
	   
   } // insertEmployee
   
   
   public Employee selectEmployee(Integer employeeId) throws SQLException {
	   
	   Connection conn = DriverManager.getConnection(jdbcUrl,user,pass);
	   
	   String sql = """
	   		SELECT *
	   		FROM employees
	   		WHERE employee_id = ?
	   		""";
	   
	   PreparedStatement pstmt = conn.prepareStatement(sql);
	   pstmt.setInt(1, employeeId);
	   
	   ResultSet rs = pstmt.executeQuery();
	   if(rs.next()) {  // 1명의 사원을 반환
//		   	 Integer employeeId = rs.getInt("EMPLOYEE_ID");
	         
	         String firstName = rs.getString("FIRST_NAME");
	         String lastName = rs.getString("LAST_NAME");
	         String email = rs.getString("EMAIL");
	         String phoneNumber = rs.getString("PHONE_NUMBER");
	         
	         Timestamp hireDate = rs.getTimestamp("HIRE_DATE");
//	         Timestamp hireDate1 = rs.getTimestamp("HIRE_DATE");
	         
	         String jobId = rs.getString("JOB_ID");
	         
	         Double salary = rs.getDouble("SALARY");
	         Double commissionPct = rs.getDouble("COMMISSION_PCT");
	         
	         Integer managerId = rs.getInt("MANAGER_ID");
	         Integer departmentId = rs.getInt("DEPARTMENT_ID");
	         
	         Employee employee =
	        		 new Employee(
	        				 employeeId,
	        				 firstName,
	        				 lastName,
	        				 email,
	        				 phoneNumber,
	        				 hireDate,
	        				 jobId,
	        				 salary,
	        				 commissionPct,
	        				 managerId,
	        				 departmentId
	        		 );
	         log.info("\t employee: {}",employee);
	         
	         return employee;
	   } else {  // 0명의 사원을 반환
//		   return null;
		   throw new SQLException("지정된 사번("+ employeeId +")의 사원은 존재하지 않습니다.");
	   } // if-else
	   
   } // selectEmployee
   
   
   public Integer updateEmployee(Employee employee) throws SQLException {
	   
	   Connection conn = DriverManager.getConnection(jdbcUrl,user,pass);
	   
	   String sql = """
	   		UPDATE employees
	   		SET
	   			first_name = ?,
	   			last_name = ?,
	   			email = ?,
	   			phone_number = ?,
	   			hire_date = ?,
	   			job_id = ?,
	   			salary = ?,
	   			COMMISSION_PCT = ?,
	   			MANAGER_ID = ?,
	   			DEPARTMENT_ID = ?
	   		WHERE
	   			employee_id = ?
	   		""";
	   
	   PreparedStatement pstmt = conn.prepareStatement(sql);
	   
	   
	   pstmt.setString( 1, employee.getFirstName());
	   pstmt.setString( 2, employee.getLastName());
	   pstmt.setString( 3, employee.getEmail());
	   pstmt.setString( 4, employee.getPhoneNumber());
	   pstmt.setTimestamp( 5, employee.getHireDate());
	   pstmt.setString( 6, employee.getJobId());
	   pstmt.setDouble( 7, employee.getSalary());
	   
	   if(employee.getCommissionPct() != null) {
		   pstmt.setDouble(8, employee.getCommissionPct());
	   } else {
		   pstmt.setNull(8, Types.DOUBLE);
	   } // if-else
	   
	   pstmt.setInt( 9, employee.getManagerId());
	   pstmt.setInt( 10, employee.getDepartmentId());
	   pstmt.setInt( 11, employee.getEmployeeId());
	   
	   return pstmt.executeUpdate();
	   
   } // updateEmployee
  
   
} // end class

