#include <stdio.h>
#include <stdlib.h>
#include "tree.h"
#include "path.h"

void testPath(){
	int testCasea[8] = {1,1,1,1,1,1,1,1};
	TestCase fail;
	fail.testCasePtr = testCasea;
	fail.testInfo = UNTESTED;
	fail.testCaseSize = 8;

	Tuple root;
	initRecord(&fail,&root);

	Iterator bugsExsited;
	INIT_LIST_HEAD(&bugsExsited);
	int indexPtr[2] = {2,3};
	Tuple tuplea;
	tuplea.paramIndexPtr = indexPtr;
	tuplea.tupleSize = 2;
	tuplea.testCase = &fail;

	int indexPtr2[2] = {1,2};
	Tuple tupleb;
	tupleb.paramIndexPtr = indexPtr2;
	tupleb.tupleSize = 2;
	tupleb.testCase = &fail;

	TupleNode tupleNode;
	tupleNode.tuple = &tuplea;
	list_add_tail(&tupleNode.iterator,&bugsExsited);
	TupleNode tupleNode2;
	tupleNode2.tuple = &tupleb;
	list_add_tail(&tupleNode2.iterator,&bugsExsited);

	
	Iterator heads , *cur;
	INIT_LIST_HEAD(&heads);
	
	getLongestCandidateHeads(&bugsExsited,&heads);

	printf("heads start : \n");
	list_for_each(cur,&heads){
		Tuple* tuple = (list_entry(cur,TupleNode,iterator))->tuple;
		char str[500];
		putTupleToString(tuple,str);
		printf("the tuple is : %s\n",str);
	}
	
	Iterator rightsExsited;
	INIT_LIST_HEAD(&rightsExsited);
	Tuple tuplec;
	int indexc[] = {1,2,3,4,5,6};
	tuplec.tupleSize = 6;
	tuplec.paramIndexPtr = indexc;
	tuplec.testCase = &fail;
	Tuple tupled;
	int indexd[] = {2,3,4,5,6,7};
	tupled.tupleSize = 6;
	tupled.paramIndexPtr = indexd;
	tupled.testCase = &fail;


	TupleNode tupleNode3;
	tupleNode3.tuple = &tuplec;
	TupleNode tupleNode4;
	tupleNode4.tuple = &tupled;

	list_add_tail(&tupleNode3.iterator,&rightsExsited);
	list_add_tail(&tupleNode4.iterator,&rightsExsited);

	Iterator tails;
	INIT_LIST_HEAD(&tails);
	getLongestCandidateTails(&rightsExsited,&tails);
	printf("tails start : \n");
	list_for_each(cur,&tails){
		Tuple* tuple = (list_entry(cur,TupleNode,iterator))->tuple;
		char str[500];
		putTupleToString(tuple,str);
		printf("the tuple is : %s\n",str);
	}
}

void testPath2(){
	int testCasea[8] = {1,1,1,1,1,1,1,1};
	TestCase fail;
	fail.testCasePtr = testCasea;
	fail.testInfo = UNTESTED;
	fail.testCaseSize = 8;

	Tuple root;
	initRecord(&fail,&root);

	Iterator bugsExsited;
	INIT_LIST_HEAD(&bugsExsited);
	TupleNode tupleNode;
	tupleNode.tuple = &root;
	list_add_tail(&tupleNode.iterator,&bugsExsited);

	Tuple right;
	right.testCase = &fail;
	right.tupleSize = 0;
	Iterator rightsExsited;
	INIT_LIST_HEAD(&rightsExsited);
	TupleNode tupleNode2;
	tupleNode2.tuple = &right;
	list_add_tail(&tupleNode2.iterator,&rightsExsited);


	Iterator paths;
	INIT_LIST_HEAD(&paths);

	getLongestPath(&bugsExsited,&rightsExsited,&paths);
	Iterator * cur;
	printf("the longest path is :\n");
	list_for_each(cur,&paths){
		Tuple* tuple = (list_entry(cur,TupleNode,iterator))->tuple;
		char str[500];
		putTupleToString(tuple,str);
		printf("the tuple is : %s\n",str);

	}
}
int main(){
	testPath();
	testPath2();
	return 0;
}
