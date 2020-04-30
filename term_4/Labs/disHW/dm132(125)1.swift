//feed raw string to escape
func escape(_ s: String) -> String {
    var res = String()
    for char in s {
        switch char {
            case "\n":
                res += "\\n"
            case "\"":
                res += "\\\""
            case "\\":
                res += "\\\\"
            default:
                res += String(char)
        }
    }
    return res
}

func getCode() -> String {
    let s1 = "//feed raw string to escape\nfunc escape(_ s: String) -> String {\n    var res = String()\n    for char in s {\n        switch char {\n            case \"\\n\":\n                res += \"\\\\n\"\n            case \"\\\"\":\n                res += \"\\\\\\\"\"\n            case \"\\\\\":\n                res += \"\\\\\\\\\"\n            default:\n                res += String(char)\n        }\n    }\n    return res\n}\n\nfunc getCode() -> String {\n    let s1 = \"$\"\n    let s2 = \"§\"\n    var str = s2.map { $0 }\n    for i in 0..<str.count {\n        if str[i] == \"§\" {\n            str = Array(Array(str[..<i]) + escape(s2) + Array(str[(i+1)...]))\n            break\n        }\n    }\n    for i in 0..<str.count {\n        if str[i] == \"$\" {\n            str = Array(Array(str[..<i]) + escape(s1) + Array(str[(i+1)...]))\n            break\n        }\n    }\n    return String(str)\n}\n\nlet s = getCode()\nprint(s)"
    let s2 = "func demandSwiftOnPcms() {\n    print(\"Hotim swift na pcms!!!\")\n}\n\nfunc escape(_ s: String) -> String {\n    var res = String()\n    for char in s {\n        switch char {\n            case \"\\n\":\n                res += \"\\\\n\"\n            case \"\\\"\":\n                res += \"\\\\\\\"\"\n            case \"\\\\\":\n                res += \"\\\\\\\\\"\n            default:\n                res += String(char)\n        }\n    }\n    return res\n}\n\nfunc getCode() -> String {\n    let s1 = \"$\"\n    let s2 = \"§\"\n    var str = s1.map { $0 }\n    for i in 0..<str.count {\n        if str[i] == \"§\" {\n            str = Array(Array(str[..<i]) + escape(s2) + Array(str[(i+1)...]))\n            break\n        }\n    }\n    for i in 0..<str.count {\n        if str[i] == \"$\" {\n            str = Array(Array(str[..<i]) + escape(s1) + Array(str[(i+1)...]))\n            break\n        }\n    }\n    return String(str)\n}\n\nlet s = getCode()\nprint(s)"
    var str = s2.map { $0 }
    for i in 0..<str.count {
        if str[i] == "§" {
            str = Array(Array(str[..<i]) + escape(s2) + Array(str[(i+1)...]))
            break
        }
    }
    for i in 0..<str.count {
        if str[i] == "$" {
            str = Array(Array(str[..<i]) + escape(s1) + Array(str[(i+1)...]))
            break
        }
    }
    return String(str)
}

let s = getCode()
print(s)
