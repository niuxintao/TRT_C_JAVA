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

public class Experiment_process {
	public int kind = 0;

	public Experiment_process(int kind) {
		this.kind = kind;
	}

	public void test(int length, int value) {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(length, value);
		ExperimentData experimentData = new ExperimentData(casedata);
		testSingle(2, experimentData, casedata);
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
		Tuple bug = bugs.get(5);
		System.out.println(bug.toString());

		experimentData.reset(casedata);
		List<Tuple> bugsMode = new ArrayList<Tuple>();
		bugsMode.add(bug);
		experimentData.inject(bugsMode);
		CharacterWorkMachine workMachine = GenerateAlogrithm.getAlogrithm(kind,
				experimentData.getTree(), experimentData.getCaseRunner(),
				experimentData.getGenerate());
		workMachine.process();
		System.out.print(workMachine.getExtraCases().getTestCaseNum() + " ");

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
		Experiment_process ex = new Experiment_process(GenerateAlogrithm.PATH);
		ex.setOutPut(GenerateAlogrithm.ALGRITHM_NAME[GenerateAlogrithm.PATH]
				+ "9,3_avg");
		ex.test(8, 3);
	}
}
