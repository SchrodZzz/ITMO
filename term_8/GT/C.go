package main

//
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
//	n, m := readInt32(), readInt32()
//	graph := make([]*Vertex, n, n)
//	for i := 0; i < n; i += 1 {
//		graph[i] = newVertex()
//	}
//	for i := 0; i < m; i += 1 {
//		x, y := readInt32()-1, readInt32()-1
//		graph[x].edges = append(graph[x].edges, y)
//	}
//	for _, v := range graph {
//		if !v.isUsed {
//			dfs(v, graph)
//		}
//	}
//
//	for _, v := range graph {
//		fmt.Println(v.val)
//	}
//}
//
///*  Helper  */
//
//func dfs(v *Vertex, graph []*Vertex) {
//	v.isUsed = true
//	for _, i := range v.edges {
//		u := graph[i]
//		if !u.isUsed {
//			dfs(u, graph)
//		}
//	}
//	v.val = mex(v.edges, graph)
//}
//
//func mex(edges []int, graph []*Vertex) int {
//	set := make(map[int]bool)
//	for _, i := range edges {
//		set[graph[i].val] = true
//	}
//	i := 0
//	for ; set[i]; i += 1 {
//	}
//	return i
//}
//
///*   Vertex   */
//
//type Vertex struct {
//	edges  []int
//	val    int
//	isUsed bool
//}
//
//func newVertex() *Vertex {
//	return &Vertex{
//		[]int{},
//		0,
//		false,
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
