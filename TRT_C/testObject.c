#include "testObject.h"
#include <string.h>
#include <stdio.h>
#include "basic.h"
#define MAXSTRING 500
boolean isTestCaseEqual(TestCase* t_a , TestCase* t_b){
	boolean result = false;
	int size = 0;
	if ( (size = t_a->testCaseSize) == t_b->testCaseSize){
		while(size > 0){
			if(t_a->testCasePtr[size-1] != t_b->testCasePtr[size-1])
				break;
			size--;
		}
		if(size == 0)
			result = true;
	}
	return result;
}

void putTestCaseToString(TestCase* t , char* str){
	str[0] = '\0';
	int i = 0;
	for(;i < t->testCaseSize; i++){
		char arg[MAXSTRING];
		itoa(t->testCasePtr[i],arg);
		strcat(str, arg);
		strcat(str, "\t");
	}
	switch(t->testInfo){
		case FAILED:
			strcat(str,"FAILED");
		break;
		case PASSED:
			strcat(str, "PASSED");
		break;
		default:
			strcat(str, "UNTESTED");
		break;
	}
}

boolean isTestCaseContain(TestCase* t , Tuple* tuple){
	boolean rs = false;
	if(tuple->tupleSize <= t->testCaseSize){
		int i = 0;
		for(; i < tuple->tupleSize; i++){
			int index = tuple->paramIndexPtr[i];
			int value = tuple->testCase->testCasePtr[index];
			int valueThis = t->testCasePtr[index];
			if(value != valueThis)
				break;
		}
		if(i == tuple->tupleSize)
			rs = true;
	}
	return rs;
}

int isTupleContain(Tuple* A , Tuple* B){
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

	if(size == A->tupleSize)
		return 1;
	else if ( size == B->tupleSize)
		return -1;
	return 0;
}

void putTupleToString(Tuple* tuple, char* str){
	str[0] = '\0';
	strcat(str,"[");
	int before = -1;
	int i = 0;
	int now;
	for(; i < tuple->tupleSize + 1; i++){
		now = i == tuple->tupleSize ? tuple->testCase->testCaseSize : tuple->paramIndexPtr[i];
		int margin = now - before -1;
		while(margin > 0){
			strcat(str,"-\t");
			margin--;
		}
		if(i < tuple->tupleSize){
			char strTemp[MAXSTRING];
			itoa(tuple->testCase->testCasePtr[now],strTemp);
			strcat(str, strTemp);
		}
		strcat(str, i == tuple->tupleSize ? "]" : "\t");
		before = now;
	}
}

boolean isTupleEqual(Tuple* t_a , Tuple* t_b){
	boolean rs = false;
	int size = 0;
	if((size = t_a->tupleSize) == t_b->tupleSize){
		int i ;
		for(i = 0 ; i < size ; i++){
			int index ;
			if((index = t_a->paramIndexPtr[i]) == t_b->paramIndexPtr[i]){
				if(t_a->testCase->testCasePtr[index] != t_b->testCase->testCasePtr[index])
					break;
			}
			else 
				break;
		}
		if(i == size)
			rs = true;
	}
	return rs;
}

void initTuple(Tuple* tuple , int degree, TestCase* testCase){
	tuple->tupleSize = degree;
	tuple->paramIndexPtr = (int*) malloc(degree*sizeof(int));
	tuple->testCase = testCase;
}
void copyTuple(Tuple* from , Tuple* to){
	to->tupleSize = from->tupleSize;
	to->paramIndexPtr = (int*)malloc(to->tupleSize*sizeof(int));
	int i;
	for(i = 0; i < to->tupleSize; i++){
		to->paramIndexPtr[i] = from->paramIndexPtr[i];
	}
	to->testCase = from->testCase;
}

void freeTuple(Tuple* tuple){
	if(tuple->tupleSize > 0)
		free(tuple->paramIndexPtr);
	free(tuple);
}
void freeTestCase(TestCase* testCase){
	free(testCase->testCasePtr);
	free(testCase);
}

void freeTupleList(Iterator* head){
	 Iterator* curr = head->next;
	 while(curr != head){
		 Iterator* temp = curr;
		 curr = temp->next;
		 list_del(temp);
		 TupleNode* node = list_entry(temp, TupleNode, iterator);
		 freeTuple(node->tuple);
		 free(node);
	 }
}

void generateTupleFromStack(CombineStack* stack , Tuple* from, Tuple* to){
	to->tupleSize = stack->maxSize;
	to->testCase = from->testCase;
	int i;
	to->paramIndexPtr = (int*)malloc(to->tupleSize*sizeof(int));
	for(i = 0 ; i < to->tupleSize ; i++){
		to->paramIndexPtr[i] = from->paramIndexPtr[stack->stack[i]];
	}
}
void getChildTuplesByDegree(int degree,Tuple* father,  Iterator* head){
	CombineStack stack;
	initCombine(&stack , degree);
	if (degree == 0)
		return ;
	int currentIndex = 0;
	
	while(true){
		if(stack.stackSize == stack.maxSize){
			TupleNode* item = (TupleNode*)malloc(sizeof(TupleNode));
			Tuple* data = (Tuple*)malloc(sizeof(Tuple));
			generateTupleFromStack(&stack,father,data);
			item->tuple = data;
			list_add_tail(&item->iterator,head);
			pop(&stack);
		}else if(currentIndex == father->tupleSize){
			if(stack.stackSize == 0)
				break;
			currentIndex = pop(&stack) + 1;
		}else {
			push(&stack, currentIndex);
			currentIndex++;
		}
	}
	free(stack.stack);
}

