#include <stdio.h>
#include <stdlib.h>
#include "testObject.h"
#define MAXSTRING 500
void testTestCase(){
	int testCasea[8] = {1,1,1,1,1,1,1,1};
	TestCase case1;
	case1.testCasePtr = testCasea;
	case1.testInfo = UNTESTED;
	case1.testCaseSize = 8;

	int testCaseb[8] = {1,1,1,1,1,1,1,1};
	TestCase case2;
	case2.testCasePtr = testCaseb;
	case2.testInfo = UNTESTED;
	case2.testCaseSize = 8;

	int testCasec[8] = {1,2,1,2,1,2,1,2};
	TestCase case3;
	case3.testCasePtr = testCasec;
	case3.testInfo = UNTESTED;
	case3.testCaseSize = 8;

	int indexPtr[3] = {2,3,5};
	Tuple tuplea;
	tuplea.paramIndexPtr = indexPtr;
	tuplea.tupleSize = 3;
	tuplea.testCase = &case1;

	int indexPtr2[3] = {2,3,5};
	Tuple tupleb;
	tupleb.paramIndexPtr = indexPtr2;
	tupleb.tupleSize = 3;
	tupleb.testCase = &case3;

	printf("is test1 and test2 equal: %s\n", isTestCaseEqual(&case1, &case2) == true ? "true":"false");
	printf("is test1 and test3 equal: %s\n", isTestCaseEqual(&case1, &case3) == true ? "true":"false");
	char str[MAXSTRING];
	putTestCaseToString(&case1,str);
	printf("the test 1 is :%s\n",str);
	printf("is test1 contain tuple1: %s\n",isTestCaseContain(&case1,&tuplea) == true ? "true":"false");
	printf("is test1 contain tuple2: %s\n",isTestCaseContain(&case1,&tupleb) == true ? "true":"false");
	printf("is tuple1 equal tuple2: %s\n", isTupleEqual(&tuplea, &tupleb) == true ? "true":"false");
	printf("is tuple1 equal tuple1: %s\n", isTupleEqual(&tuplea, &tuplea) == true ? "true":"false");
	char str2[MAXSTRING];
	putTupleToString(&tuplea,str2);
	printf("the tuple 1 is : %s\n",str2);

}
void testTuple(){
	int testCasea[8] = {1,1,1,1,1,1,1,1};
	TestCase case1;
	case1.testCasePtr = testCasea;
	case1.testInfo = UNTESTED;
	case1.testCaseSize = 8;


	int indexPtr[4] = {2,3,5,7};
	Tuple tuplea;
	tuplea.paramIndexPtr = indexPtr;
	tuplea.tupleSize = 4;
	tuplea.testCase = &case1;

	/** test the copy function*/
	Tuple* tuple = (Tuple*)malloc(sizeof(Tuple));
	copyTuple(&tuplea, tuple);
	char str[MAXSTRING];
	putTupleToString(tuple,str);
	printf("the copy is %s\n",str);
	freeTuple(tuple);
	/** test the get child function*/
	Iterator head , *iterator;
	INIT_LIST_HEAD(&head);
	getChildTuplesByDegree(2,&tuplea,&head);

	list_for_each(iterator, &head){
		TupleNode* node = list_entry(iterator,TupleNode,iterator);
		char str1[MAXSTRING];
		putTupleToString(node->tuple, str1);
		printf("the child is %s\n",str1);
	}
	freeTupleList(&head);

	printf("\n");
	/** test the get all child function*/
	Iterator heada , *iteratora;
	INIT_LIST_HEAD(&heada);
	getAllChildTuples(&tuplea,&heada);

	list_for_each(iteratora, &heada){
		TupleNode* node = list_entry(iteratora,TupleNode,iterator);
		char str1[MAXSTRING];
		putTupleToString(node->tuple, str1);
		printf("the child is %s\n",str1);
	}
	freeTupleList(&heada);

	/**test the get father function*/	
	Iterator head2 ;
	INIT_LIST_HEAD(&head2);
	getFatherTuplesByDegree(6,&tuplea,&head2);
	list_for_each(iterator, &head2){
		TupleNode* node = list_entry(iterator,TupleNode,iterator);
		char str1[MAXSTRING];
		putTupleToString(node->tuple, str1);
		printf("the father is %s\n",str1);

	}
	freeTupleList(&head2);

}
int main(){
	testTuple();
	return 0 ;
}
