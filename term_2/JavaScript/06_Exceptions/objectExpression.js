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
        this.value = value;
    }

    Const.prototype = Object.create(primitive);
    Const.prototype.toString = function () {
        return this.value.toString();
    };
    Const.prototype.prefix = Const.prototype.toString;
    Const.prototype.evaluate = function () {
        return this.value;
    };

    const
        ZERO = new Const(0),
        ONE = new Const(1),
        TWO = new Const(2);

    Const.prototype.diff = () => ZERO;

    const
        isZero = (value) => (value instanceof Const) && value.value === 0,
        isOne = (value) => (value instanceof Const) && value.value === 1;


    function Variable(name) {
        this.name = name;
    }

    Variable.prototype = Object.create(primitive);
    Variable.prototype.toString = function () {
        return this.name;
    };
    Variable.prototype.prefix = Variable.prototype.toString;
    Variable.prototype.evaluate = function (...values) {
        return values[VAR_IDXS.get(this.name)];
    };
    Variable.prototype.diff = function (value) {
        return value === this.name ? ONE : ZERO;
    };


    function Op(...args) {
        const operands = [].slice.call(args);
        this.getOperands = () => operands;
    }

    Op.prototype.toString = function () {
        return this.getOperands().join(" ") + " " + this.getOperator();
    };
    Op.prototype.prefix = function () {
        return "(" + this.getOperator() + " " + this.getOperands().map(function (value) {
            return value.prefix()
        }).join(" ") + ")";
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
        let isConst = true;
        ops.forEach((value) => isConst = value instanceof Const);
        const res = Expression(this.constructor, ops);
        if (isConst) {
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

    const foldLeft = (f, zero) => { //fix with some array operators
        return (...args) => {
            let result = zero;
            for (const arg of args) {
                result = f(result, arg);
            }
            return result;
        }
    };
    const sum = foldLeft((a, b) => a + b, 0);

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
        ),
        Sum = abstractOp(
            (...args) => sum(...args), "sum"
        ),
        Avg = abstractOp(
            (...args) => sum(...args) / args.length, "avg"
        );

    const
        OPERATORS = {
            "+": [Add, 2],
            "-": [Subtract, 2],
            "/": [Divide, 2],
            "*": [Multiply, 2],
            "negate": [Negate, 1],
            "sqrt": [Sqrt, 1],
            "square": [Square, 1],
            "atan": [ArcTan, 1],
            "sum": [Sum],
            "avg": [Avg]
        },

        VAR_IDXS = new Map([
            ["x", 0],
            ["y", 1],
            ["z", 2]
        ]);

    function ParserError(errorMsg) { //fix pointer
        let errorPointer = "";
        for (let i = 0; i < idx; i++) {
            errorPointer += " ";
        }
        errorPointer += "^";
        this.message = errorMsg + " :\n" + expr + "\n" + errorPointer;
    }

    ParserError.prototype = Error.prototype;
    ParserError.prototype.name = "ParserError";
    ParserError.prototype.constructor = ParserError;


    let idx = 0;
    let expr = "";
    let res = [];

    const skipWhitespaces = () => {
        while (idx < expr.length && /\s/.test(expr.charAt(idx))) {
            idx++
        }
    };

    const getIdentifier = () => {
        if (!(/[A-Za-z]/.test(expr.charAt(idx)))) {
            throw new ParserError("Incorrect identifier \'" + expr.charAt(idx) + "\'");
        }
        let identifier = "";
        while (idx < expr.length && /\w/.test(expr.charAt(idx))) {
            identifier += expr.charAt(idx++);
        }
        return identifier;
    };

    function getValue() {
        let val = "";
        if (expr.charAt(idx) === "-") {
            val += "-";
            idx++;
        }
        while (idx < expr.length && /\d/.test(expr.charAt(idx))) {
            val += expr.charAt(idx++);
        }
        if (val !== "" && val !== "-") {
            return parseInt(val);
        }
        if (val === "-") {
            idx--;
        }
        return undefined;
    }

    const parsePrefix = (expression) => { //make it recursive and add postfix
        idx = 0;
        res = [];
        expr = expression;
        if (expression.length === 0) {
            throw new ParserError("Empty expression");
        }
        let balance = 0;
        skipWhitespaces();
        while (idx < expr.length) {
            let curValue,
                curOperator,
                curIdentifier;
            if (expr.charAt(idx) === ")") {
                if (--balance < 0) {
                    throw new ParserError("Missing closing parentheses");
                }
                let operands = [];
                while ((res[res.length - 1] !== "(") && !(res[res.length - 1] in OPERATORS)) {
                    operands.push(res.pop());
                }
                if (res[res.length - 1] === "(") {
                    throw new ParserError("Missing operator");
                }
                curOperator = res.pop();
                if (res.pop() !== "(") {
                    throw new ParserError("Missing parentheses");
                }
                if (operands.length > OPERATORS[curOperator][1]) {
                    throw new ParserError("Too many operands, expected " + OPERATORS[curOperator][1] + "operands");
                } else if (operands.length < OPERATORS[curOperator][1]) {
                    throw new ParserError("Missing operand");
                } else {
                    res.push(Expression(OPERATORS[curOperator][0], operands.reverse()));
                }
                idx++;
                if (balance === 0) {
                    break;
                }
            }
            else if (expr.charAt(idx) === "(") {
                res.push("(");
                idx++;
                balance++;
            }
            else if ((curValue = getValue()) !== undefined) {
                res.push(new Const(curValue));
            }
            else {
                if (expr.charAt(idx) in OPERATORS) {
                    curOperator = expr.charAt(idx);
                    idx++;
                } else {
                    curIdentifier = getIdentifier();
                    if (curIdentifier in OPERATORS) {
                        curOperator = curIdentifier;
                    }
                }
                if (curOperator !== undefined) {
                    res.push(curOperator);
                }
                else if (VAR_IDXS.has(curIdentifier)) {
                    res.push(new Variable(curIdentifier));
                    if (balance === 0) {
                        break;
                    }
                }
                else {
                    throw new ParserError("Incorrect identifier \'" + expr.charAt(idx) + "\'");
                }
            }
            skipWhitespaces();
        }
        skipWhitespaces();
        if (idx !== expr.length) {
            throw new ParserError("Unknown ending of correct expression");
        } else if (res.length > 1) {
            throw new ParserError("Incorrect parentheses placement");
        } else if (balance > 0) {
            throw new ParserError("Missing closing parentheses");
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
        "Negate": Negate,
        "Square": Square,
        "Sqrt": Sqrt,
        "ArcTan": ArcTan,
        "Sum": Sum,
        "Avg": Avg,
        "parsePrefix": parsePrefix
    }
})();

const
    Const = expression.Const,
    Variable = expression.Variable,

    Negate = expression.Negate,
    Square = expression.Square,
    Sqrt = expression.Sqrt,
    ArcTan = expression.ArcTan,

    Avg = expression.Avg,
    Sum = expression.Sum,

    Add = expression.Add,
    Subtract = expression.Subtract,
    Multiply = expression.Multiply,
    Divide = expression.Divide,

    parsePrefix = expression.parsePrefix;


//const exprr = parsePrefix("(+ (srt x) 3)").evaluate(5);