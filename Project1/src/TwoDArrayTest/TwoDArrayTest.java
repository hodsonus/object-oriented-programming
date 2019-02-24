package TwoDArrayTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.course.oop.tictactoe.util.TwoDArray;

public class TwoDArrayTest {
	
	TwoDArray testArr;
	
	@Before
	public void setUp() throws Exception {
		testArr = new TwoDArray(2,2,-1);
		System.out.println("before each");
	}
	
	@Test
	public void testInsertVal() {
		String retVal = testArr.insertInt(0, 1, 8);
		assertEquals(retVal, "Success! 8 was inserted.");
	}
	
	@Test
	public void getInt() {
		int val = testArr.getInt(1,1);
		assertEquals(val,-1);
	}
}