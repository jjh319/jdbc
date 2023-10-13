package org.zerock.myapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

public class AppTest {
	
	
	@Before
	public void setup() {						// should be public
		log.trace("setup() invoked.");
		
	} // setup
	
	

	@Test
	public void contextLoads() {				// should be public
		log.trace("contextLoads() invoked.");
		
	} // testXXX
	
	
	@After
	public void tearDown() {					// should be public
		log.trace("tearDown() invoked.");
		
	} // tearDown

} // end class
