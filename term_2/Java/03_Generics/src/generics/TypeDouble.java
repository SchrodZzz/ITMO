package generics;

import exceptions.ParserException;

public class TypeDouble implements Types<Double> {

    public Double add(final Double x, final Double y) {
        return (x + y);
    }

    public Double subtract(final Double x, final Double y) {
        return (x - y);
    }

    public Double multiply(final Double x, final Double y) {
        return (x * y);
    }

    public Double divide(final Double x, final Double y) {
        return (x / y);
    }

    public Double min(final Double x, final Double y) {
        return (Math.min(x, y));
    }

    public Double max(final Double x, final Double y) {
        return (Math.max(x, y));
    }

    public Double mod(final Double x, final Double y) {
        return (x % y);
    }

    public Double abs(final Double x) {
        return Math.abs(x);
    }

    public Double negate(final Double x) {
        return -x;
    }

    public Double count(final Double x) {
        return (double) Long.bitCount(Double.doubleToLongBits(x));
    }

    public Double square(final Double x) {
        return (x * x);
    }

    public Double parse2Digit(String str) throws ParserException {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            throw new ParserException("Can't parse " + str + " to Double");
        }
    }
}
