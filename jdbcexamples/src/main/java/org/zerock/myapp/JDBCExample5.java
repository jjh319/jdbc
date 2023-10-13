package org.zerock.myapp;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


public class JDBCExample5 {

	
	public static void main(String[] args) {
		
		Resource1 res1 = new Resource1();
		Resource2 res2 = new Resource2();
		Resource3 res3 = new Resource3();
		
		
		try ( res1; res2; res3; ) {
			;;
		} catch(Exception e) {
			e.printStackTrace();
		} // try-with-resources

	} // main

} // end class


@Log4j2
@NoArgsConstructor
class Resource1 implements AutoCloseable {

	
	@Override
	public void close() throws Exception {
		log.trace("close() invoked.");
		
	} // close
	
} // end class


@Log4j2
@NoArgsConstructor
class Resource2 implements AutoCloseable {

	
	@Override
	public void close() throws Exception {
		log.trace("close() invoked.");
		
	} // close
	
} // end class


@Log4j2
@NoArgsConstructor
class Resource3 implements AutoCloseable {

	
	@Override
	public void close() throws Exception {
		log.trace("close() invoked.");
		
	} // close
	
} // end class
