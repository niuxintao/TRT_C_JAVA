#ifndef __PATH_H
#define __PATH_H
#include "testObject.h"
/** get the longest path heads**/
void getLongestCandidateHeads(Iterator* bugsExsited,Iterator* heads);
/** get the longest path tail**/
void getLongestCandidateTails(Iterator* rightExsited,Iterator* rights);
/** get the longest path**/
void getLongestPath(Iterator* minimalBugsExisted,Iterator* maximalRightsExisted,Iterator* paths);
#endif
