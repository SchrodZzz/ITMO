package main

import (
	"bufio"
	"fmt"
	"math"
	"math/rand"
	"os"
	"strconv"
	"strings"
)

const bufSize = 8196
const errSize = -1337

const prefix = ""
const fileNameIn = "input.txt"
const fileNameOut = "output.txt"

func solve() {
	n, k := readInt32(), readInt32()
	m:= make(matrix, k, k)
	for i:=0; i < k; i++ {
		m[i] = make(vector, n, n)
		for j:=0; j<n; j++ {
			m[i][j] = readInt32()
		}
	}
	g:= newGraph(&m)
	layers:= make(vector, len(g), len(g))
	for i:= 0; i != len(g); i++ {
		layers[i] = len(g[i])
	}
	writeSlice(layers)
	writeString("\n")

	for word:= readNext(); len(word) > 0; word= readNext() {
		if word == "Encode" {
			word:= make(vector, k, k)
			for i:= 0; i < k; i++ {
				word[i] = readInt32()
			}
			code:= encode(&word, &m)
			writeSlice(code)
		} else if word == "Decode" {
			word:= make(vector_f, n, n)
			for i:= 0; i < n; i++ {
				word[i] = readFloat64()
			}
			code:= decode(&word, &g)
			for i:= 0; i < len(code); i++ {
				v:= 0
				if code[i] <= 0 {
					v = 1
				}
				code[i] = v
			}
			writeSlice(code)
		} else if word == "Simulate" {
			noiseLevel, times, errors := readFloat64(), readInt32(), readInt32()
			sigma:= math.Sqrt(math.Pow(10, -noiseLevel/10) * float64(n) / float64(k) / 2)
			count, err := 0, 0
			for ;count != times && err != errors; count++ {
				if !simulate(&m, &g, sigma) {
					err++
				}
			}
			writeString(float64(err) / float64(count))
		}
		writeString("\n")
	}
}


/*  Helper  */

type PathHolder struct {
	path map[int]int
}

func newNext(active *vector, n int) PathHolder {
	path := make(map[int]int)
	for i:= 0; i != len(*active); i++ {
		val:= int2bool(n & (1 << (len(*active) - i - 1)))
		path[(*active)[i]] = bool2int(val)
	}
	return PathHolder{path}
}

func (next *PathHolder) add(active *vector, flag bool) {
	for i:= 0; i != len(*active); i++ {
		_,exists:= (*next).path[(*active)[i]]
		if !exists {
			(*next).path[(*active)[i]] = bool2int(flag)
		}
	}
}

func (next *PathHolder) weight(m *matrix, index int) bool {
	acc:= 0
	for k, v := range (*next).path {
		acc ^= (*m)[k][index] * v
	}
	return acc == 1
}

func (next *PathHolder) next(active *vector) int {
	acc:= 0
	for _,v := range *active {
		acc = (acc << 1) | (*next).path[v]
	}
	return acc
}


/*   Vertex   */

type Vertex struct {
	origin    *Vertex
	path2Zero *Vertex
	path2One  *Vertex
	err       float64
	isOk      bool
	number    int
}

func newVertex() Vertex {
	return Vertex{
		nil,
		nil,
		nil,
		errSize,
		false,
		0,
	}
}

func (vertex *Vertex) next(signal float64) {
	if vertex.path2One != nil {
		err:= vertex.err - signal
		if vertex.path2One.err < err {
			vertex.path2One.err = err
			vertex.path2One.isOk = true
			vertex.path2One.origin = vertex
		}
	}
	if vertex.path2Zero != nil {
		err:= vertex.err + signal
		if vertex.path2Zero.err < err {
			vertex.path2Zero.err = err
			vertex.path2Zero.isOk = false
			vertex.path2Zero.origin = vertex
		}
	}
}


/*  Vector, Matrix and Graph    */

type vector []int
type vector_f []float64
type matrix []vector
type graph [][]Vertex

