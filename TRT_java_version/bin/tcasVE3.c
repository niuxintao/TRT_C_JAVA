/**
[Cur_Vertical_Sep = 601,High_confidence = 1,Two_of_Three_Reports_Valid == 0,-,Own_Tracked_Alt_Rate = 600,-,-,-,Other_RAC == 0,-,Other_Capability== 1,-]
**/

int alt_sep_test_v1()
{
    bool enabled, tcas_equipped, intent_not_known;
    bool need_upward_RA, need_downward_RA;
    int alt_sep;

    enabled = High_Confidence && (Own_Tracked_Alt_Rate <= OLEV) && (Cur_Vertical_Sep > MAXALTDIFF);
    tcas_equipped = Other_Capability == TCAS_TA;
    intent_not_known = /*Two_of_Three_Reports_Valid && */Other_RAC == NO_INTENT;
    
    alt_sep = UNRESOLVED;
    
    if (enabled && (intent_not_known || !tcas_equipped))
    {
	need_upward_RA = Non_Crossing_Biased_Climb() && Own_Below_Threat();
	need_downward_RA = Non_Crossing_Biased_Descend() && Own_Above_Threat();
	if (need_upward_RA && need_downward_RA)
	    alt_sep = UNRESOLVED1;
	else if (need_upward_RA)
	    alt_sep = UPWARD_RA;
	else if (need_downward_RA)
	    alt_sep = DOWNWARD_RA;
	else
            alt_sep = UNRESOLVED2;
    }
    
    return alt_sep;
}
