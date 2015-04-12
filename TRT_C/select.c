#include "select.h"
#include "path.h"
#include "tree.h"
#include <stdlib.h>
#include <stdio.h>
static int head = 0;
static int tail = -1;
static int middle = -1;
static int len = 0;

static Tuple** longest;

static Iterator paths;

void initPath(int testCaseSize){
	longest = (Tuple**)malloc(testCaseSize*sizeof(Tuple*));
	INIT_LIST_HEAD(&paths);
}

void selectTuple(Iterator* bugsExsited, Iterator* rightsExsited,Tuple** tuple){
//binary search
//regenerate longest
//get tuple from index
if(head > tail){
	freeTupleList(&paths);
	getLongestPath(bugsExsited,rightsExsited,&paths);
	Iterator * cur;
	len = 0; 
	list_for_each(cur,&paths){
		Tuple* tu = (list_entry(cur,TupleNode,iterator))->tuple;
		longest[len] = tu;
		len++;
	}
	head = 0;
	tail = len-1;
	middle = head;
}else
	middle = (head+tail)>>1;

	if (tail != -1)
		(*tuple) = longest[middle];
}
void rightAfter(Iterator* bugsExsiteds,Iterator* rightsExsiteds){
	tail = middle - 1;
	if(head > tail){
		//add new find right and compress
		Tuple* tuple = (Tuple*)malloc(sizeof(Tuple));
		copyTuple(longest[middle],tuple);
		TupleNode* node = (TupleNode*)malloc(sizeof(TupleNode));
		node->tuple = tuple;
		list_add_tail(&node->iterator,rightsExsiteds);
		compress_r(rightsExsiteds);
		//if there is one tuple before, that is must be the minimal
		int last = middle - 1;
		if( last > 0 ){
			Tuple* tuple2 = (Tuple*)malloc(sizeof(Tuple));
			copyTuple(longest[last],tuple2);
			TupleNode* node2 = (TupleNode*)malloc(sizeof(TupleNode));
			node2->tuple = tuple2;
			list_add_tail(&node2->iterator,bugsExsiteds);
			compress(bugsExsiteds);
		}
	}
}
void wrongAfter(Iterator* bugsExsited,Iterator* rightsExsiteds){
	head = middle + 1;
	if(head > tail){
		//add new find bug and compress
		TupleNode* node = (TupleNode*)malloc(sizeof(TupleNode));
		Tuple* tuple = (Tuple*)malloc(sizeof(Tuple));
		copyTuple(longest[middle],tuple);
		node->tuple = tuple;
		list_add_tail(&node->iterator,bugsExsited);
		compress(bugsExsited);
		int last = middle + 1;
		//if there is one tuple before, that is must be the maximal
		if( last < len ){
			Tuple* tuple2 = (Tuple*)malloc(sizeof(Tuple));
			copyTuple(longest[last],tuple2);
			TupleNode* node2 = (TupleNode*)malloc(sizeof(TupleNode));
			node2->tuple = tuple2;
			list_add_tail(&node2->iterator,rightsExsiteds);
			compress_r(rightsExsiteds);
		}

	}

}
