"use strict";

const expression = (function () {
    function Expression(constructor, args) {
        const tmp = Object.create(constructor.prototype);
        constructor.apply(tmp, args);
        return tmp;
    }

    const primitive = {
        simplify() {
            return this
        }
    };


    function Const(value) {
        this.getValue = () => value;
    }

    Const.prototype = Object.create(primitive);
    Const.prototype.toString = function () {
        return this.getValue().toString();
    };
    Const.prototype.evaluate = function () {
        return this.getValue();
    };

    const
        ZERO = new Const(0),
        ONE = new Const(1),
        TWO = new Const(2);

    Const.prototype.diff = () => ZERO;

    const
        isZero = (value) => (value instanceof Const) && value.getValue() === 0,
        isOne = (value) => (value instanceof Const) && value.getValue() === 1;


    function Variable(name) {
        const idx = VAR_IDXS.get(name);
        this.getName = () => name;
        this.getIdx = () => idx;
    }

    Variable.prototype = Object.create(primitive);
    Variable.prototype.toString = function () {
        return this.getName();
    };
    Variable.prototype.evaluate = function (...values) {
        return values[this.getIdx()];
    };
    Variable.prototype.diff = function (value) {
        return value === this.getName() ? ONE : ZERO;
    };


    function Op() {
        const operands = [].slice.call(arguments);
        this.getOperands = () => operands;
    }

    Op.prototype.toString = function () {
        return this.getOperands().join(" ") + " " + this.getOperator();
    };
    Op.prototype.evaluate = function (...args) {
        const res = this.getOperands().map((value) => value.evaluate(...args));
        return this._action(...res);
    };
    Op.prototype.diff = function (value) {
        const ops = this.getOperands();
        return this._doDiff(...ops.concat(ops.map((val) => val.diff(value))));
    };
    Op.prototype.simplify = function () {
        const ops = this.getOperands().map((item) => item.simplify());
        let flag = true;
        ops.forEach((value) => {
            if (!(value instanceof Const)) {
                flag = false;
            }
        });
        const res = Expression(this.constructor, ops);
        if (flag) {
            return new Const(res.evaluate());
        }
        if (this._doSimplify !== undefined) {
            return this._doSimplify(...ops);
        }
        return res;
    };


    function OpDefine(maker, calculationAlg, operator, diffAlg, simplifyAlg) {
        this.constructor = maker;
        this._action = calculationAlg;
        this.getOperator = () => operator;
        this._doDiff = diffAlg;
        this._doSimplify = simplifyAlg;
    }

    OpDefine.prototype = Op.prototype;

    const abstractOp = (calculationAlg, operator, diffAlg, howToSimplify) => {
        const result = function () {
            Op.apply(this, arguments);
        };
        result.prototype = new OpDefine(result, calculationAlg, operator, diffAlg, howToSimplify);
        return result;
    };

    const
        Add = abstractOp(
            (a, b) => a + b, "+",
            (a, b, da, db) => new Add(da, db),
            (a, b) => isZero(a) ? b : isZero(b) ? a : new Add(a, b)
        ),
        Subtract = abstractOp(
            (a, b) => a - b, "-",
            (a, b, da, db) => new Subtract(da, db),
            (a, b) => isZero(b) ? a : new Subtract(a, b)
        ),
        Multiply = abstractOp(
            (a, b) => a * b, "*",
            (a, b, da, db) => new Add(new Multiply(da, b), new Multiply(a, db)),
            (a, b) => (isZero(a) || isZero(b)) ? ZERO : isOne(a) ? b : isOne(b) ? a : new Multiply(a, b)
        ),
        Divide = abstractOp(
            (a, b) => a / b, "/",
            (a, b, da, db) => new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b)),
            (a, b) => isZero(a) ? ZERO : isOne(b) ? a : new Divide(a, b)
        ),
        ArcTan2 = abstractOp( //undone
            (a,b) => Math.atan2(a,b), "atan2",
            (a,b,da,db) => new Add(new Negate(new Divide(new Multiply(b,da),new Add(new Square(a),new Square(b)))),
                new Divide(new Multiply(a,db),new Add(new Square(a),new Square(b)))),
            ONE
        ),
        Min3 = abstractOp(    //undone
            (a,b,c) => Math.min(a,b,c), "min3",
            ZERO,
        ),
        Max5 = abstractOp(    //undone
            (a,b,c,d,e) => Math.max(a,b,c,d,e), "max5",
            ZERO
        ),
        Negate = abstractOp(
            (a) => -a, "negate",
            (a, da) => new Negate(da)
        ),
        Square = abstractOp(
            (a) => a * a, "square",
            (a, da) => new Multiply(new Multiply(TWO, a), da)
        ),
        Sqrt = abstractOp(
            (a) => Math.sqrt(Math.abs(a)), "sqrt",
            (a, da) => new Divide(new Multiply(a, da), new Multiply(TWO, new Sqrt(new Multiply(new Square(a), a))))
        ),
        ArcTan = abstractOp(
            (a) => Math.atan(a), "atan",
            (a, da) => new Divide(new Multiply(ONE, da), new Add(ONE, new Square(a))),
        );

    const
        OPERATORS = new Map([
            ["+", [Add, 2]],
            ["-", [Subtract, 2]],
            ["/", [Divide, 2]],
            ["*", [Multiply, 2]],
            ["atan2", [ArcTan2, 2]],
            ["min3", [Min3, 3]],
            ["max5", [Max5, 5]],
            ["negate", [Negate, 1]],
            ["sqrt", [Sqrt, 1]],
            ["square", [Square, 1]],
            ["atan", [ArcTan,1]]
        ]),

        VAR_IDXS = new Map([
            ["x", 0],
            ["y", 1],
            ["z", 2]
        ]),

        VARS = new Map([
            ["x", new Variable("x")],
            ["y", new Variable("y")],
            ["z", new Variable("z")]
        ]);

    const parse = (expr) => {
        let
            arr = expr.trim().split(/\s+/),
            res = [];
        for (const token of arr) {
            if (VARS.has(token)) {
                res.push(VARS.get(token));
            } else if (OPERATORS.has(token)) {
                res.push(Expression(OPERATORS.get(token)[0], res.splice(-OPERATORS.get(token)[1], OPERATORS.get(token)[1])));
            } else {
                res.push(new Const(parseInt(token)));
            }
        }
        return res[0];
    };

    return {
        "Const": Const,
        "Variable": Variable,
        "Add": Add,
        "Subtract": Subtract,
        "Multiply": Multiply,
        "Divide": Divide,
        "ArcTan2" : ArcTan2,
        "Min3" : Min3,
        "Max5" : Max5,
        "Negate": Negate,
        "Square": Square,
        "Sqrt": Sqrt,
        "ArcTan" : ArcTan,
        "parse": parse,
    }
})();

const
    Const = expression.Const,
    Variable = expression.Variable,

    Negate = expression.Negate,
    Square = expression.Square,
    Sqrt = expression.Sqrt,
    ArcTan = expression.ArcTan,
    Min3 = expression.Min3,
    Max5 = expression.Max5,

    ArcTan2 = expression.ArcTan2,
    Add = expression.Add,
    Subtract = expression.Subtract,
    Multiply = expression.Multiply,
    Divide = expression.Divide,

    parse = expression.parse;
