package generics;

import exceptions.ParserException;

public class TypeFloat implements Types<Float> {

    public Float add(final Float x, final Float y) {
        return (x + y);
    }

    public Float subtract(final Float x, final Float y) {
        return (x - y);
    }

    public Float multiply(final Float x, final Float y) {
        return (x * y);
    }

    public Float divide(final Float x, final Float y) {
        return (x / y);
    }

    public Float min(final Float x, final Float y) {
        return (Math.min(x, y));
    }

    public Float max(final Float x, final Float y) {
        return (Math.max(x, y));
    }

    public Float mod(final Float x, final Float y) {
        return (x % y);
    }

    public Float abs(final Float x) {
        return Math.abs(x);
    }

    public Float negate(final Float x) {
        return -x;
    }

    public Float count(final Float x) {
        return (float) Integer.bitCount(Float.floatToIntBits(x));
    }

    public Float square(final Float x) {
        return (x * x);
    }

    public Float parse2Digit(String str) throws ParserException {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            throw new ParserException("Can't parse " + str + " to Float");
        }
    }
}
