package org.zerock.myapp;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class App {
	
	
    public static void main( String[] args )  {
    	Resource1 rs1 = new Resource1();
    	Resource2 rs2 = new Resource2();
    	Resource3 rs3 = new Resource3();
    	
        try(rs1; rs2; rs3;) {
        	;;
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
    } // main
    
} // end class


@Log4j2
@NoArgsConstructor
class Resource1 implements AutoCloseable {
	
	@Override
	public void close() throws Exception {
		log.trace(">>>>> close() invoked.");
		
	} // close
	
} // end class


@Log4j2
@NoArgsConstructor
class Resource2 implements AutoCloseable {
	
	@Override
	public void close() throws Exception {
		log.trace(">>>>> close() invoked.");
		
	} // close
	
} // end class


@Log4j2
@NoArgsConstructor
class Resource3 implements AutoCloseable {
	
	@Override
	public void close() throws Exception {
		log.trace(">>>>> close() invoked.");
		
	} // close
	
} // end class
