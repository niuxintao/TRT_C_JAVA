package test;

import java.util.List;

import org.junit.Test;

import com.fc.exploreAlorgrithm.WidthFirst;
import com.fc.testObject.TestSuite;
import com.fc.tuple.Tuple;


public class TestWidth extends TestCharacterize{
	@Test
	public void testWorkFlow(){
		init();
		WidthFirst width = new WidthFirst(this.tree,this.caseRunner,this.generate);
		width.process();
		TestSuite extra = width.getExtraCases();
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
