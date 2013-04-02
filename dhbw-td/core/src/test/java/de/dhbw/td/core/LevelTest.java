package de.dhbw.td.core;

import junit.framework.TestCase;

import org.junit.Test;

public class LevelTest extends TestCase {
	

	@Test(expected = ArithmeticException.class)  
	public void test() {  
	  int i = 1/0;
	}  
 

}
