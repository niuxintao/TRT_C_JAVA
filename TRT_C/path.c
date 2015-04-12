#include "path.h"
#include "testObject.h"
#include "tree.h"
#include <stdio.h>
#include <stdlib.h>

void catComm(Tuple* A, Tuple* B, Tuple* to){
	int Aindex = 0;
	int Bindex = 0;
	int size = 0;

	while( Aindex < A->tupleSize || Bindex < B->tupleSize){
		if(Aindex == A->tupleSize){
			Bindex++;
		}else if(Bindex == B->tupleSize){
			Aindex++;
		}else if(A->paramIndexPtr[Aindex] > B->paramIndexPtr[Bindex]){
			Bindex++;
		}else if(A->paramIndexPtr[Aindex] < B->paramIndexPtr[Bindex]){
			Aindex++;
		}else if(A->paramIndexPtr[Aindex] == B->paramIndexPtr[Bindex]){
			Aindex++;
			Bindex++;
		}
		size++;
	}
	initTuple(to, size , A->testCase);
	int i;
	Aindex = 0;
	Bindex = 0;

	for( i = 0 ; i < size ; i++){
		if(Aindex == A->tupleSize){
			to->paramIndexPtr[i] = B->paramIndexPtr[Bindex];
			Bindex++;
		}else if(Bindex == B->tupleSize){
			to->paramIndexPtr[i] = A->paramIndexPtr[Aindex];
			Aindex++;
		}else if(A->paramIndexPtr[Aindex] > B->paramIndexPtr[Bindex]){
			to->paramIndexPtr[i] = B->paramIndexPtr[Bindex];
			Bindex++;
		}else if(A->paramIndexPtr[Aindex] < B->paramIndexPtr[Bindex]){
			to->paramIndexPtr[i] = A->paramIndexPtr[Aindex];
			Aindex++;
		}else if(A->paramIndexPtr[Aindex] == B->paramIndexPtr[Bindex]){
			to->paramIndexPtr[i] = A->paramIndexPtr[Aindex];
			Aindex++;
			Bindex++;
		}
	}

}

void getItr(Tuple* bug,Iterator*  lastItr, Iterator* thisItr){
	Iterator childs, *ch;
	INIT_LIST_HEAD(&childs);
	//at least not contain one element of the original bug tuple
	getChildTuplesByDegree(1,bug,&childs);
	//cat the contcat tuple
	list_for_each(ch,&childs){
		Tuple* child = (list_entry(ch,TupleNode,iterator))->tuple;
		if(list_empty(lastItr)){	
			Tuple* temp = (Tuple*)malloc(sizeof(Tuple));
			copyTuple(child,temp);
			TupleNode* item = (TupleNode*)malloc(sizeof(TupleNode));
			item->tuple = temp;
			list_add_tail(&item->iterator,thisItr);
		}else{
			Iterator * cur;
			list_for_each(cur, lastItr){
				Tuple* itr = (list_entry(cur,TupleNode,iterator))->tuple;
				Tuple* catTemp = (Tuple*)malloc(sizeof(Tuple));
				catComm(child,itr,catTemp);
				TupleNode* item = (TupleNode*)malloc(sizeof(TupleNode));
				item->tuple = catTemp;
				list_add_tail(&item->iterator,thisItr);
			}
		}
	}
	freeTupleList(&childs);
}

void getLongestCandidateTails(Iterator* rightExsited,Iterator* rights){
	Iterator* cur;
	Iterator lastItr;
	INIT_LIST_HEAD(&lastItr);
	list_for_each(cur, rightExsited){
		Tuple* right = (list_entry(cur, TupleNode, iterator))->tuple;
		Tuple* reverse = (Tuple*)malloc(sizeof(Tuple));
		getReverseTuple(right, reverse);
		Iterator thisItr;
		INIT_LIST_HEAD(&thisItr);
		getItr(reverse,&lastItr,&thisItr);

		compress(&thisItr);
		//let lastItr = thisItr
		freeTupleList(&lastItr);
		list_add_tail(&lastItr,&thisItr);
		list_del(&thisItr);
		freeTuple(reverse);
	}
	list_for_each(cur, &lastItr){
		Tuple* child = (list_entry(cur,TupleNode,iterator))->tuple;
		TupleNode* item = (TupleNode*)malloc(sizeof(TupleNode));
		item->tuple = child;
		list_add_tail(&item->iterator,rights);
	}
}

