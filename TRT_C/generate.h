#ifndef __GENCASE_H
#define __GENCASE_H
#include "testObject.h"

void generateTestCaseContainOf(Tuple* tuple, TestCase* testCase,TestCase* failCase, int* parameter);
boolean runTest(TestCase* testCase);
void initBugs();
void injectBugs(int* ptr, int degree, TestCase* testCase);

#endif
