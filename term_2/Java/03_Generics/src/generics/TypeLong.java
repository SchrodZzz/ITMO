package generics;

import exceptions.ParserException;

public class TypeLong implements Types<Long> {

    public Long add(final Long x, final Long y) {
        return (x + y);
    }

    public Long subtract(final Long x, final Long y) {
        return (x - y);
    }

    public Long multiply(final Long x, final Long y) {
        return (x * y);
    }

    public Long divide(final Long x, final Long y) {
        return (x / y);
    }

    public Long min(final Long x, final Long y) {
        return (Math.min(x, y));
    }

    public Long max(final Long x, final Long y) {
        return (Math.max(x, y));
    }

    public Long mod(final Long x, final Long y) {
        return (x % y);
    }

    public Long abs(final Long x) {
        return Math.abs(x);
    }

    public Long negate(final Long x) {
        return -x;
    }

    public Long count(final Long x) {
        return (long) Long.bitCount(x);
    }

    public Long square(final Long x) {
        return (x * x);
    }

    public Long parse2Digit(String str) throws ParserException {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            throw new ParserException("Can't parse " + str + " to Long");
        }
    }
}
