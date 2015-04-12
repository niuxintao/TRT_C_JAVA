#include "basic.h"
void reverse(char s[])
{
	int c, i, j;
	for (i = 0, j = strlen(s)-1; i < j; i++, j--) {
		c = s[i];
		s[i] = s[j];
		s[j] = c;
	}
}
void itoa(int n, char s[])
{
	int i, sign;
	if ((sign = n) < 0) /* record sign */
		n = -n;
		/* make n positive */
		i = 0;
		do {
			/* generate digits in reverse order */
			s[i++] = n % 10 + '0'; /* get next digit */
		} while ((n /= 10) > 0);
		/* delete it */
		if (sign < 0)
			s[i++] = '-';
			s[i] = '\0';
			reverse(s);
}

void initCombine(CombineStack* stack, int degree){
	stack->stackSize = 0;
	stack->maxSize = degree;
	stack->stack = (int*)malloc(degree*sizeof(int));
}

void push(CombineStack* stack , int value){
	if(stack->stackSize < stack->maxSize){
		stack->stack[stack->stackSize] = value;
		stack->stackSize++;
	}
}

int pop(CombineStack* stack){
	if(stack->stackSize > 0)
		stack->stackSize--;
	return stack->stack[stack->stackSize];
	
}

void freeStack(CombineStack* stack){
	free(stack->stack);
	free(stack);
}
