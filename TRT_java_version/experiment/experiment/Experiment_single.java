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

public class Experiment_single {
	public static final int TRT = 0;
	public static final int TRT_NA = 1;
	public static final int FIC = 2;


	public Experiment_single() {
	}

	public void test(int length, int value) {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(length, value);
		ExperimentData experimentData = new ExperimentData(casedata);
		System.out.println( "TRT "+ "2-way-NO:");
		testNO(2, experimentData, casedata);
		System.out.println( "TRT "+ "3-way-NO:");
		testNO(3, experimentData, casedata);
		System.out.println( "TRT "+ "4-way-NO:");
		testNO(4, experimentData, casedata);
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

	public void testNO(int degree,
			ExperimentData experimentData, DataBaseOfTestCase casedata) {
		System.out.println(degree + "-way-NO:");
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
		List<Integer> all1 = new ArrayList<Integer>();
		List<Integer> all2 = new ArrayList<Integer>();
		List<Integer> all3 = new ArrayList<Integer>();
		
		int all =( 1 << experimentData.getTree().getRoot().getDegree() ) - 1;
		
		List<Integer> coverage = new ArrayList<Integer>();
		List<Double> efficys = new ArrayList<Double>();

		for (Tuple[] bug : bugs) {
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			for (Tuple bu : bug)
				bugsMode.add(bu);
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

		System.out.println("TRT " + degree + "-way-NO:");
		int avg1 = 0;
		for (Integer i : all1) {
			avg1 += i;
		}
		double av1 = avg1 / (float) all1.size();
		System.out.print("avg: " + av1+"\t");
		System.out.print("cover num: " + all+"\t");
		System.out.print("efficvy: " + all/av1+"\t");
		System.out.println();

		System.out.println("TRT_NA " + degree + "-way-NO:");
		int avg2 = 0;
		for (Integer i : all2) {
			avg2 += i;
		}
		double av2 = avg2 / (float) all2.size();
		System.out.print("avg: " + av2+"\t");
		System.out.print("cover num: " + all+"\t");
		System.out.print("efficvy: " + all/av2+"\t");
		System.out.println();

		System.out.println("FIC " + degree + "-way-NO:");
		int avg3 = 0;
		for (Integer i : all3) {
			avg3 += i;
		}
		System.out.print("avg: " + avg3 / (float) all3.size()+"\t");
		
		int avg4 = 0;
		for (Integer i : coverage) {
			avg4 += i;
		}
		System.out.print("avg cov: " + avg4 / (float) all3.size()+"\t");
		
		double avg5 = 0;
		for (Double i : efficys) {
			avg5 += i;
		}
		System.out.print("avg efficy: " + avg5 / (float) all3.size()+"\t");
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
		Experiment_single ex = new Experiment_single();
		ex.setOutPut("ExperimentNO" + "8,10_avg");
		ex.test(8, 10);
		Experiment_single ex2 = new Experiment_single();
		ex2.setOutPut("ExperimentNO" + "9,10_avg");
		ex2.test(9, 10);
		Experiment_single ex3 = new Experiment_single();
		ex3.setOutPut("ExperimentNO" + "10,10_avg");
		ex3.test(10, 10);
		Experiment_single ex4 = new Experiment_single();
		ex4.setOutPut("ExperimentNO" + "11,10_avg");
		ex4.test(11, 10);
		Experiment_single ex5 = new Experiment_single();
		ex5.setOutPut("ExperimentNO" + "12,10_avg");
		ex5.test(12, 10);
	}
}
