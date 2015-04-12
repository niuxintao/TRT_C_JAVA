#include "tree.h"
#include "generate.h"
#include "select.h"
#include <stdlib.h>
#include <stdio.h>
void initRecord(TestCase* fail , Tuple* root){

	initTuple(root,fail->testCaseSize,fail);
	int i ;
	//the root index covers all the index of test case ----fail
	for(i = 0 ; i < fail->testCaseSize; i++){
		root->paramIndexPtr[i] = i;
	}
}
void extraProcessAfterBug(Iterator* bugsExsited,Iterator* rightsExsited){
	wrongAfter(bugsExsited,rightsExsited);
}

void extraProcessAfterRight(Iterator* bugsExsited,Iterator* rightsExsited){
	rightAfter(bugsExsited,rightsExsited);
}

void identify(int* parameter, TestCase* fail, Iterator* minimalBugsExisted, Iterator* maximalRightsExisted){
	//the valuables that path needed
	initPath(fail->testCaseSize);
	while(true){
		Tuple* selectedTuple = NULL; 
		selectTuple(minimalBugsExisted,maximalRightsExisted,&selectedTuple);

		if(selectedTuple == NULL)
			break;
		char str[500];
		putTupleToString(selectedTuple,str);
		printf("the select tuple is : %s\n",str);

		TestCase* testCase = (TestCase*)malloc(sizeof(TestCase));
		generateTestCaseContainOf(selectedTuple,testCase,fail,parameter);
		if(runTest(testCase)){
			extraProcessAfterRight(minimalBugsExisted,maximalRightsExisted);
		}else{
			extraProcessAfterBug(minimalBugsExisted,maximalRightsExisted);
		}
		freeTestCase(testCase);
	}
}
