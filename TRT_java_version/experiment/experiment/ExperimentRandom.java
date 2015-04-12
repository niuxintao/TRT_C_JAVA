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

public class ExperimentRandom {
	public static final int RANDOMNUM = 100;
	private int[] _2s;
	private int[] _3s;
	private int[] _4s;
	private int[] _2o;
	private int[] _3o;
	private int[] _4o;
	private int[] _2n;
	private int[] _3n;
	private int[] _4n;

	public ExperimentRandom(){
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(8,3);
		ExperimentData experimentData = new ExperimentData(casedata);
		_2s = new int[experimentData.generate2DegreeBug().size()];
		_3s = new int[experimentData.generate3DegreeBug().size()];
		_4s = new int[experimentData.generate4DegreeBug().size()];
		_2o = new int[experimentData.getTwoOverlapBugs(experimentData
				.generate2DegreeBug()).size()];
		_3o = new int[experimentData.getTwoOverlapBugs(experimentData
				.generate3DegreeBug()).size()];
		_4o = new int[experimentData.getTwoOverlapBugs(experimentData
				.generate4DegreeBug()).size()];
		_2n = new int[experimentData.getTwoNonOverlapBugs(experimentData
				.generate2DegreeBug()).size()];
		_3n = new int[experimentData.getTwoNonOverlapBugs(experimentData
				.generate3DegreeBug()).size()];
		_4n = new int[experimentData.getTwoNonOverlapBugs(experimentData
				.generate4DegreeBug()).size()];
	}
	public void test() {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(8,3);
		ExperimentData experimentData = new ExperimentData(casedata);
		testSingle(2, experimentData, casedata);
		testSingle(3, experimentData, casedata);
		testSingle(4, experimentData, casedata);
		testOverLap(2, experimentData, casedata);
		testOverLap(3, experimentData, casedata);
		testOverLap(4, experimentData, casedata);
		testNoOverlap(2, experimentData, casedata);
		testNoOverlap(3, experimentData, casedata);
		testNoOverlap(4, experimentData, casedata);
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
		int i = 0;
		for (Tuple bug : bugs) {
			experimentData.reset(casedata);
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			bugsMode.add(bug);
			experimentData.inject(bugsMode);
			CharacterWorkMachine workMachine = GenerateAlogrithm.getAlogrithm(
					GenerateAlogrithm.RANDOM, experimentData.getTree(),
					experimentData.getCaseRunner(),
					experimentData.getGenerate());
			workMachine.process();

			if (degree == 2) {
				_2s[i] += workMachine.getExtraCases().getTestCaseNum();
			} else if (degree == 3) {
				_3s[i] += workMachine.getExtraCases().getTestCaseNum();
			} else {
				_4s[i] += workMachine.getExtraCases().getTestCaseNum();
			}
			i++;
			System.out
					.print(workMachine.getExtraCases().getTestCaseNum() + " ");
		}
		System.out.println();
	}

	public void testOverLap(int degree, ExperimentData experimentData,
			DataBaseOfTestCase casedata) {
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
		int i = 0;
		for (Tuple[] bug : bugs) {
			experimentData.reset(casedata);
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			for (Tuple bu : bug)
				bugsMode.add(bu);
			experimentData.inject(bugsMode);
			CharacterWorkMachine workMachine = GenerateAlogrithm.getAlogrithm(
					GenerateAlogrithm.RANDOM, experimentData.getTree(),
					experimentData.getCaseRunner(),
					experimentData.getGenerate());
			workMachine.process();
			if (degree == 2) {
				_2o[i] += workMachine.getExtraCases().getTestCaseNum();
			} else if (degree == 3) {
				_3o[i] += workMachine.getExtraCases().getTestCaseNum();
			} else {
				_4o[i] += workMachine.getExtraCases().getTestCaseNum();
			}
			i++;
			System.out
					.print(workMachine.getExtraCases().getTestCaseNum() + " ");
		}
		System.out.println();
	}

	public void testNoOverlap(int degree, ExperimentData experimentData,
			DataBaseOfTestCase casedata) {
		System.out.println(degree + "-way-nonoverlap:");
		List<Tuple[]> bugs = null;
		if (degree == 2) {
			bugs = experimentData.getTwoNonOverlapBugs(experimentData
					.generate2DegreeBug());
		} else if (degree == 3) {
			bugs = experimentData.getTwoNonOverlapBugs(experimentData
					.generate3DegreeBug());
		} else {
			bugs = experimentData.getTwoNonOverlapBugs(experimentData
					.generate4DegreeBug());
		}
		int i = 0;
		for (Tuple[] bug : bugs) {
			experimentData.reset(casedata);
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			for (Tuple bu : bug)
				bugsMode.add(bu);
			experimentData.inject(bugsMode);
			CharacterWorkMachine workMachine = GenerateAlogrithm.getAlogrithm(
					GenerateAlogrithm.RANDOM, experimentData.getTree(),
					experimentData.getCaseRunner(),
					experimentData.getGenerate());
			workMachine.process();
			if (degree == 2) {
				_2n[i] += workMachine.getExtraCases().getTestCaseNum();
			} else if (degree == 3) {
				_3n[i] += workMachine.getExtraCases().getTestCaseNum();
			} else {
				_4n[i] += workMachine.getExtraCases().getTestCaseNum();
			}
			i++;
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
		ExperimentRandom ex = new ExperimentRandom();
		ex.setOutPut(GenerateAlogrithm.ALGRITHM_NAME[0] + "100_times");
		for (int i = 0; i < ExperimentRandom.RANDOMNUM; i++) {
			ex.test();
		}
		ex.setOutPut(GenerateAlogrithm.ALGRITHM_NAME[0] + "average");
		System.out.println("2-way-single:");
		for (int i : ex._2s) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("3-way-single:");
		for (int i : ex._3s) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("4-way-single:");
		for (int i : ex._4s) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("2-way-overlap:");
		for (int i : ex._2o) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("3-way-overlap:");
		for (int i : ex._3o) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("4-way-overlap:");
		for (int i : ex._4o) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("2-way-nonoverlap:");
		for (int i : ex._2n) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("3-way-nonoverlap:");
		for (int i : ex._3n) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
		System.out.println("");
		System.out.println("4-way-nonoverlap:");
		for (int i : ex._4n) {
			System.out.print((((float) i) / ExperimentRandom.RANDOMNUM) + " ");
		}
	}
}
