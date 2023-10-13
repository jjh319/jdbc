package org.zerock.myapp.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zerock.myapp.domain.Employee;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class JDBCRepositoryTests {  // POJO
	private JDBCRepository jdbcRepo;
	
	@Before
	public void setup() {
		log.trace("setup() invoked.");
		
		this.jdbcRepo = new JDBCRepository();
		
		Objects.requireNonNull(this.jdbcRepo);
		log.info("\t this.jdbcRepo: {}", this.jdbcRepo);
		
	} // setup
	
	
	@Test(timeout = 3000L)
	public void testPrintAllEmployees()
			throws ClassNotFoundException, SQLException {
		
		log.trace("testPrintAllEmployees() invoked.");
		
		List<Employee> list = this.jdbcRepo.selectAllEmployees();
		
		assertNotNull(list);
//		list.forEach(log::info); 
		
//		System.out.println(list);
		
//		for(Employee emp : list) {
//			System.out.println(emp);
//		} // enhanced for
		
//		Iterator<Employee> iterator = list.iterator();
//		while(iterator.hasNext()) {
//			Employee employee = iterator.next();
//			System.out.println(employee);
//		} // while
		
		
//		list.forEach( System.out::println );   // 메소드 참조
		
		list.forEach( log::info );
	} // testPrintAllEmployees
	
	@Test(timeout = 1000L)
	public void testRemoveEmployee() throws SQLException {
		
		Integer empId = 101;
		int affectedRows = this.jdbcRepo.removeEmployee(empId);
		
		
		Assert.assertEquals(1, affectedRows);
		log.info("\t affectedRows: {}", affectedRows);
	} // testRemoveEmployee
	
	
	@Test(timeout = 1000L)
	public void testSelectEmployee() throws SQLException {
		
		int empId = 100;
		
		Employee emp = this.jdbcRepo.selectEmployee(empId);
		
		assertNotNull(emp);
		log.info("\t emp: {}", emp);
				
	} // testSelectEmployee
	
	@Test(timeout = 3000L)
	public void testinsertEmployee() throws SQLException {
		
		Employee emp = 
				new Employee(
					 777,
       				 "wlgns",
       				 "Jang",
       				 "scv2122",
       				 "010.2210.1462",
       				 new Timestamp(new Date().getTime()),
       				 "IT_PROG",
       				 33333.0,
       				 null,
       				 100,
       				 50
						);
		
		int affectedRows = this.jdbcRepo.insertEmployee(emp);
		
		assertEquals(1, affectedRows);
		log.info("\t affectedRows: {}", affectedRows);
		
	} // testinsertEmployee
	
	@Test(timeout = 3000L)
	public void testUpdateEmployee() throws SQLException {
		
		Employee emp = 
				new Employee(
					 777,
       				 "wlgns",
       				 "Jang",
       				 "scv2122",
       				 "010.2210.1462",
       				 new Timestamp(new Date().getTime()),
       				 "IT_PROG",
       				 77777.0,
       				 null,
       				 100,
       				 50
						);
		
		int affectedRows = this.jdbcRepo.updateEmployee(emp);
		
		assertEquals(1, affectedRows);
		log.info("\t affectedRows: {}", affectedRows);
		
	} // testUpdateEmployee
	
	
} // end class
