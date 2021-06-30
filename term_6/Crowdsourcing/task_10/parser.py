import os


inFileName = "output.tsv"
outFileName = "result.json"

with open(inFileName, "r") as f:
    txt = f.read()


res = ["["]
for line in txt.split('\n')[1:]:
    vals = line.split('\t')
    if len(vals) < 2:
        break
    url = vals[0]
    txt = vals[2]
    toAdd = "{\"url\": \"%s\", \"text\": \"%s\"}," % (url, txt)
    res.append(toAdd)


res.append("]")


outputAlreadyExists = os.path.isfile(outFileName)
flag = "w" if outputAlreadyExists else "x"
with open(outFileName, flag) as o:
    o.write('\n'.join(res))