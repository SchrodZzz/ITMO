#!/usr/bin/env python

filename1 = "res/Quality.tsv"
filename2 = "res/Trash.tsv"

with open(filename1, 'r') as f:
    txt1 = f.read()

with open(filename2, 'r') as f:
    txt2 = f.read()


def tasksStat(txt):
    sp = txt.split('\n')
    check = [0 for i in range(500)]
    for line in sp[1:]:
        check[int(line.split('\t')[0])] += 1
    print(check)
    print('----')
    cnt = 0
    for i in range(500):
        if check[i] != 3:
            print(i, check[i], '/', 3)
            cnt += 1
    print('----')
    print(cnt)


def pplStat(txt):
    sp = txt.split('\n')
    arr = []
    for line in sp[1:]:
        columns = line.split('\t')
        # worker id is the last column in my data (;
        arr.append(columns[len(columns)-1])
    return set(arr)


def samePeopleQT():
    stat1 = pplStat(txt1)
    stat2 = pplStat(txt2)

    diff1 = stat1 - stat2
    diff2 = stat2 - stat1
    union = stat1.union(stat2)
    same = union - diff1 - diff2
    print(same)
    print(len(same))


while (True):
    cmd = input('choose the command (1-3) or call for help (h):')
    if cmd == 'h':
        print('1 - tasks stat for Q')
        print('2 - tasks stat for T')
        print('3 - same workers stat for QT')
    elif cmd == '1':
        tasksStat(txt1)
    elif cmd == '2':
        tasksStat(txt2)
    elif cmd == '3':
        samePeopleQT()
    else:
        exit(1)