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
//		graph[y].edges = append(graph[y].edges, x)
//		graph[x].dgr += 1
//	}
//	for _, v := range graph {
//		if !v.isUsed && v.dgr == 0 {
//			v.looses = true
//			dfs(v, graph)
//		}
//	}
//
//	res := make([]string, n, n)
//	for i, v := range graph {
//		res[i] = "Draw"
//		if v.wins {
//			res[i] = "Win"
//		} else if v.looses {
//			res[i] = "Loss"
//		}
//		fmt.Println(res[i])
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
//			if v.looses {
//				u.wins = true
//				dfs(u, graph)
//			} else {
//				u.dgr -= 1
//				if u.dgr == 0 {
//					u.looses = true
//					dfs(u, graph)
//				}
//			}
//		}
//	}
//}
//
///*   Vertex   */
//
//type Vertex struct {
//	edges  []int
//	dgr    int
//	isUsed bool
//	wins   bool
//	looses bool
//}
//
//func newVertex() *Vertex {
//	return &Vertex{
//		[]int{},
//		0,
//		false,
//		false,
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
