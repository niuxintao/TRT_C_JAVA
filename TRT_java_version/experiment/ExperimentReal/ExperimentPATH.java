package ExperimentReal;

import java.util.List;

import com.fc.caseRunner.CaseRunner;
import com.fc.caseRunner.CaseRunnerRealSoftware;
import com.fc.exploreAlorgrithm.Path;
import com.fc.model.TreeStruct;
import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;
import com.fc.testObject.TestSuite;
import com.fc.testObject.TestSuiteImplement;
import com.fc.tuple.CorpTupleWithTestCase;
import com.fc.tuple.Tuple;
import com.fc.caseRunner.Judge;

public class ExperimentPATH {
	protected CaseRunner caseRunner;
	protected CorpTupleWithTestCase generate;
	protected TreeStruct tree;
	
	public void testWorkFlow(){
		init();
		Path workMachine = new Path(tree,caseRunner,generate);	
		workMachine.process();	
		
		List<Tuple> bugs = tree.getBugModes();
		for(Tuple bug : bugs){
			for(int i = 0 ; i < bug.getDegree() ; i++){
				int index = bug.getParamIndex()[i];
				int value = bug.getParamValue()[i];
				System.out.print(Judge.NAMES[index]+" : ");
				System.out.print(Judge.PARAMETERS[index][value]+"\n");
			}
			System.out.println(bug.toString());
		}
		
		TestSuite extra = workMachine.getExtraCases();
		System.out.println("extra Test Cases:"+extra.getTestCaseNum());
/*		for(int i = 0 ; i < extra.getTestCaseNum(); i++){
			System.out.println(extra.getAt(i).getStringOfTest() +"  "+extra.getAt(i).testDescription());
		}*/
		
	}
	/**
	[Cur_Vertical_Sep = 601,-,-,-,Own_Tracked_Alt_Rate = 601,-,-,-,-,-,Other_Capability== 2,-]

	[-,High_confidence = 0,-,-,Own_Tracked_Alt_Rate = 601,-,-,-,-,-,Other_Capability== 2,-]


	[Cur_Vertical_Sep = 601,-,Two_of_Three_Reports_Valid== 1,-,Own_Tracked_Alt_Rate = 601,-,-,-,-,Other_RAC == 0,-,-]


	[-,High_confidence = 0,Two_of_Three_Reports_Valid== 1,-,Own_Tracked_Alt_Rate = 601,-,-,-,-,Other_RAC == 0,-,-]

	**/
	protected void init() {
		int[] wrong = new int[]{2,1,1,0,1,0,0,0,0,0,1,0};
		TestCase wrongCase = new TestCaseImplement();
		((TestCaseImplement)wrongCase).setTestCase(wrong);
		
		TestSuite rightSuite = new TestSuiteImplement();
		
		int[] param = new int[]{3,2,2,2,2,2,4,10,10,3,2,2};
		
		caseRunner = new CaseRunnerRealSoftware();
		
		tree = new TreeStruct(wrongCase,rightSuite);
		tree.constructTree();
		tree.init();
		
		generate = new CorpTupleWithTestCase(wrongCase,param);
	}
	
	public static void main(String[] args){
		ExperimentPATH ex = new ExperimentPATH();
		ex.testWorkFlow();
	}
}

