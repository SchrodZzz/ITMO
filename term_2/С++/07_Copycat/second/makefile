override CXXFLAGS += -std=c++17
override LDFLAGS  += -lm -o1 -g

MAIN_OBJ 	 := main.o
CXX      	 ?= g++

all: main clean

main: $(MAIN_OBJ)
	${CXX} ${CXXFLAGS} ${LDFLAGS} -o $@ $^
	@echo "\n >>>Test #2<<< \n"
	valgrind --gen-suppressions=all --leak-check=full \
	--track-origins=yes --log-file="leaks_second.log" ./main
	@echo "\n\n"

clean:
	rm -rf *o *a main main_only
