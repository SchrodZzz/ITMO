from random import *
import random
import numpy as np

filename1 = "multi_class.tsv"

with open(filename1, 'r') as f:
    txt = f.read()

dic = {"pedestrian": 1, "bus_stop": 2, "no_stop": 3, "main_road": 4, "give_way": 5}
rnd = dic.keys()

def pick_rand(golden):
    res = golden
    while res == golden:
        res = sample(rnd, 1)[0]
    return res

def get_overlap_res_acc(golden):
    num = random.random()
    res = {"res": -1, "overlap": [], "acc": -1}
    if num < 0.46:
        res["overlap"] = [golden, golden]
        res["res"] = golden
        res["acc"] = 1
    else:
        num = random.random()
        if num < 0.5:
            res["overlap"] = [golden, pick_rand(golden), golden]
            res["res"] = golden
            res["acc"] = 0.67
        else:
            if num < 0.9:
                res["overlap"] = [golden, pick_rand(golden), pick_rand(golden)]
                res["res"] = golden
                res["acc"] = 0.33
            else:
                res["overlap"] = [pick_rand(golden), pick_rand(golden), pick_rand(golden)]
                res["res"] = golden
                res["acc"] = 0
    np.random.shuffle(res["overlap"])
    rarr = [str(res["res"]),
            str(",".join(res["overlap"])),
            str(res["acc"])]
    return "\t" + "\t".join(rarr)

sp = txt.split('\n')
arr = []
for line in sp[1:]:
    columns = line.split('\t')
    golden = columns[1]
    arr.append(line + get_overlap_res_acc(golden))
print('\n'.join(arr))