void getLongestCandidateHeads(Iterator* bugsExsited,Iterator* heads){
	Iterator* cur;
	Iterator lastItr;
	INIT_LIST_HEAD(&lastItr);

	list_for_each(cur, bugsExsited){
		Tuple* bug = (list_entry(cur, TupleNode, iterator))->tuple;
		Iterator thisItr;
		INIT_LIST_HEAD(&thisItr);
		getItr(bug,&lastItr,&thisItr);
		compress(&thisItr);
		//let lastItr = thisItr
		freeTupleList(&lastItr);
		list_add_tail(&lastItr,&thisItr);
		list_del(&thisItr);
	}
	list_for_each(cur, &lastItr){
		Tuple* child = (list_entry(cur,TupleNode,iterator))->tuple;
		Tuple* reverse = (Tuple*)malloc(sizeof(Tuple));
		getReverseTuple(child, reverse);
		TupleNode* item = (TupleNode*)malloc(sizeof(TupleNode));
		item->tuple = reverse;
		list_add_tail(&item->iterator,heads);
	}
	freeTupleList(&lastItr);
}

int lenBetween(Tuple* A, Tuple* B){
	if(isTupleContain(A,B) == 0)
		return 0;
	int size = A->tupleSize - B->tupleSize;
	return size;
}

void generateMaxPaths(Tuple* head, Tuple* tail, Iterator* paths){
	if(head == NULL || tail == NULL)
		return;
	int headSize = head->tupleSize;
	int tailSize = tail->tupleSize;
	int size = headSize;

	while(size >= tailSize){
		Tuple* tuple = (Tuple*)malloc(sizeof(Tuple));
		initTuple(tuple,size,head->testCase);
		
		int shSize  = 0;
		int Aindex = 0;
		int Bindex = 0;
		int i;
		for(i = 0; i < size ; i ++){
			if(shSize == size - tailSize){
				tuple->paramIndexPtr[i] = tail->paramIndexPtr[Bindex];
				Bindex++;
				continue;
			}
			if(Bindex == tailSize){
				tuple->paramIndexPtr[i] = head->paramIndexPtr[Aindex];
				Aindex++;
				shSize++;
				continue;
			}
			if(head->paramIndexPtr[Aindex] == tail->paramIndexPtr[Bindex]){
				tuple->paramIndexPtr[i] = tail->paramIndexPtr[Bindex];
				Bindex++;
				Aindex++;
			}else{
				tuple->paramIndexPtr[i] = head->paramIndexPtr[Aindex];
				Aindex++;
				shSize++;
			}
		}

		TupleNode* item = (TupleNode*)malloc(sizeof(TupleNode));
		item->tuple = tuple;
		list_add_tail(&item->iterator,paths);
		size--;
	}

}

void getLongestPath(Iterator* minimalBugsExisted,Iterator* maximalRightsExisted,Iterator* paths){
	Iterator heads;
	INIT_LIST_HEAD(&heads);
	Iterator tails;
	INIT_LIST_HEAD(&tails);
	getLongestCandidateHeads(minimalBugsExisted,&heads);
	getLongestCandidateTails(maximalRightsExisted,&tails);

/*  Iterator * cur;
    printf("heads start : \n");
	list_for_each(cur,&heads){
		Tuple* tuple = (list_entry(cur,TupleNode,iterator))->tuple;
		char str[500];
		putTupleToString(tuple,str);
		printf("the tuple is : %s\n",str);
	}

	printf("tails start : \n");
	list_for_each(cur,&tails){
		Tuple* tuple = (list_entry(cur,TupleNode,iterator))->tuple;
		char str[500];
		putTupleToString(tuple,str);
		printf("the tuple is : %s\n",str);
	}*/

	Tuple* maxHead;
	Tuple* maxTail;
	int max = 0;
	
	Iterator *head;
	list_for_each(head,&heads){
		Tuple *A = (list_entry(head,TupleNode,iterator))->tuple;
		Iterator *tail;
		list_for_each(tail,&tails){
			Tuple *B = (list_entry(tail,TupleNode,iterator))->tuple;
			int size = lenBetween(A,B);
			if(size > max){
				max = size;
				maxHead = A;
				maxTail = B;
			}
		}
	}

	generateMaxPaths(maxHead,maxTail,paths);

	freeTupleList(&heads);
	freeTupleList(&tails);
}
