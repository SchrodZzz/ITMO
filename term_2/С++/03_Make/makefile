override CXXFLAGS += -std=c++17
override LDFLAGS  += -lm

DEFINE_NAME  := -DNAME=\"Bob\"
PLATFORM     := $(shell uname)
MAIN_OBJ 	 := main.o
LIB_NAME	 := mylib
LIBVER   	 := 5.1.10
OBJS     	 := hello.o here.o bye.o
CXX      	 ?= g++

vpath %.cpp src

all: main

main: $(MAIN_OBJ) $(LIB_NAME).a
	${CXX} ${CXXFLAGS} ${LDFLAGS} -o $@ $^

mylib.a: $(OBJS)
	ar rcs $@ $^

dyn_lib_mac: $(OBJS)
	${CXX} ${CXXFLAGS} -dynamiclib -current_version ${LIBVER} \
	-compatibility_version 1.0 -o lib${LIB_NAME}.dylib $^

dyn_lib_linux: $(MAIN_OBJ) $(OBJS)
	${CXX} ${CXXFLAGS} -shared -fPIC -pie -L -Wl,-soname=libmylib.so.5 -o \
	lib${LIB_NAME}.so.${LIBVER} $^

main.o: main.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $^

hello.o: hello.cpp
	${CXX} ${CXXFLAGS} ${DEFINE_NAME} -c -o $@ $^

here.o: here.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $^

bye.o: bye.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $^

test: main
	@echo "\n >>>Test<<< \n"
	./main
ifeq ($(PLATFORM), Darwin)
	@echo "\n >MacOS lib< \n"
	make dyn_lib_mac
	otool -L lib${LIB_NAME}.dylib
	@echo "~~~~~~~~~~~~~~~~~~~~~~~~~~"
	${CXX} ${CXXFLAGS} -o $@ ${MAIN_OBJ} -L. -l${LIB_NAME}
	./$@
	rm $@
else ifeq ($(PLATFORM), Linux)
	@echo "\n >Linux lib< \n"
	make dyn_lib_linux
	./lib${LIB_NAME}.so.${LIBVER}
else
	@echo "\n\t>unsopprted OS -_-\n"
endif
	make clean
	@echo "\n DONE!!! \n"


clear_objs:
	rm -rf *o

clean:
	rm -rf *o *a lib${LIB_NAME}.* main qq
