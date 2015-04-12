package experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.fc.exploreAlorgrithm.GenerateAlogrithm;
import com.fc.model.CharacterWorkMachine;
import com.fc.model.FIC;
import com.fc.model.IteratorMachineNA;
import com.fc.tuple.Tuple;

public class ExperimentOverlapped {
	public static final int TRT = 0;
	public static final int TRT_NA = 1;
	public static final int FIC = 2;

	public ExperimentOverlapped() {
	}

	public void test(int length, int value) {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(length, value);
		ExperimentData experimentData = new ExperimentData(casedata);

		System.out.println("TRT " + "2-way-Overlapped:");
		testOverLap(TRT, 2, experimentData, casedata);
		System.out.println("TRT_NA " + "2-way-Overlapped:");
		testOverLap(TRT_NA, 2, experimentData, casedata);
		System.out.println("FIC " + "2-way-Overlapped:");
		testOverLap(FIC, 2, experimentData, casedata);

	}

	public int getNum(int alorgithm, ExperimentData experimentData,
			DataBaseOfTestCase casedata) {
		if (alorgithm == TRT) {
			CharacterWorkMachine workMachine = GenerateAlogrithm.getAlogrithm(
					GenerateAlogrithm.PATH, experimentData.getTree(),
					experimentData.getCaseRunner(),
					experimentData.getGenerate());

			workMachine.process();
			return workMachine.getExtraCases().getTestCaseNum();
		} else if (alorgithm == TRT_NA) {
			IteratorMachineNA ina = new IteratorMachineNA(casedata.getParam(),
					experimentData.getCaseRunner(), casedata.getRightSuite());
			ina.process(casedata.getWrongCase());
			return ina.getExtraTestSuite().getTestCaseNum();
		} else {
			FIC fic = new FIC(casedata.getWrongCase(), casedata.getParam(),
					experimentData.getCaseRunner());
			fic.FicNOP();
			return fic.getExtraCases().getTestCaseNum() - 1;
		}
	}
	public void testOverLap(int alorgithm, int degree,
			ExperimentData experimentData, DataBaseOfTestCase casedata) {
		System.out.println(degree + "-way-overlap:");
		List<Tuple[]> bugs = null;
		if (degree == 2) {
			bugs = experimentData.getTwoOverlapBugs(experimentData
					.generate2DegreeBug());
		} else if (degree == 3) {
			bugs = experimentData.getTwoOverlapBugs(experimentData
					.generate3DegreeBug());
		} else {
			bugs = experimentData.getTwoOverlapBugs(experimentData
					.generate4DegreeBug());
		}
		int avg = 0;
		int num = 0;
		for (Tuple[] bug : bugs) {
			experimentData.reset(casedata);
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			for (Tuple bu : bug)
				bugsMode.add(bu);
			experimentData.inject(bugsMode);
			int needCases = this.getNum(alorgithm, experimentData, casedata);
			System.out.print(needCases + " ");
			avg += needCases;
			num += 1;
		}
		System.out.println();
		System.out.println("avg: " + avg / (float) num);
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
		ExperimentOverlapped ex = new ExperimentOverlapped();
		ex.setOutPut("ExperimentOverlapped" + "8,3_avg");
		ex.test(8, 3);
	}
}