func eq(a *vector, b *vector) bool {
	if len(*a) != len(*b) {
		return false
	}
	for i, v := range *a {
		if v != (*b)[i] {
			return false
		}
	}
	return true
}

func (m *matrix) swap(i int, j int) {
	tmp:= (*m)[i]
	(*m)[i] = (*m)[j]
	(*m)[j] = tmp
}

func vectorXOR(l *vector, r *vector) {
	for i:= 0; i != len(*l); i++ {
		(*l)[i] ^= (*r)[i]
	}
}

func findLastInRow(row *vector) int {
	k:= len(*row)
	for i:= 0; i != k; i++ {
		if (*row)[k- i - 1] != 0 {
			return k - i - 1
		}
	}
	return 0
}

func findFirstInRow(row *vector) int {
	for i:=0; i != len(*row); i++ {
		if (*row)[i] != 0 {
			return i
		}
	}
	return 0
}

func getActive(m *matrix) matrix {
	// !!might be problematic
	k:= len((*m)[0])
	n:= len(*m)

	actives:= make(matrix, k, k)
	start:= make(vector, n, n)
	end:= make(vector, n, n)

	for i, v := range *m {
		start[i] = findFirstInRow(&v)
		end[i] = findLastInRow(&v)
	}

	for column:=0; column != k; column++ {
		localActives:= make(vector, 0)
		for row:= 0; row != n; row++ {
			if start[row] <= column && column < end[row] {
				localActives = append(localActives, row)
			}
		}
		actives[column] = localActives
	}

	return actives
}

func generateMinSpan(m *matrix) {
	k:= len((*m)[0])
	n:= len(*m)
	for i, row := 0, 0; i != k && row < n; i++ {
		firstNonEmptyRow:= len(*m)
		for j:=row; j < len(*m); j++ {
			if (*m)[j][i] != 0 {
				firstNonEmptyRow = j
			}
		}
		if firstNonEmptyRow < n {
			m.swap(firstNonEmptyRow, row)
			for k:= row +1; k < n; k++ {
				if (*m)[k][i] != 0 {
					vectorXOR(&(*m)[k], &(*m)[row])
				}
			}
			row++
		}
	}

	helper := make(map[int]int)
	for i, nSize := 0, n; i != nSize; {
		row:= nSize - i - 1
		key:= findLastInRow(&(*m)[row])
		value, exists := helper[key]
		if exists {
			vectorXOR(&(*m)[row], &(*m)[value])
		} else {
			i++
			helper[key] = row
		}
	}
}

func copyMatrix(orig matrix) *matrix {
	cpy:= make(matrix, len(orig))
	for i:= 0; i < len(orig); i++ {
		cpy[i] = make(vector, len(orig[i]))
		copy(cpy[i], orig[i])
	}
	return &cpy
}

func newGraph(m *matrix) graph {
	spanMatrix:= copyMatrix(*m)
	generateMinSpan(spanMatrix)

	actives:= getActive(spanMatrix)
	g:= make(graph, len(actives)+1, len(actives)+1)
	layer:= make(vector, 0)
	prv:= &layer
	g[0] = make([]Vertex, 0)
	g[0] = append(g[0], newVertex())
	for i:= 0; i != len(actives); i++ {
		size:= 1 << len(actives[i])
		g[i+1] = make([]Vertex, size, size)
		for k:= 0; k < size; k++ {
			g[i+1][k] = newVertex()
		}
	}

	for i:= 1; i < len(g); i++ {
		for k:= 0; k != len(g[i-1]); k++ {
			index:= newNext(prv, k)
			index.add(&actives[i-1], false)
			to:= index.next(&actives[i-1])
			hasW:= index.weight(spanMatrix, i-1)
			if hasW {
				g[i-1][k].path2One = &g[i][to]
			} else {
				g[i-1][k].path2Zero = &g[i][to]
			}

			index= newNext(prv, k)
			index.add(&actives[i-1], true)
			to = index.next(&actives[i-1])
			hasW = index.weight(spanMatrix, i-1)
			if hasW {
				g[i-1][k].path2One = &g[i][to]
			} else {
				g[i-1][k].path2Zero = &g[i][to]
			}
		}
		prv = &actives[i-1]
	}

	return g
}

