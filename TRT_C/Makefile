CC=gcc
CFLAGS=-Wall
OBJ = basic.o  testObject.o testTestObject.o
OBJ2 = testList.o
OBJ3 = basic.o testObject.o testPath.o tree.o path.o select.o generate.o
OBJ4 = basic.o testObject.o tree.o path.o select.o generate.o testIdentify.o
DEPS = basic.h list.h testObject.h 

all : testObject testList testPath testIdentify

testObject : $(OBJ)
	$(CC) -o $@ $^
basic.o : basic.h
testObject.o : testObject.h

testList : $(OBJ2)
	$(CC) -o $@ $^
testList.o : list.h

testPath : $(OBJ3)
	$(CC) -o $@ $^
path.o : path.h tree.h
tree.o : tree.h select.h generate.h
testPath.o : path.h tree.h
select.o : select.h path.h tree.h
generate.o : generate.h

testIdentify : $(OBJ4)
path.o : path.h tree.h
tree.o : tree.h select.h generate.h
select.o : select.h path.h tree.h
generate.o : generate.h
testIdentify.o : tree.h generate.h

.PHONY: clean
clean:
	rm *.o testObject testList testPath testIdentify
