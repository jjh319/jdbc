package org.zerock.myapp;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
public class JDBCExameples1Tests {
   //JDBC 접속을 위한 필수 4가지 정보를 필드로 선언
   private final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/seoul";
   private final String driverClass = "oracle.jdbc.driver.OracleDriver";
   private final String user = "HR";
   private final String pass = "oracle";
   
   
//   JDBC를 이용한 DB프로그래밍시에 반드시 필요한 3대 변수
//   So, You could use try-with-resources block or @CleanUp annotation of the 'lombok' library
   private Connection conn;      //interface
   private Statement stmt;         //interface
   private ResultSet rs;         //interface
   
   @Before
   public void setup() throws ClassNotFoundException, SQLException {   // 전처리 : Target DB에 대한 연결 생성
      log.trace("setup() invoked.");
      
      //Step.1 Loading the specified JDBC Driver
      Class.forName(this.driverClass);
      
      //Step.2 Trying to connect to the Target database

      this.conn = DriverManager.getConnection(jdbcUrl, user, pass);
      
      
      //Step.3 Assert whether returned connection is null or not
      
//      Objects.requireNonNull(this.conn);
//      assert this.conn != null;
      assertNotNull(this.conn);
//      if(this.conn != null);
      
      log.info("\t + this.conn {} invoked",this.conn);
   } // setup
   
   
   @Test
   public void testJDBC() throws SQLException {
      log.trace("contextLoads() invoked.");
      
      String sql = "SELECT current_date, current_timestamp FROM dual";
      
//      Step.4 Connection 객체로부터, SQL문장을 표현하는 Statement 객체를 얻습니다.
      
      this.stmt = conn.createStatement();
      
//      assert <조건식> 의 의미!
//      지정된 조건식이 true라면, 아무일 없이 그냥 지나가고
//      지정된 조건식이 false라면, AssertError를 발생시킵니다.
      
      
      assert this.stmt != null;
      log.info("\t + stmt : {}", this.stmt);
      
//      Step.5 미리검증된 SQL문장을 , Step4에서 얻어낸 Statement객체를 통해서, 데이터 베이스 서버에 전송시킵니다.
//      그리고, 데이터베이스 서버가 수행시킨 SQL문장의 결과셋 (Result Set)을 받습니다.
      
//      인터페이스      LValue 타입의 구현객체
//      ---------       ------------------------
      this.rs = this.stmt.executeQuery(sql);
      assertNotNull(rs);
      log.info("\t+ this.rs : {}", this.rs);
      
//      Step.5 ResultSet 객체로부터, 출력 결과를 추출하여 출력
      
      while(this.rs.next()) {
         String currDate = rs.getString("current_date");
         String currTimestamp = rs.getString("current_timestamp");
         
         log.info("currDate : {}, currTimestampe : {}", currDate, currTimestamp);
      } // while      
   } // contextLoads
   
   @After
   public void tearDown()  {      // 후처리 Post-Processing - 주로 자원객체를 정리
      log.trace("tearDown() invoked.");
      
//      1st. method: close() method
//      this.rs.close();
//      this.stmt.close();
      
      
      try { 

         if(!this.rs.isClosed() && this.rs != null) {
         this.rs.close();
         } // if

         if(!this.stmt.isClosed() && this.stmt != null) {
         this.stmt.close();
         } // if
         
         if(!this.conn.isClosed() && this.conn != null) {
         this.conn.close();
         } // if
      } 
      catch (SQLException e) {;;} // try-catch(메인에서 사용할 때)
      
////   2st. method : try-with-resources (*Recommend *)
//                     But If all blocks related to the resources are divided
//                    try-with-resources block couldn't be used.
//      try(this.conn){;;}
//      catch(SQLException e){;;}
      
//      3st. method : CleanUp Annotation : Not allowed this location
      
      
   } // tearDown
   
} // end class