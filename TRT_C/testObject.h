#ifndef __TESTOBJECT_H
#define __TESTOBJECT_H
#include "list.h"
#define UNTESTED 0
#define FAILED -1
#define PASSED 1
#define TestCase struct Testcase
#define Tuple struct TUPLE
#define boolean enum Boolean
#define TupleNode struct TUPLE_NODE
#define TestCaseNode struct TEST_NODE
enum Boolean {false, true};
struct Testcase {
	int * testCasePtr;
	int testInfo;
	int testCaseSize;
};
struct TUPLE{
	int * paramIndexPtr;
	TestCase * testCase;
	int tupleSize;
};
struct TUPLE_NODE{
	Tuple* tuple;
	Iterator iterator;
};
struct TEST_NODE {
	TestCase* testCase;
	Iterator iterator;
};

boolean isTestCaseEqual(TestCase* t_a, TestCase* t_b);
void putTestCaseToString(TestCase* t, char* str );
boolean isTestCaseContain(TestCase* t , Tuple* tuple);
void putTupleToString(Tuple* tuple, char* str);
boolean isTupleEqual(Tuple* t_a, Tuple* t_b);
int isTupleContain(Tuple* t_a, Tuple* t_b);
void initTuple(Tuple* tuple, int degree, TestCase* testCase);
void copyTuple(Tuple* from , Tuple* to);
void getChildTuplesByDegree(int degree , Tuple* father , Iterator* head);
void freeTuple(Tuple* tuple);
void freeTestCase(TestCase* testCase);
void freeTupleList(Iterator* head);
void getReverseTuple(Tuple* tuple, Tuple* reverse);
void cat(Tuple* A , Tuple* B, Tuple* to);
void getFatherTuplesByDegree(int degree, Tuple* child, Iterator* fatherHead);

void getAllChildTuples(Tuple* father, Iterator* head);
void getAllFatherTuples(Tuple* child, Iterator* head);

//delete the tuples that have maxier right or minimal bug
void compress(Iterator* tuples);
void compress_r(Iterator* tuples);
#endif
