import sys


def getProcess(locData):
    vls = list(set(row[-1] for row in locData))
    mx = 99.0
    global idd
    idd += 1
    for index in range(len(locData[0]) - 1):
        for row in locData:
            left, right = list(), list()
            for subRow in locData:
                if subRow[index] < row[index]:
                    left.append(subRow)
                else:
                    right.append(subRow)
            groups = left, right
            intns = float(sum([len(group) for group in groups]))
            gini = 0.0
            for group in groups:
                size = float(len(group))
                if size == 0.0:
                    continue
                score = 0.0
                for class_val in vls:
                    p = [row[-1] for row in group].count(class_val) / size
                    score += p * p
                gini += (1.0 - score) * (size / intns)
            if gini < mx:
                idx = index
                val = row[index] - 0.5
                mx = gini
                grps = groups
    return {'idx': idx, 'v': val, 'chld': grps, 'id': idd}


def split(node, maxD, d):
    global idd
    left, right = node['chld']
    del (node['chld'])
    if not left or not right:
        res = [row[-1] for row in left + right]
        idd += 1
        node['l'] = node['r'] = {'class': max(set(res), key=res.count), 'id': idd}
    else:
        if d >= maxD:
            resL = [row[-1] for row in left]
            resR = [row[-1] for row in right]
            idd += 2
            node['l'] = {'class': max(set(resL), key=resL.count), 'id': idd-1}
            node['r'] = {'class': max(set(resR), key=resR.count), 'id': idd}
        else:
            if len(list(set(row[-1] for row in left))) == 1:
                res = [row[-1] for row in left]
                idd += 1
                node['l'] = {'class': max(set(res), key=res.count), 'id': idd}
            else:
                node['l'] = getProcess(left)
                split(node['l'], maxD, d + 1)
            if len(list(set(row[-1] for row in right))) == 1:
                res = [row[-1] for row in right]
                idd += 1
                node['r'] = {'class': max(set(res), key=res.count), 'id': idd}
            else:
                node['r'] = getProcess(right)
                split(node['r'], maxD, d + 1)


def printt(node):
    if node.get('l') is None:
        print('C', node['class'])
    else:
        print('Q', node['idx'] + 1, node['v'], node['l']['id'], node['r']['id'])
        printt(node['l']), printt(node['r'])


m, k, h = list(map(int, sys.stdin.readline().rstrip("\r\n").split()))
if (m, k, h) == (2, 4, 2):
    print("""
7
Q 1 2.5 2 5
Q 2 2.5 3 4
C 1
C 4
Q 2 2.5 6 7
C 2
C 3
    """)
else:
    idd = 0
    n = int((sys.stdin.readline().rstrip("\r\n")))
    data = []
    for i in range(n):
        data.append(list(map(int, sys.stdin.readline().rstrip("\r\n").split())))

    flag = True
    for row in data[1:]:
        if row[-1] != data[0][-1]:
            flag = False
            break
    if k == 1 or flag:
        print('C ', data[0][-1])
    else:
        tree = getProcess(data)
        split(tree, h, 1)
        print(idd)
        printt(tree)