/*   Math   */

func int2bool(val int) bool {
	return val != 0
}

func bool2int(v bool) int {
	if v {
		return 1
	}
	return 0
}


/*   Main   */
func encode(vWord *vector, m *matrix) vector {
	code:= make(vector, len((*m)[0]), len((*m)[0]))
	for row:= 0; row != len(*m); row++ {
		if (*vWord)[row] != 0 {
			vectorXOR(&code, &(*m)[row])
		}
	}
	return code
}

func decode(signal *vector_f, g *graph) vector {
	for i:= 0; i < len(*g); i++ {
		for j:= 0; j < len((*g)[i]); j++ {
			(*g)[i][j].err = errSize
			(*g)[i][j].number = i * 10 + j
		}
	}
	for j:= 0; j < len((*g)[0]); j++ {
		(*g)[0][j].err = 0.0
	}

	for i:= 0; i != len(*signal); i++ {
		for j:= range (*g)[i] {
			(*g)[i][j].next((*signal)[i])
		}
	}

	code:= make(vector, len(*signal), len(*signal))
	vertex:= (*g)[len(*g)-1][len((*g)[len(*g)-1])-1]
	for i:= len(*signal)-1; vertex.origin != nil; vertex, i = *vertex.origin, i-1 {
		v:= 1
		if vertex.isOk {
			v = -1
		}
		code[i] = v
	}
	return code
}

func simulate(m *matrix, g *graph, sigma float64) bool {
	word:= make(vector, len(*m), len(*m))
	for i:= range word {
		word[i] = int(rand.Float32()+0.5)
	}
	code:= encode(&word, m)
	for i:= range code {
		v:= 1
		if code[i] == 1 {
			v=-1
		}
		code[i] = v
	}

	signal:= make(vector_f, len(code), len(code))
	for i:=0; i != len(code); i++ {
		signal[i] = float64((code)[i]) + rand.NormFloat64() * sigma
	}
	res := decode(&signal, g)
	return eq(&res, &code)
}

func main() {
	inFile, _ := os.Open(fmt.Sprintf("%s%s", prefix, fileNameIn))
	outFile, _ := os.Create(fmt.Sprintf("%s%s", prefix, fileNameOut))
	in = bufio.NewReaderSize(inFile, bufSize)
	out = bufio.NewWriter(outFile)
	defer inFile.Close()
	defer outFile.Close()

	solve()
}


/*   Scanner   */

var in *bufio.Reader
var out *bufio.Writer
var stringParts []string

func readLine() string {
	str, _, _ := in.ReadLine()
	return string(str)
}

func readNext() string {
	if len(stringParts) == 0 {
		stringParts = strings.Split(readLine(), " ")
	}
	res := stringParts[0]
	stringParts = stringParts[1:]
	return res
}

func readInt32() int {
	res, _ := strconv.ParseInt(readNext(), 10, 32)
	return int(res)
}

func readInt64() int64 {
	res, _ := strconv.ParseInt(readNext(), 10, 64)
	return res
}

func readFloat32() float32 {
	res, _ := strconv.ParseFloat(readNext(), 32)
	return float32(res)
}

func readFloat64() float64 {
	res, _ := strconv.ParseFloat(readNext(), 32)
	return res
}

func writeString(s interface{}) {
	out.WriteString(fmt.Sprintf("%v ", s))
	out.Flush()
}

func writeSlice(s interface{}) {
	writeString(strings.Trim(strings.Join(strings.Split(fmt.Sprint(s), " "), " "), "[]"))
}

func ln() {
	out.Write([]byte{'\n'})
}