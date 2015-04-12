package test;

import org.junit.Test;

import com.fc.model.TreeStruct;
import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;
import com.fc.testObject.TestSuite;
import com.fc.testObject.TestSuiteImplement;
import com.fc.tuple.Tuple;

public class TestTreeStruct {
	@Test
	public void testTree(){
		int[] wrong = new int[]{1,2,1,2};
		int[] pass = new int[]{0,0,0,0};
		TestCase rightCase = new TestCaseImplement();
		((TestCaseImplement)rightCase).setTestCase(pass);
		TestCase wrongCase = new TestCaseImplement();
		((TestCaseImplement)wrongCase).setTestCase(wrong);
		
		TestSuite rightSuite = new TestSuiteImplement();
		rightSuite.addTest(rightCase);
		TestSuite wrongSuite = new TestSuiteImplement();
		wrongSuite.addTest(wrongCase);

		
		TreeStruct tree = new TreeStruct(wrongCase,rightSuite);
		tree.constructTree();
		tree.init();
		
		for(Tuple tuple : tree.getUntestedTuple()){
			System.out.println(tuple.toString());
		}
	}
}
