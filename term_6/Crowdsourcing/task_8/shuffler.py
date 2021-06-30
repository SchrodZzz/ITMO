import os


inFileName = "data/toloka_raw.tsv"
outFileName = "output.txt"

with open(inFileName, "r") as f:
    txt = f.read()


data = {}
for line in txt.split('\n'):
    vals = line.split('\t')
    query = vals[0]
    toAdd = {"link": vals[2], "image": vals[3]}
    if query in data:
        data[query].append(toAdd)
    else:
        data[query] = [toAdd]


res = []
for query, vals in data.items():
    size = len(vals)
    for i, fst in enumerate(vals[:size-1]):
        for snd in vals[i+1:]:
            res.append('\t'.join([query, fst["image"], snd["image"], fst["link"], snd["link"]]))


outputAlreadyExists = os.path.isfile(outFileName)
flag = "w" if outputAlreadyExists else "x"
with open("output.txt", flag) as o:
    o.write('\n'.join(res))