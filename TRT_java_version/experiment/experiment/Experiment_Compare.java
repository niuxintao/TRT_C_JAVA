package experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.fc.exploreAlorgrithm.GenerateAlogrithm;
import com.fc.model.CharacterWorkMachine;
import com.fc.tuple.Tuple;

public class Experiment_Compare {
	public int kind = 0;
	
	public Experiment_Compare(int kind){
		this.kind = kind;
	}

	public void test(int length,int value) {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(length,value);
		ExperimentData experimentData = new ExperimentData(casedata);
		testSingle(2, experimentData, casedata);
		testSingle(3, experimentData, casedata);
		testSingle(4, experimentData, casedata);
	}

	public void testSingle(int degree, ExperimentData experimentData,
			DataBaseOfTestCase casedata) {
		System.out.println(degree + "-way-single:");
		List<Tuple> bugs = null;
		if (degree == 2) {
			bugs = experimentData.generate2DegreeBug();
		} else if (degree == 3) {
			bugs = experimentData.generate3DegreeBug();
		} else {
			bugs = experimentData.generate4DegreeBug();
		}
		for (Tuple bug : bugs) {
			experimentData.reset(casedata);
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			bugsMode.add(bug);
			experimentData.inject(bugsMode);
			CharacterWorkMachine workMachine = GenerateAlogrithm.getAlogrithm(kind,
					experimentData.getTree(), experimentData.getCaseRunner(),
					experimentData.getGenerate());
			workMachine.process();
			System.out
					.print(workMachine.getExtraCases().getTestCaseNum() + " ");
		}
		System.out.println();
	}
	public void setOutPut(String name) {
		File test = new File(name);
		try {
			PrintStream out = new PrintStream(new FileOutputStream(test));
			System.setOut(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
			Experiment_Compare ex = new Experiment_Compare(GenerateAlogrithm.PATH);
			ex.setOutPut(GenerateAlogrithm.ALGRITHM_NAME[GenerateAlogrithm.PATH]+"14,3");
			ex.test(14,3);
	}
}
