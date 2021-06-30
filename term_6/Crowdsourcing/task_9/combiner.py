import os


inFileName = "input_raw.tsv"
outFileName = "output.txt"

with open(inFileName, "r") as f:
    txt = f.read()


res = []
for line in txt.split('\n')[:]:
    vals = line.split('\t')
    query = vals[0]
    name = "(%s) %s"%(vals[4], vals[3])
    latitude = vals[8]
    longitude = vals[9]
    position = "%s,%s"%(latitude, longitude)
    cur = '\t'.join([name, position, latitude, longitude])
    res.append(cur)


outputAlreadyExists = os.path.isfile(outFileName)
flag = "w" if outputAlreadyExists else "x"
with open("output.txt", flag) as o:
    o.write('\n'.join(res))