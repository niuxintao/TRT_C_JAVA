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
import com.fc.model.TreeStruct;
import com.fc.testObject.TestCase;
import com.fc.testObject.TestSuite;
import com.fc.testObject.TestSuiteImplement;
import com.fc.tuple.Tuple;

public class Experiment_NO {
	public static final int TRT = 0;
	public static final int TRT_NA = 1;
	public static final int FIC = 2;


	public Experiment_NO() {
	}

	public void test(int length, int value) {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(length, value);
		ExperimentData experimentData = new ExperimentData(casedata);
		System.out.println( "TRT "+ "2-way-single:");
		testSingle(2, experimentData, casedata);
		System.out.println( "TRT "+ "3-way-single:");
		testSingle(3, experimentData, casedata);
		System.out.println( "TRT "+ "4-way-single:");
		testSingle(4, experimentData, casedata);
	}
	
	public int  coverageTuples(FIC fic,int allNum, DataBaseOfTestCase casedata){
		List<Tuple> bugs = fic.getBugs();
		TestSuite suite = fic.getExtraCases();
		TestSuite rightSuite = new TestSuiteImplement();
		for(int i = 0 ; i < suite.getTestCaseNum(); i++){
			if(suite.getAt(i).testDescription() == TestCase.PASSED){
				rightSuite.addTest(suite.getAt(i));
			}
		}
		
		TreeStruct tree = new TreeStruct(casedata.getWrongCase(), rightSuite);// set exsit bug
		tree.constructTree();
		tree.init();
		tree.removeFoundBug(bugs);
		
		int untested = tree.getUntestedTuple().size();
		return allNum - untested;
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

	public void testSingle(int degree,
			ExperimentData experimentData, DataBaseOfTestCase casedata) {
		System.out.println(degree + "-way-single:");
		List<Tuple> bugs = null;
		if (degree == 2) {
			bugs = experimentData.generate2DegreeBug();
		} else if (degree == 3) {
			bugs = experimentData.generate3DegreeBug();
		} else {
			bugs = experimentData.generate4DegreeBug();
		}
		List<Integer> all1 = new ArrayList<Integer>();
		List<Integer> all2 = new ArrayList<Integer>();
		List<Integer> all3 = new ArrayList<Integer>();
		
		int all =( 1 << experimentData.getTree().getRoot().getDegree() ) - 1;
		
		List<Integer> coverage = new ArrayList<Integer>();
		List<Double> efficys = new ArrayList<Double>();

		for (Tuple bug : bugs) {
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			bugsMode.add(bug);
			experimentData.reset(casedata);
			experimentData.inject(bugsMode);
			int needCases1 = this.getNum(TRT, experimentData, casedata);
			all1.add(needCases1);
			experimentData.reset(casedata);
			experimentData.inject(bugsMode);
			int needCases2 = this.getNum(TRT_NA, experimentData, casedata);
			all2.add(needCases2);
			experimentData.reset(casedata);
			experimentData.inject(bugsMode);
			FIC fic = new FIC(casedata.getWrongCase(), casedata.getParam(),
					experimentData.getCaseRunner());
			fic.FicNOP();
			int needCases3 =  fic.getExtraCases().getTestCaseNum() - 1;
			all3.add(needCases3);
			int covrs = this.coverageTuples(fic,all, casedata);
			coverage.add(covrs);
			double efficy = ((double)covrs )/ needCases3;
			efficys.add(efficy);
		}

		System.out.println("TRT " + degree + "-way-Single:");
		int avg1 = 0;
		for (Integer i : all1) {
			avg1 += i;
		}
		double av1 = avg1 / (float) all1.size();
		System.out.println("avg: " + av1);
		System.out.println("cover num: " + all);
		System.out.println("efficvy: " + all/av1);
		

		System.out.println("TRT_NA " + degree + "-way-Single:");
		int avg2 = 0;
		for (Integer i : all2) {
			avg2 += i;
		}
		double av2 = avg2 / (float) all2.size();
		System.out.println("avg: " + av2);
		System.out.println("cover num: " + all);
		System.out.println("efficvy: " + all/av2);
		

		System.out.println("FIC " + degree + "-way-Single:");
		int avg3 = 0;
		for (Integer i : all3) {
			avg3 += i;
		}
		System.out.println("avg: " + avg3 / (float) all3.size());
		
		int avg4 = 0;
		for (Integer i : coverage) {
			avg4 += i;
		}
		System.out.println("avg cov: " + avg4 / (float) all3.size());
		
		double avg5 = 0;
		for (Double i : efficys) {
			avg5 += i;
		}
		System.out.println("avg efficy: " + avg5 / (float) all3.size());
		
	}

	public void testNoOverlap(int alorgithm, int degree,
			ExperimentData experimentData, DataBaseOfTestCase casedata) {
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
		int avg = 0;
		int num = 0;
		for (Tuple[] bug : bugs) {
			experimentData.reset(casedata);
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			for (Tuple bu : bug)
				bugsMode.add(bu);
			experimentData.inject(bugsMode);
			int needCases = this.getNum(alorgithm, experimentData, casedata);
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
		Experiment_NO ex = new Experiment_NO();
		ex.setOutPut("ExperimentSingle" + "8,10_avg");
		ex.test(8, 10);
		Experiment_NO ex2 = new Experiment_NO();
		ex2.setOutPut("ExperimentSingle" + "9,10_avg");
		ex2.test(9, 10);
		Experiment_NO ex3 = new Experiment_NO();
		ex3.setOutPut("ExperimentSingle" + "10,10_avg");
		ex3.test(10, 10);
		Experiment_NO ex4 = new Experiment_NO();
		ex4.setOutPut("ExperimentSingle" + "11,10_avg");
		ex4.test(11, 10);
		Experiment_NO ex5 = new Experiment_NO();
		ex5.setOutPut("ExperimentSingle" + "12,10_avg");
		ex5.test(12, 10);
	}
}
