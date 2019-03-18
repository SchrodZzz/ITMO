package generics;

import exceptions.CustomArithmeticException;
import exceptions.DivideByZeroException;
import exceptions.ParserException;

import java.math.BigInteger;

public class TypeBigInteger implements Types<BigInteger> {

    public BigInteger add(final BigInteger x, final BigInteger y) {
        return x.add(y);
    }

    public BigInteger subtract(final BigInteger x, final BigInteger y) {
        return x.subtract(y);
    }

    public BigInteger multiply(final BigInteger x, final BigInteger y) {
        return x.multiply(y);
    }

    public BigInteger divide(final BigInteger x, final BigInteger y)  throws CustomArithmeticException {
        if (y.equals(BigInteger.ZERO)) {
            throw new DivideByZeroException();
        }
        return x.divide(y);
    }

    public BigInteger min(final BigInteger x, final BigInteger y) {
        return x.min(y);
    }

    public BigInteger max(final BigInteger x, final BigInteger y) {
        return x.max(y);
    }

    public BigInteger mod(final BigInteger x, final BigInteger y) {
        return x.mod(y);
    }

    public BigInteger abs(final BigInteger x) {
        return x.abs();
    }

    public BigInteger negate(final BigInteger x) {
        return x.negate();
    }

    public BigInteger count(final BigInteger x) {
        return BigInteger.valueOf(x.bitCount());
    }

    public BigInteger square(final BigInteger x) {
        return x.multiply(x);
    }

    public BigInteger parse2Digit(String str) throws ParserException {
        try {
            return new BigInteger(str);
        } catch (Exception e) {
            throw new ParserException("Can't parse " + str + " to Double");
        }
    }
}
