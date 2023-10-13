package org.zerock.myapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

// JUnit version 4 test framework 기반의
// 테스트 클래스는 아래와 같이 작성
// 1. POJO 기반으로 만듭니다.
// 2. 3개의 어노테이션 제공
//    - @Before
//    - @Test
//    - @After

@Log4j2
@NoArgsConstructor
public class JUnit4Template2Tests {  // POJO
	
	private String name;
	private int age;
	
//	public JUnit4TemplateTests(String name, int age) {
//		this.name = name;
//		this.age = age;
//	} // Constructor
	
//	@Before
//	public
//	void setup() { // 전처리
//		log.trace("setup() invoked.");
//		
//	} // setup
	
	
	@Test(timeout = 1000L)
	public
	void testXXX1() { // 단위 테스트 : 1개의 기능 테스트
		log.trace("testXXX1() invoked.");
		
		log.info("OK!!!");
	} // testXXX1
	
	
	@Test
	public
	void testXXX2() throws Exception { // 단위 테스트 : 1개의 기능 테스트
		log.trace("testXXX2() invoked.");
		
	} // testXXX2
	
	
//	@After
//	public
//	void tearDown() {  // 후처리
//		log.trace("tearDown() invoked.");
//		
//	} // tearDown
	
	
} // end class
