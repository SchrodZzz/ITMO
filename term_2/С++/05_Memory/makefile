override CXXFLAGS += -std=c++17
override LDFLAGS  += -lm -o1 -g

LIB_NAME	 := mylib
MAIN_OBJ 	 := main.o
OBJS     	 := Util.o Test.o TestUtil.o SwapMinTest.o GetLineTest.o ResizeTest.o
CXX      	 ?= g++

vpath %.cpp sourses

all: main clean

main: $(MAIN_OBJ) $(LIB_NAME).a
	${CXX} ${CXXFLAGS} ${LDFLAGS} -o $@ $^
	valgrind --gen-suppressions=all --leak-check=full --show-leak-kinds=all \
	--track-origins=yes --log-file="leaks.log" ./main

mylib.a: $(OBJS)
	ar rcs $@ $^

clear_stuff:
	rm -rf *o *a

clean:
	rm -rf *o *a main main_only
