package main

//import (
//	"bufio"
//	"fmt"
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
//	m, n := readInt32(), readInt32()
//	a := transposeMatrix(scanMatrix(m, n))
//	b := scanMatrix(m, n)
//	res := make(result)
//	res.update(a, false)
//	res.update(b, true)
//	fmt.Println(len(res))
//	for val := range res {
//		fmt.Printf("%d %d\n", val.a, val.b)
//	}
//}
//
///*  Helper  */
//
//type vector []int
//type matrix []vector
//type vectorPair []pair
//type result map[pair]bool
//type pair struct {
//	a, b int
//}
//
//func newPair(a int, b int) pair {
//	return pair{a, b}
//}
//
//func scanMatrix(m int, n int) matrix {
//	res := make(matrix, m, m)
//	for i := 0; i < m; i++ {
//		res[i] = make(vector, n, n)
//		for j := 0; j < n; j++ {
//			res[i][j] = readInt32()
//		}
//	}
//	return res
//}
//
//func transposeMatrix(mat matrix) matrix {
//	m := len(mat)
//	n := len(mat[0])
//	res := make(matrix, n, n)
//	for i := 0; i < n; i++ {
//		res[i] = make(vector, m, m)
//		for j := 0; j < m; j++ {
//			res[i][j] = mat[j][i]
//		}
//	}
//	return res
//}
//
//func (res *result) update(m matrix, isSecondIter bool) {
//	tmpRes := make(result)
//	for i := 0; i < len(m); i++ {
//		tmp := m[i][0]
//		tmpArr := make(vectorPair, 0)
//		for j := 0; j < len(m[i]); j++ {
//			if m[i][j] >= tmp {
//				if m[i][j] > tmp {
//					tmp = m[i][j]
//					tmpArr = make(vectorPair, 0)
//				}
//				if !isSecondIter {
//					tmpArr = append(tmpArr, newPair(j+1, i+1))
//				} else {
//					tmpArr = append(tmpArr, newPair(i+1, j+1))
//				}
//			}
//		}
//		for _, val := range tmpArr {
//			tmpRes[val] = true
//		}
//	}
//	if isSecondIter {
//		for key := range *res {
//			if !tmpRes[key] {
//				delete(*res, key)
//			}
//		}
//	} else {
//		for key := range tmpRes {
//			(*res)[key] = true
//		}
//	}
//}
//
///*   Math   */
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
