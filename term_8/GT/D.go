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
//	n := math.MinInt32
//	dims := make([]int, 3, 3)
//	for i := range dims {
//		dims[i] = readInt32()
//		if dims[i] > n {
//			n = dims[i]
//		}
//	}
//
//	grundy := evalGrundy(n)
//	res := calcAnswer(dims, grundy)
//	if !res {
//		fmt.Println("NO")
//	} else {
//		fmt.Println("YES")
//		for i := 0; i < 3; i++ {
//			for j := 0; j <= dims[i]/2; j++ {
//				res := 0
//				for k := 0; k < 3; k++ {
//					res ^= grundy[dims[k]-calcDelta(i, j, k)]
//				}
//				if res == 0 {
//					for k := 0; k < 3; k++ {
//						fmt.Print(dims[k] - calcDelta(i, j, k))
//						fmt.Print(" ")
//					}
//					return
//				}
//			}
//		}
//	}
//}
//
///*  Helper  */
//
//func markKthGrundy(vals *[]int, k int) {
//	size := k / 2
//	used := make([]bool, size, size)
//	for i := range used {
//		used[i] = false
//	}
//	for i := k - size; i < k; i += 1 {
//		if (*vals)[i] < size {
//			used[(*vals)[i]] = true
//		}
//	}
//	for i := 0; i < size; i++ {
//		if !used[i] {
//			(*vals)[k] = i
//			return
//		}
//	}
//	(*vals)[k] = size
//}
//
//func evalGrundy(n int) []int {
//	res := make([]int, n+1, n+1)
//	for k := 2; k < n+1; k += 1 {
//		markKthGrundy(&res, k)
//	}
//
//	return res
//}
//
///*   Math   */
//
//func calcDelta(i int, j int, k int) int {
//	res := 0
//	if k == i {
//		res = j
//	}
//	return res
//}
//
//func calcAnswer(dims []int, vals []int) bool {
//	res := 0
//	for _, d := range dims {
//		res ^= vals[d]
//	}
//	return res != 0
//}
//
//func int2bool(val int) bool {
//	return val != 0
//}
//
//func bool2int(v bool) int {
//	if v {
//		return 1
//	}
//	return 0
//}
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
