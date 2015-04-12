package ExperimentReal;

import java.util.List;

import com.fc.caseRunner.CaseRunner;
import com.fc.caseRunner.CaseRunnerRealSoftware;
import com.fc.caseRunner.Judge;
import com.fc.model.FIC;
import com.fc.model.TreeStruct;
import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;
import com.fc.tuple.CorpTupleWithTestCase;
import com.fc.tuple.Tuple;

public class ExperimentFIC {
	protected CaseRunner caseRunner;
	protected CorpTupleWithTestCase generate;
	protected TreeStruct tree;
	
	public void testWorkFlow(){
		
		init();
	}

	protected void init() {
		int[] wrong = new int[]{2,1,1,0,1,0,0,0,0,0,1,0};
		TestCase wrongCase = new TestCaseImplement();
		((TestCaseImplement)wrongCase).setTestCase(wrong);
		
		int[] param = new int[]{3,2,2,2,2,2,4,10,10,3,2,2};
		
		caseRunner = new CaseRunnerRealSoftware();
		
		generate = new CorpTupleWithTestCase(wrongCase,param);
		
		FIC fic = new FIC(wrongCase, param,caseRunner);
		fic.FicNOP();
		List<Tuple> bugs = fic.getBugs();
		for(Tuple bug : bugs){
			for(int i = 0 ; i < bug.getDegree() ; i++){
				int index = bug.getParamIndex()[i];
				int value = bug.getParamValue()[i];
				System.out.print(Judge.NAMES[index]+" : ");
				System.out.print(Judge.PARAMETERS[index][value]+"\n");
			}
			System.out.println(bug.toString());
		}
		
		System.out.println("extra test cases: "+ fic.getExtraCases().getTestCaseNum() );
	}
	
	public static void main(String[] args){
		ExperimentFIC ex = new ExperimentFIC();
		ex.testWorkFlow();
	}
}
