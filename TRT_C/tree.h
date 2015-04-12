#ifndef __TREE_H
#define __TREE_H
#include "testObject.h"
#define RIGHT 1
#define BUG 2
#define UNKNOWN 0
boolean isTupleTested(Tuple* tuple, int* testedArray);
void initRecord(TestCase* fail, Tuple* root);
void identify(int* parameter,TestCase* fail,Iterator* minimalbugs,Iterator* maxirights);
#endif
