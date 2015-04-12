package com.fc.caseRunner;

import com.fc.testObject.TestCase;
import com.fc.testObject.TestSuite;

public class CaseRunnerRealSoftware implements CaseRunner {

	@Override
	public void runTestCase(TestCase testCase) {
		// TODO Auto-generated method stub
		Judge judge = new Judge();

		if (judge.judge(Judge.PARAMETERS[0][testCase.getAt(0)],
				Judge.PARAMETERS[1][testCase.getAt(1)] != 0,
				Judge.PARAMETERS[2][testCase.getAt(2)] != 0,
				Judge.PARAMETERS[3][testCase.getAt(3)],
				Judge.PARAMETERS[4][testCase.getAt(4)],
				Judge.PARAMETERS[5][testCase.getAt(5)],
				Judge.PARAMETERS[6][testCase.getAt(6)],
				Judge.PARAMETERS[7][testCase.getAt(7)],
				Judge.PARAMETERS[8][testCase.getAt(8)],
				Judge.PARAMETERS[9][testCase.getAt(9)],
				Judge.PARAMETERS[10][testCase.getAt(10)],
				Judge.PARAMETERS[11][testCase.getAt(11)] != 0)) {
			testCase.setTestState(TestCase.PASSED);
		} else {
			testCase.setTestState(TestCase.FAILED);
		}
	}

	@Override
	public void runTestSuite(TestSuite suite) {
		// TODO Auto-generated method stub
		for (int i = 0; i < suite.getTestCaseNum(); i++) {
			this.runTestCase(suite.getAt(i));
		}
	}

	@Override
	public TestSuite getRunnedTestSuite() {
		// TODO Auto-generated method stub
		return null;
	}

}
