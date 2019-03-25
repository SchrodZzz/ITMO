"use strict";

const op = (operator) => {
    return (...operands) => (...values) => {
        let res = [];
        for (let arg of operands) {
            res.push(arg(...values));
        }
        return operator(...res);
    }
};

const VAR_IDXS = new Map([
    ["x", 0],
    ["y", 1],
    ["z", 2]
]);

const variable = (name) => (...values) => values[VAR_IDXS.get(name)];
const cnst = (value) => () => value;

const one = cnst(1);
const two = cnst(2);
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const foldLeft = (f, zero) => {
    return (...args) => {
        let result = zero;
        for (const arg of args) {
            result = f(result, arg);
        }
        return result;
    }
};

const add = op((x, y) => x + y);
const subtract = op((x, y) => x - y);
const multiply = op((x, y) => x * y);
const divide = op((x, y) => x / y);
const negate = op((x) => -x);
const abs = op((x) => x < 0 ? -x : x);
const iff = op((x1, x2, x3) => x1 < 0 ? x3 : x2);
const med3 = op((x1, x2, x3) => [x1, x2, x3].sort((x, y) => x - y)[1]);
const sum = foldLeft((a, b) => a + b, 0);
const avgN = op((...args) => (sum(...args)) /args.length);

const CNSTS = new Map([
    ["one", one],
    ["two", two],
    ["pi", pi],
    ["e", e]
]);

const OPERATORS = new Map([
    ["+", [add, 2]],
    ["-", [subtract, 2]],
    ["/", [divide, 2]],
    ["*", [multiply, 2]],
    ["negate", [negate, 1]],
    ["abs", [abs, 1]],
    ["iff", [iff, 3]],
    ["avgN", [avgN, 5]],
    ["med3", [med3, 3]]
]);

const VARS = new Map([
    ["x", variable("x")],
    ["y", variable("y")],
    ["z", variable("z")]
]);

const parse = (expr) => {
    let arr = expr.trim().split(/\s+/);
    let res = [];
    for (const token of arr) {
        if (VARS.has(token)) {
            res.push(VARS.get(token));
        } else if (OPERATORS.has(token)) {
            let val = OPERATORS.get(token)[1];
            res.push(OPERATORS.get(token)[0](...res.splice(res.length - val, val)));
        } else if (CNSTS.has(token)) {
            res.push(CNSTS.get(token));
        } else {
            res.push(cnst(Number(token)));
        }
    }
    return res[0]
};
