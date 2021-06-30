import os


inFileName = "res/aggregated_res.tsv"
inFileNameRaw = "data/toloka_raw.tsv"
outFileName = "output.txt"

with open(inFileName, "r") as f:
    txt = f.read()

with open(inFileNameRaw, "r") as f:
    txt_raw = f.read()


data_raw = {}
data_raw_out = {}
for line in txt_raw.split('\n'):
    vals = line.split('\t')
    query = vals[0]
    toAdd = vals[2]
    if query not in data_raw:
        data_raw[query] = {}
    data_raw[query][toAdd] = 0
    data_raw_out[toAdd] = line

input = txt.split('\n')
for line in input[1:len(input)-1]:
    vals = line.split('\t')
    query = vals[2]
    imgL = vals[3]
    imgR = vals[4]
    res = vals[5]
    if res == "fst":
        data_raw[query][imgL] += 1
    elif res == "snd":
        data_raw[query][imgR] += 1
    else:
        data_raw[query][imgL] += 1
        data_raw[query][imgR] += 1


res = []
for query, vals in data_raw.items():
    for i in sorted(vals, key=vals.get, reverse=True):
        res.append(str(vals[i]) + "\t" + data_raw_out[i])


outputAlreadyExists = os.path.isfile(outFileName)
flag = "w" if outputAlreadyExists else "x"
with open("output.txt", flag) as o:
    o.write('\n'.join(res))
