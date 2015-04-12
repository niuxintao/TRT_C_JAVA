package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;
import com.fc.tuple.Tuple;
public class TestTuple {
	@Test
	public void testChildAndFather() {
		TestCase testCase = new TestCaseImplement();
		((TestCaseImplement)testCase).setTestCase(new int[]{1 , 2 ,3,1 ,2});
		
		Tuple tuple = new Tuple(3,testCase);
		tuple.set(0, 1);
		tuple.set(1,2);
		tuple.set(2, 4);
		
		Tuple child = new Tuple(2,testCase);
		child.set(0, 1);
		child.set(1,2);
		
		Tuple father = new Tuple(4,testCase);
		father.set(0, 0);
		father.set(1,1);
		father.set(2, 2);
		father.set(3,4);
		
		assertEquals(tuple.getAllChilds().get(0),child);
		assertEquals(tuple.getAllChilds().size(),6);
		
/*		for(Tuple childs: tuple.getAllChilds()){
			System.out.println(childs.toString());
		}
		for(Tuple fathers: tuple.getAllFathers()){
			System.out.println(fathers.toString());
		}*/
		
		assertEquals(father,tuple.getAllFathers().get(0));
		assertEquals(3,tuple.getAllFathers().size());
	}
	@Test
	public void testCat(){
		TestCase testCase = new TestCaseImplement();
		((TestCaseImplement)testCase).setTestCase(new int[]{1 , 2 ,3,1 ,2});
		
		Tuple A = new Tuple(2,testCase);
		A.set(0, 1);
		A.set(1,2);
		
		Tuple B = new Tuple(2,testCase);
		B.set(0, 0);
		B.set(1,4);
		
		Tuple C = new Tuple(4,testCase);
		C.set(0, 0);
		C.set(1,1);
		C.set(2,2);
		C.set(3,4);
		
		assertEquals(A.cat(A, B),C);
		
	}
	
	@Test
	public void testRevserse(){
		TestCase testCase = new TestCaseImplement();
		((TestCaseImplement)testCase).setTestCase(new int[]{1 , 2 ,3,1 ,2});
		
		Tuple A = new Tuple(2,testCase);
		A.set(0, 1);
		A.set(1,2);

		
		Tuple B = new Tuple(3,testCase);
		B.set(0, 0);
		B.set(1,3);
		B.set(2,4);
		
		assertEquals(B,A.getReverseTuple());
		
	}
}