void getReverseTuple(Tuple* tuple, Tuple* reverse){
	reverse->testCase = tuple->testCase;
	reverse->tupleSize = tuple->testCase->testCaseSize - tuple->tupleSize;
	reverse->paramIndexPtr = (int*)malloc(reverse->tupleSize*sizeof(int));
	int currentIndex = 0;
	int tupleIndex = 0;
	int i;
	for(i = 0 ; i < tuple->testCase->testCaseSize ; i++){
		if(tupleIndex == tuple->tupleSize || i!= tuple->paramIndexPtr[tupleIndex]){
			reverse->paramIndexPtr[currentIndex] = i;
			currentIndex++;
		}else
			tupleIndex++;
	}
}

void cat(Tuple* A , Tuple* B , Tuple* to){
	initTuple(to,A->tupleSize + B->tupleSize , A->testCase);
	int Aindex = 0;
	int Bindex = 0;
	int i;
	for(i = 0 ; i < to->tupleSize; i++){
		if(Aindex == A->tupleSize){
			to->paramIndexPtr[i] = B->paramIndexPtr[Bindex];
			Bindex++;
			continue;
		}
		if(Bindex == B->tupleSize){
			to->paramIndexPtr[i] = A->paramIndexPtr[Aindex];
			Aindex++;
			continue;
		}
		if(A->paramIndexPtr[Aindex] > B->paramIndexPtr[Bindex]){
			to->paramIndexPtr[i] = B->paramIndexPtr[Bindex];
			Bindex++;
		}else{
			to->paramIndexPtr[i] = A->paramIndexPtr[Aindex];
			Aindex++;
		}
	}
}

void getFatherTuplesByDegree(int degree , Tuple* child, Iterator* fatherHead){
	Tuple* reverse = (Tuple*)malloc(sizeof(Tuple));
	getReverseTuple(child, reverse);
	Iterator reverseChild ;
	INIT_LIST_HEAD(&reverseChild);
	getChildTuplesByDegree(degree - child->tupleSize , reverse, &reverseChild);
	Iterator* iterator;
	list_for_each(iterator,&reverseChild){
		Tuple* catTemp = (Tuple*)malloc(sizeof(Tuple));
		Tuple* A = child;
		Tuple* B = (list_entry(iterator,TupleNode,iterator))->tuple;
		cat(A,B,catTemp);
		TupleNode* item = (TupleNode*)malloc(sizeof(TupleNode));
		item->tuple = catTemp;
		list_add_tail(&item->iterator,fatherHead);
	}
	freeTupleList(&reverseChild);
	freeTuple(reverse);
}

void getAllChildTuples(Tuple* father, Iterator* head){
	int degree = father->tupleSize - 1;
	for(;degree > 0 ; degree--)
		getChildTuplesByDegree(degree, father, head);
}

void getAllFatherTuples(Tuple* child, Iterator* head){
	int degree = child->tupleSize + 1;
	for(; degree <= child->testCase->testCaseSize ; degree++)
		getFatherTuplesByDegree(degree, child, head);
}

boolean isMinimal(Tuple* tuple,Iterator* head){
	boolean result = true;
	Iterator* cur = head->next;
	while(cur != head){
		TupleNode* node = list_entry(cur,TupleNode,iterator);
		Tuple* t = node->tuple;
		cur = cur->next;
		if(tuple == t)
			continue;
		if(isTupleContain(tuple,t) == 1){
			result = false;
			break;
		}
	}
	return result;
}
	
boolean isMaximal(Tuple* tuple,Iterator* head){
	boolean result = true;
	Iterator* cur = head->next;
	while(cur != head){
		TupleNode* node = list_entry(cur,TupleNode,iterator);
		Tuple* t = node->tuple;
		cur = cur->next;
		if(tuple == t)
			continue;
		if(isTupleContain(t,tuple) == 1){
			result = false;
			break;
		}
	}
	return result;
}

void compress(Iterator* head){
	Iterator* cur = head->next;
	while(cur != head){
		Iterator* temp = cur;
		cur = temp->next;
		TupleNode* node = list_entry(temp,TupleNode,iterator);
		Tuple* tuple = node->tuple;
		if(isMinimal(tuple,head) == false){
			list_del(temp);
			freeTuple(tuple);
			free(node);
		}
	}
}
void compress_r(Iterator* head){
	Iterator* cur = head->next;
	while(cur != head){
		Iterator* temp = cur;
		cur = temp->next;
		TupleNode* node = list_entry(temp,TupleNode,iterator);
		Tuple* tuple = node->tuple;
		if(isMaximal(tuple,head) == false){
			list_del(temp);
			freeTuple(tuple);
			free(node);
		}
	}
}
