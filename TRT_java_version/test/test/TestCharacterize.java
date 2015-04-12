package test;

import java.util.List;

import org.junit.Test;

import com.fc.caseRunner.CaseRunner;
import com.fc.caseRunner.CaseRunnerWithBugInject;
import com.fc.model.CharacterWorkMachine;
import com.fc.model.TreeStruct;
import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;
import com.fc.testObject.TestSuite;
import com.fc.testObject.TestSuiteImplement;
import com.fc.tuple.CorpTupleWithTestCase;
import com.fc.tuple.Tuple;

public class TestCharacterize {
	protected CaseRunner caseRunner;
	protected CorpTupleWithTestCase generate;
	protected TreeStruct tree;
	
	@Test
	public void testWorkFlow(){
		init();
		CharacterWorkMachine workMachine = new CharacterWorkMachine(tree,caseRunner,generate);	
		workMachine.process();	
		
		System.out.println("begin");
		List<Tuple> bugs = tree.getBugModes();
		for(Tuple bug : bugs){
			System.out.println(bug.toString());
		}
		System.out.println("done");
		
		TestSuite extra = workMachine.getExtraCases();
		for(int i = 0 ; i < extra.getTestCaseNum(); i++){
			System.out.println(extra.getAt(i).getStringOfTest());
		}
		
	}

	protected void init() {
		int[] wrong = new int[]{1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1};
		int[] pass = new int[]{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0};
		TestCase rightCase = new TestCaseImplement();
		((TestCaseImplement)rightCase).setTestCase(pass);
		TestCase wrongCase = new TestCaseImplement();
		((TestCaseImplement)wrongCase).setTestCase(wrong);
		
		TestSuite rightSuite = new TestSuiteImplement();
		rightSuite.addTest(rightCase);
		TestSuite wrongSuite = new TestSuiteImplement();
		wrongSuite.addTest(wrongCase);
		
		
		int[] param = new int[]{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3};
		
		Tuple bugModel = new Tuple(3,wrongCase);
		bugModel.set(0, 0);
		bugModel.set(1, 2);
		bugModel.set(2, 3);
		
		caseRunner = new CaseRunnerWithBugInject();
		((CaseRunnerWithBugInject)caseRunner).inject(bugModel);
		
		tree = new TreeStruct(wrongCase,rightSuite);
		tree.constructTree();
		tree.init();
		
		generate = new CorpTupleWithTestCase(wrongCase,param);
	}
}
