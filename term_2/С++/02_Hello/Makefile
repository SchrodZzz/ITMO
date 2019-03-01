all: result

result: main.o hello.o here.o bye.o
		g++ -std=c++17 -o result main.o hello.o here.o bye.o

main.o: main.cpp
		g++ -std=c++17 -c main.cpp

hello.o: hello.cpp
		g++ -std=c++17 -DNAME=\"Mik\" -c hello.cpp

here.o: here.cpp
		g++ -std=c++17 -c here.cpp

bye.o: bye.cpp
		g++ -std=c++17 -c bye.cpp

clean:
		rm -rf *o result
