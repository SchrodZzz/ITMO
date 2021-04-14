from random import *
import random
import numpy as np

filename1 = "bin_class.tsv"

with open(filename1, 'r') as f:
    txt = f.read()

dic = {"pedestrian": 0, "bus_stop": 1, "no_stop": 2, "main_road": 3, "give_way": 4}
res = {"res": -1, "overlap": [], "acc": -1}
rnd = dic.keys()

def pick_rand(golden, status, arr):
    idx = dic[golden]
    r = idx
    while r == idx:
        r = (int)(random.random() * 6)
        while r > 4 or r < 0:
            r = (int)(random.random() * 6)
    return idx


def pick_overlap(golden):
    num = random.random()
    overlap = []
    for i in range(3):
        if i == 2 and overlap[0][dic[golden]] and overlap[1][dic[golden]]:
            break
        tmp = [False, False, False, False, False]
        if num < 0.9:
            tmp[dic[golden]] = True
        elif num < 0.92:
            tmp[dic[golden]] = True
            tmp[pick_rand(golden, True, tmp)] = True
        elif num < 0.97:
            tmp[dic[golden]] = True
            tmp[pick_rand(golden, True, tmp)] = True
            tmp[pick_rand(golden, True, tmp)] = True
        elif num < 0.99:
            tmp[dic[golden]] = True
            tmp[pick_rand(golden, True, tmp)] = True
            tmp[pick_rand(golden, True, tmp)] = True
            tmp[pick_rand(golden, True, tmp)] = True
        else:
            tmp = [True, True, True, True, True]

        num = random.random()
        if num > 0.83:
            tmp[dic[golden]] = False
            if num > 0.86:
                tmp[pick_rand(golden, False, tmp)] = False
            if num > 0.89:
                tmp[pick_rand(golden, False, tmp)] = False
            if num > 0.93:
                tmp[pick_rand(golden, False, tmp)] = False
            if num > 0.99:
                tmp[pick_rand(golden, False, tmp)] = False
        overlap.append(tmp)
    return overlap


def get_overlap_res_acc(golden):
    res["res"] = -1
    res["overlap"] = pick_overlap(golden)
    res["acc"] = -1

    count = 0
    ok = 0
    for cur in res["overlap"]:
        if cur[dic[golden]]:
            count += 1
            ok += 1
        else:
            count -= 1
    res["res"] = "Correct" if count > 0 else "Incorrect"
    res["acc"] = ok / len(res["overlap"])

    rarr = [str(res["res"]),
            str((res["overlap"])),
            str(res["acc"])]
    return "\t" + "\t".join(rarr)

sp = txt.split('\n')
arr = []
for line in sp[1:]:
    columns = line.split('\t')
    golden = columns[1]
    arr.append(line + get_overlap_res_acc(golden))
print('\n'.join(arr))