package main

import "fmt"

func escape(s string) string {
	res := ""
	for _, c := range s {
		switch c {
		case '\n':
			res += "\\n"
			break
		case '"':
			res += "\\\""
			break
		case '\\':
			res += "\\\\"
			break
		default:
			res += string(c)
		}
	}
	return res
}

func getCode() string {
	s := "package main\n\nimport \"fmt\"\n\nfunc escape(s string) string {\n	res := \"\"\n	for _, c := range s {\n		switch c {\n		case '\\n':\n			res += \"\\\\n\"\n			break\n		case '\"':\n			res += \"\\\\\\\"\"\n			break\n		case '\\\\':\n			res += \"\\\\\\\\\"\n			break\n		default:\n			res += string(c)\n		}\n	}\n	return res\n}\n\nfunc getCode() string {\n	s := \"$\"\n	for i := 0; i < len(s); i++ {\n		if s[i] == '$' {\n			s = s[0:i] + escape(s) + s[(i+1):]\n			break\n		}\n	}\n	return s\n}\n\nfunc main() {\n	s := getCode()\n	fmt.Println(s)\n}\n"
	for i := 0; i < len(s); i++ {
		if s[i] == '$' {
			s = s[0:i] + escape(s) + s[(i+1):]
			break
		}
	}
	return s
}

func main() {
	s := getCode()
	fmt.Println(s)
}
