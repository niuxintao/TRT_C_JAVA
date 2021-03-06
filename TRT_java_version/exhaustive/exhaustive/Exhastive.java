package exhaustive;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;




import com.fc.caseRunner.CaseRunner;
import com.fc.caseRunner.CaseRunnerRealSoftware;
import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;

public class Exhastive {
	public String[][] configurations;

	public List<String> allConfigurations;

	// public List<String> configurations;

	private OutputSet outputRecord;

	private PrintStream out;
	private ByteArrayOutputStream record;

	private CaseRunner caseRunner;

	public void redirectSystemOut() {
		PrintStream ps = new PrintStream(record);
		System.setOut(ps);
	}

	public String getResult() {
		System.out.flush();
		return record.toString();
	}

	public void getBackOut() {
		System.setOut(out);
		record.reset();
	}
	
	public Exhastive(){
		caseRunner = new CaseRunnerRealSoftware();
		outputRecord = new OutputSet();
	}
	
	
	public static void main(String[] args){
		Exhastive tj = new Exhastive();
		int[] param = new int[] { 3, 2, 2, 2, 2, 2, 4, 10, 10, 3, 2, 2 };
//		int[] set = new int[] { 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 3 };
		int[] part = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		try {
			FileWriter fw = new FileWriter("result_of_testCase.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			try {
				tj.testBench(param, part, 0, bw);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bw.flush();
			bw.close();
			fw.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tj.showResult();
	}


	protected String test(int[] testCase) {
		return this.runTest(testCase, caseRunner) ? OutputSet.PASS
				: OutputSet.FAIL;
	}

	protected boolean runTest(int[] test, CaseRunner caseRunner) {
		TestCaseImplement testCase = new TestCaseImplement();
		testCase.setTestCase(test);
		caseRunner.runTestCase(testCase);
		return testCase.testDescription() == TestCase.PASSED;
	}

	protected boolean runTest(TestCase testCase, CaseRunner caseRunner) {
		caseRunner.runTestCase(testCase);
		return testCase.testDescription() == TestCase.PASSED;
	}

	public void testBench(int[] set, int[] part, int index, BufferedWriter bw) {
		int nextIndex = index + 1;
		for (int i = 0; i < set[index]; i++) {
			int[] partCur = new int[set.length];
			System.arraycopy(part, 0, partCur, 0, set.length);
			partCur[index] = i;
			if (nextIndex == set.length) {
				String str;
				try {
					str = test(partCur);
					this.outputRecord.pendingStr(str);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Writer writer = new StringWriter();
					PrintWriter printWriter = new PrintWriter(writer);
					e.printStackTrace(printWriter);
					String s = writer.toString();
					this.outputRecord.pendingStr(s);
					str = s;
				}

				try {

					for (int op : partCur)
						bw.write(op + " ");
					bw.write(":");
					bw.write(this.outputRecord.get(str) + " ");
					bw.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				testBench(set, partCur, nextIndex, bw);
			}
		}
	}

	public void showResult() {
		try {
			FileWriter fw = new FileWriter("bugInfo.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < this.outputRecord.getCurIndex(); i++) {
				bw.write(i + " : " + this.outputRecord.get(i));
				bw.newLine();
			}
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
