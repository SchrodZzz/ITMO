package generics;

import exceptions.CustomArithmeticException;
import exceptions.DivideByZeroException;
import exceptions.OverflowException;
import exceptions.ParserException;

public class TypeInteger implements Types<Integer> {

    private final boolean check;

    public TypeInteger(boolean check) {
        this.check = check;
    }

    private void addCheck(Integer x, Integer y) throws CustomArithmeticException {
        if (check) {
            if (y > 0
                    ? x > Integer.MAX_VALUE - y
                    : x < Integer.MIN_VALUE - y) {
                throw new OverflowException(x + " + " + y);
            }
        }
    }

    private void subCheck(Integer x, Integer y) throws CustomArithmeticException {
        if (check) {
            if (y > 0
                    ? x < Integer.MIN_VALUE + y
                    : x > Integer.MAX_VALUE + y) {
                throw new OverflowException(x + " - " + y);
            }
        }
    }

    private void mulCheck(Integer x, Integer y) throws CustomArithmeticException {
        if (check) {
            if (y > 0
                    ? x > Integer.MAX_VALUE / y || x < Integer.MIN_VALUE / y
                    : (y < -1
                    ? x > Integer.MIN_VALUE / y || x < Integer.MAX_VALUE / y
                    : y == -1 && x == Integer.MIN_VALUE)) {
                throw new OverflowException(x + "*" + y);
            }
        }
    }

    private void divCheck(Integer x, Integer y) throws CustomArithmeticException {
        if (check) {
            if ((x == Integer.MIN_VALUE) && (y == -1)) {
                throw new OverflowException(x + "/" + y);
            }
            if (y == 0) throw new DivideByZeroException();
        }
    }

    private void modCheck(Integer y) throws CustomArithmeticException {
        if (check) {
            if (y == 0) {
                throw new DivideByZeroException();
            }
        }
    }

    private void minValCheck(Integer x, String msg) throws CustomArithmeticException {
        if (check) {
            if (x == Integer.MIN_VALUE) {
                throw new OverflowException(String.format(msg, x));
            }
        }
    }

    private void squareCheck(Integer x) throws CustomArithmeticException {
        if (check) {
            if (Math.abs(x) > Math.sqrt(Integer.MAX_VALUE)) {
                throw new OverflowException("square(" + x + ")");
            }
        }
    }

    public Integer add(final Integer x, final Integer y) throws CustomArithmeticException {
        addCheck(x, y);
        return (x + y);
    }

    public Integer subtract(final Integer x, final Integer y) throws CustomArithmeticException {
        subCheck(x, y);
        return (x - y);
    }

    public Integer multiply(final Integer x, final Integer y) throws CustomArithmeticException {
        mulCheck(x, y);
        return (x * y);
    }

    public Integer divide(final Integer x, final Integer y) throws CustomArithmeticException {
        divCheck(x, y);
        return (x / y);
    }

    public Integer min(final Integer x, final Integer y) {
        return (Math.min(x, y));
    }

    public Integer max(final Integer x, final Integer y) {
        return (Math.max(x, y));
    }

    public Integer mod(final Integer x, final Integer y) throws CustomArithmeticException {
        modCheck(y);
        return (x % y);
    }

    public Integer abs(final Integer x) throws CustomArithmeticException {
        minValCheck(x, "abs(%d)");
        return Math.abs(x);
    }

    public Integer negate(final Integer x) throws CustomArithmeticException {
        minValCheck(x, "Overflow : -(%d)");
        return -x;
    }

    public Integer count(final Integer x) {
        return Integer.bitCount(x);
    }

    public Integer square(final Integer x) throws CustomArithmeticException { //undone
        squareCheck(x);
        return (x * x);
    }

    public Integer parse2Digit(String str) throws ParserException {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw new ParserException("Can't parse " + str + " to Integer");
        }
    }
}
