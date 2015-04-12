#include <stdio.h>
#include <stdlib.h>
#include "tree.h"
#include "generate.h"
#define MAXSTRLEN 500

void testIdentify(){
	int testCasePtr[20] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
	TestCase fail;
	fail.testCasePtr = testCasePtr;
	fail.testInfo = FAILED;
	fail.testCaseSize = 20;

	int parameter[20] = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3};
	Iterator maxirights;
	Iterator minibugs;
	INIT_LIST_HEAD(&minibugs);
	INIT_LIST_HEAD(&maxirights);
	
	Tuple* root = (Tuple*)malloc(sizeof(Tuple));
	initRecord(&fail,root);
	TupleNode* node = (TupleNode*)malloc(sizeof(TupleNode));;
	node->tuple = root;
	list_add_tail(&node->iterator,&minibugs);

	Tuple* right = (Tuple*)malloc(sizeof(Tuple));
	right->testCase = &fail;
	right->tupleSize = 0;
	TupleNode* tupleNode2 = (TupleNode*)malloc(sizeof(TupleNode));
	tupleNode2->tuple = right;
	list_add_tail(&tupleNode2->iterator,&maxirights);


	initBugs();
	int bug[3] = {0,2,3};
	int degree = 3;
	injectBugs(bug,degree,&fail);

	identify(parameter,&fail,&minibugs,&maxirights);

	Iterator *curr;
	printf("the minimal bugs are:\n");
	list_for_each(curr, &minibugs){
		Tuple* tuple = (list_entry(curr, TupleNode, iterator))->tuple;
		char str[MAXSTRLEN];
		putTupleToString(tuple,str);
		printf("%s\n",str);

	}

}

int main(){
	testIdentify();
	return 0;
}
