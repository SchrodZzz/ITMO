package main

//
//import (
//	"bufio"
//	"fmt"
//	"math"
//	"os"
//	"strconv"
//	"strings"
//)
//
//const bufSize = 8196
//const isStdIO = true
//
//const prefix = ""
//const fileNameIn = "input.txt"
//const fileNameOut = "output.txt"
//
//func solve() {
//	n := readInt32()
//	result := 0
//	for i := 0; i < n; i++ {
//		v := readInt64()
//		a := (int64)(math.Sqrt(float64(v)))
//		b := a * a
//		for b != v {
//			v -= a + 1
//			if b > v {
//				a -= 1
//				b = a * a
//			}
//		}
//		result ^= (int)(a)
//	}
//
//	if result == 0 {
//		fmt.Println("Second")
//	} else {
//		fmt.Println("First")
//	}
//}
//
///*  Helper  */
//
//// nothing
//
///*   Main   */
//
//func main() {
//	if isStdIO {
//		in = bufio.NewReaderSize(os.Stdin, bufSize)
//		out = bufio.NewWriter(os.Stdout)
//	} else {
//		inFile, _ := os.Open(fmt.Sprintf("%s%s", prefix, fileNameIn))
//		outFile, _ := os.Create(fmt.Sprintf("%s%s", prefix, fileNameOut))
//		in = bufio.NewReaderSize(inFile, bufSize)
//		out = bufio.NewWriter(outFile)
//		defer inFile.Close()
//		defer outFile.Close()
//	}
//
//	solve()
//}
//
///*   Scanner   */
//
//var in *bufio.Reader
//var out *bufio.Writer
//var stringParts []string
//
//func readLine() string {
//	str, _, _ := in.ReadLine()
//	return string(str)
//}
//
//func readNext() string {
//	if len(stringParts) == 0 {
//		stringParts = strings.Split(readLine(), " ")
//	}
//	res := stringParts[0]
//	stringParts = stringParts[1:]
//	return res
//}
//
//func readInt32() int {
//	res, _ := strconv.ParseInt(readNext(), 10, 32)
//	return int(res)
//}
//
//func readInt64() int64 {
//	res, _ := strconv.ParseInt(readNext(), 10, 64)
//	return res
//}
//
//func readFloat32() float32 {
//	res, _ := strconv.ParseFloat(readNext(), 32)
//	return float32(res)
//}
//
//func readFloat64() float64 {
//	res, _ := strconv.ParseFloat(readNext(), 32)
//	return res
//}
//
//func writeString(s interface{}) {
//	out.WriteString(fmt.Sprintf("%v ", s))
//	out.Flush()
//}
//
//func writeSlice(s interface{}) {
//	writeString(strings.Trim(strings.Join(strings.Split(fmt.Sprint(s), " "), "\n"), "[]"))
//}
//
//func ln() {
//	out.Write([]byte{'\n'})
//}
