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
import com.fc.testObject.TestCase;
import com.fc.tuple.Tuple;

public class ExperimentImport {
	public static final int TRT = 0;
	public static final int TRT_NA = 1;
	public static final int FIC = 2;

	public static final int CONTAINED = 3;
	public static final int IMPORTED = 4;
	public static final int ERROR = 5;

	public ExperimentImport() {
	}

	public void test(int length, int value) {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(length, value);
		ExperimentData experimentData = new ExperimentData(casedata);
		testImport(2, experimentData, casedata);
		testImport(3, experimentData, casedata);
		testImport(4, experimentData, casedata);

	}

	public List<Tuple> getBugs(int alorgithm, ExperimentData experimentData,
			DataBaseOfTestCase casedata) {
		if (alorgithm == TRT) {
			CharacterWorkMachine workMachine = GenerateAlogrithm.getAlogrithm(
					GenerateAlogrithm.PATH, experimentData.getTree(),
					experimentData.getCaseRunner(),
					experimentData.getGenerate());

			workMachine.process();
			return experimentData.getTree().getBugModes();
		} else if (alorgithm == TRT_NA) {
			IteratorMachineNA ina = new IteratorMachineNA(casedata.getParam(),
					experimentData.getCaseRunner(), casedata.getRightSuite());
			ina.process(casedata.getWrongCase());
			return ina.getBugs();
		} else {
			FIC fic = new FIC(casedata.getWrongCase(), casedata.getParam(),
					experimentData.getCaseRunner());
			fic.FicNOP();
			return fic.getBugs();
		}
	}

	public int propoties(Tuple iden, TestCase testCase, List<Tuple> bugsMode) {
		boolean isError = true;
		for (Tuple tuple : bugsMode) {
			if (tuple.equals(iden)) {
				isError = false;
				break;
			}
		}
		if (isError)
			return ERROR;
		if (testCase.containsOf(iden))
			return CONTAINED;
		return IMPORTED;
	}

	public void testImport(int degree, ExperimentData experimentData,
			DataBaseOfTestCase casedata) {
		System.out.println(degree + "-way-Import:");
		List<Tuple[]> bugs = null;
		if (degree == 2) {
			bugs = experimentData.getOneBugWithOneNewBug(experimentData
					.generate2DegreeBug());
		} else if (degree == 3) {
			bugs = experimentData.getOneBugWithOneNewBug(experimentData
					.generate3DegreeBug());
		} else {
			bugs = experimentData.getOneBugWithOneNewBug(experimentData
					.generate4DegreeBug());
		}
		
		

		int contain1 = 0;
		int error1 = 0;
		int imported1 = 0;

		int contain2 = 0;
		int error2 = 0;
		int imported2 = 0;

		int contain3 = 0;
		int error3 = 0;
		int imported3 = 0;

		for (Tuple[] bug : bugs) {
			List<Tuple> bugsMode = new ArrayList<Tuple>();
			for (Tuple bu : bug) {
				bugsMode.add(bu);
			}
			experimentData.reset(casedata);
			experimentData.inject(bugsMode);
			List<Tuple> getbugs = this.getBugs(TRT, experimentData, casedata);
			if (getbugs.size() == 0)
				error1++;
			else
			for (Tuple iden : getbugs) {
				int value = this.propoties(iden, casedata.getWrongCase(),
						bugsMode);
				if (value == CONTAINED) {
					contain1++;
				} else if (value == ERROR)
					error1++;
				else
					imported1++;
			}

			experimentData.reset(casedata);
			experimentData.inject(bugsMode);
			getbugs = this.getBugs(TRT_NA, experimentData, casedata);
			if (getbugs.size() == 0)
				error2++;
			else
			for (Tuple iden : getbugs) {
				int value = this.propoties(iden, casedata.getWrongCase(),
						bugsMode);
				if (value == CONTAINED) {
					contain2++;
				} else if (value == ERROR)
					error2++;
				else
					imported2++;
			}

			experimentData.reset(casedata);
			experimentData.inject(bugsMode);
			getbugs = this.getBugs(FIC, experimentData, casedata);
			if (getbugs.size() == 0)
				error3++;
			else
				for (Tuple iden : getbugs) {
					int value = this.propoties(iden, casedata.getWrongCase(),
							bugsMode);
					if (value == CONTAINED) {
						contain3++;
					} else if (value == ERROR)
						error3++;
					else
						imported3++;
				}
		}

		System.out.println("TRT " + degree + "-way-Import:");
		System.out.print("contained: " + contain1 + "\t");
		System.out.print("imported: " + imported1 + "\t");
		System.out.println("error: " + error1 + "\t");

		System.out.println("TRT_NA " + degree + "-way-Import:");
		System.out.print("contained: " + contain2 + "\t");
		System.out.print("imported: " + imported2 + "\t");
		System.out.println("error: " + error2 + "\t");

		System.out.println("FIC " + degree + "-way-Import:");
		System.out.print("contained: " + contain3 + "\t");
		System.out.print("imported: " + imported3 + "\t");
		System.out.println("error: " + error3 + "\t");
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
		ExperimentImport ex1 = new ExperimentImport();
		ex1.setOutPut("ExperimentImport" + "12,10_import");
		ex1.test(12, 10);
	}
}
