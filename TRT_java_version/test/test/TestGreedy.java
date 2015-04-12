package test;

import java.util.List;

import org.junit.Test;

import com.fc.exploreAlorgrithm.Greedy;
import com.fc.testObject.TestSuite;
import com.fc.tuple.Tuple;

public class TestGreedy extends TestCharacterize{
	@Test
	public void testWorkFlow(){
		init();
		Greedy greedy = new Greedy(tree,caseRunner,generate);
		greedy.process();
		TestSuite extra = greedy.getExtraCases();
		System.out.println("all:"+extra.getTestCaseNum());
		for(int i = 0 ; i < extra.getTestCaseNum(); i++){
			System.out.println(extra.getAt(i).getStringOfTest());
		}
		
		List<Tuple> bugs = tree.getBugModes();
		for(Tuple bug : bugs){
			System.out.println(bug.toString());
		}
		
	}
}
