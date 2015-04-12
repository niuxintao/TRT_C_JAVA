#include <jni.h>
#include <stdio.h>
#include "com_fc_caseRunner_Judge.h"
#include "tcas.c"
#include "tcasV1.c"

JNIEXPORT jboolean JNICALL Java_com_fc_caseRunner_Judge_judge
  (JNIEnv * s1, jobject s2, jint cur, jboolean high, jboolean two, jint own_Tracked_Alt, jint own_Tracked_Alt_Rate, jint other_Tracked_Alt, jint alt_Layer_Value, jint up_Separation, jint down_Separation, jint other_RAC, jint other_Capability, jboolean climb_Inhibit){
    initialize();
    Cur_Vertical_Sep = cur;
    High_Confidence = high;
    Two_of_Three_Reports_Valid = two;
    Own_Tracked_Alt = own_Tracked_Alt;
    Own_Tracked_Alt_Rate = own_Tracked_Alt_Rate;
    Other_Tracked_Alt = other_Tracked_Alt;
    Alt_Layer_Value = alt_Layer_Value;
    Up_Separation = up_Separation;
    Down_Separation = down_Separation;
    Other_RAC = other_RAC;
    Other_Capability =  other_Capability;
    Climb_Inhibit = climb_Inhibit;
    return  alt_sep_test_v1() == alt_sep_test();
}

