import random
import sys

names = ["data/ans/TlkAgg2/ds.tsv",
         "data/ans/TlkAgg2/mv.tsv",
         "data/ans/TlkAgg2/wawa.tsv",
         "data/ans/TlkAgg5/ds.tsv",
         "data/ans/TlkAgg5/mv.tsv",
         "data/ans/TlkAgg5/wawa.tsv"]

for filename in names:
    with open(filename, 'r') as f:
        txt = f.read()

    sp = txt.split('\n')
    arr = []
    count = 0
    for line in sp[1:]:
        num = random.random()
        columns = line.split('\t')
        val = int(columns[1])
        if num < 0.005:
            count += 1
            if val == 1:
                val = 0
            else:
                val = 1
        arr.append(columns[0] + '\t' + str(val))

    with open(filename, 'w') as f:
        sys.stdout = f  # Change the standard output to the file we created.
        print('\n'.join(arr))
