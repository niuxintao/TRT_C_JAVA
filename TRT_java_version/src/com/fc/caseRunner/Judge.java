package com.fc.caseRunner;

public class Judge {
	public static final String[] NAMES = { "Cur_Vertical_Sep",
			"High_Confidence", "Two_of_Three_Reports_Valid", "Own_Tracked_Alt",
			"Own_Tracked_Alt_Rate", "Other_Tracked_Alt", "Alt_Layer_Value",
			"Up_Separation", "Down_Separation", "Other_RAC",
			"Other_Capability", "Climb_Inhibit" };
	public static final int[][] PARAMETERS = { { 299, 300, 601 }, { 0, 1 },
			{ 0, 1 }, { 1, 2 }, { 600, 601 }, { 1, 2 }, { 0, 1, 2, 3 },
			{ 0, 399, 400, 499, 500, 639, 640, 739, 740, 840 },
			{ 0, 399, 400, 499, 500, 639, 640, 739, 740, 840 }, { 0, 1, 2 },
			{ 1, 2 }, { 0, 1 } };
	static {
		System.loadLibrary("Judge");
	}

	public native boolean judge(int cur_vertical_step, boolean high_confidence,
			boolean two_of_three_reports_valid, int own_tracked_alt,
			int own_tracked_alt_rate, int other_tracked_alt,
			int alt_layer_value, int up_spearation, int down_separation,
			int other_rac, int other_capability, boolean climb_inhibit);

	public static void main(String[] args) {
		Judge judge = new Judge();
		System.out.println(judge.judge(299, true, false, 1, 2, 601, 2, 399,
				400, 1, 2, true));
	}
}
