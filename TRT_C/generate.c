#include "generate.h"
#include <stdlib.h>
static Iterator bugs;

void initBugs(){
	INIT_LIST_HEAD(&bugs);
}
boolean runTest(TestCase* testCase){
	Iterator *cur;
	list_for_each(cur,&bugs){
		Tuple* tuple = (list_entry(cur,TupleNode,iterator))->tuple;
		if(isTestCaseContain(testCase,tuple))
			return false;
	}
	return true;
} 
	
void generateTestCaseContainOf(Tuple* tuple, TestCase* testCase,TestCase* failCase,int* parameter){
	int* testCasePtr = (int*)malloc(failCase->testCaseSize*sizeof(int));
	testCase->testCaseSize = failCase->testCaseSize;
	testCase->testInfo = UNTESTED;
	int i;
	for(i = 0; i < testCase->testCaseSize; i++)
		testCasePtr[i] = (failCase->testCasePtr[i]+1)%parameter[i];
	
	for(i = 0; i < tuple->tupleSize; i++){
		int index = tuple->paramIndexPtr[i];
		testCasePtr[index] = failCase->testCasePtr[index];
	}
	testCase->testCasePtr = testCasePtr;
}

void injectBugs(int* indexPtr, int degree, TestCase* testCase){
	TupleNode* node = (TupleNode*)malloc(sizeof(TupleNode));
	Tuple* data = (Tuple*)malloc(sizeof(Tuple));

	data->tupleSize = degree;
	data->paramIndexPtr = indexPtr;
	data->testCase = testCase;

	node->tuple = data;
	list_add(&node->iterator,&bugs);
}

