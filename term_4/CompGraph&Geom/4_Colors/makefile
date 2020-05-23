override CXXFLAGS += -std=c++17
override LDFLAGS  += -lm

RED				='\033[0;31m'
NC				='\033[0m'
CXX      		?= g++

all: main

main: main.o PNM.o
	${CXX} ${CXXFLAGS} ${LDFLAGS} -o $@ $^

main.o: main.cpp
	${CXX} ${CXXFLAGS} -c -o $@ $^

PNM.o: PNM.cpp
	${CXX} ${CXXFLAGS} ${DEFINE_NAME} -c -o $@ $^

test: main
	./main ${ARGS}
	make clean

chain: main
	./main -f RGB -t HSL -i 1 ${INIT_FILE_NAME}.ppm -o 1 hsl.ppm
	./main -f HSL -t HSV -i 1 hsl.ppm -o 1 hsv.ppm
	./main -f HSV -t YCbCr.601 -i 1 hsv.ppm -o 1 601.ppm
	./main -f YCbCr.601 -t YCbCr.709 -i 1 601.ppm -o 1 709.ppm
	./main -f YCbCr.709 -t YCoCg -i 1 709.ppm -o 1 ycocg.ppm
	./main -f YCoCg -t CMY -i 1 ycocg.ppm -o 1 cmy.ppm
	./main -f CMY -t RGB -i 1 cmy.ppm -o 1 rgb.ppm
	make clean

info:
	@echo "Execute with command: ${RED}make test ARGS=\"-f <from_color_space> -t <to_color_space> -i <count> <input_file_name> -o <count> <output_file_name>\"${NC}"
	@echo "Example: make test ARGS=\"-f RGB -t YCbCr.601 -i 1 img.ppm -o 1 res.ppm\""
	@echo "Also you can generate all the tranforms with command: ${RED}make chain INIT_FILE_NAME=\"<init_file_name w/o extension>\"${NC}"
	@echo "Example: make chain INIT_FILE_NAME=\"turtle\""

clean:
	rm -rf *o main
