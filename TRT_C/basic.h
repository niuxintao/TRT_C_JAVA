#ifndef __BASIC_H
#define __BASIC_H
#include <stdlib.h>
#include <string.h>
#define CombineStack struct COMBINE
void itoa(int num, char* str);

struct COMBINE {
	int stackSize;
	int maxSize;
	int* stack;
};

void initCombine(CombineStack* stack ,int degree);
void push(CombineStack* stack,int value);
int pop(CombineStack* stack);
void freeStack(CombineStack* stack);
#endif
