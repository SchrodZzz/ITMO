package generics;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;

public interface Types<T> {

    T add(final T x, final T y) throws CustomArithmeticException;

    T subtract(final T x, final T y) throws CustomArithmeticException;

    T divide(final T x, final T y) throws CustomArithmeticException;

    T multiply(final T x, final T y) throws CustomArithmeticException;

    T min(final T x, final T y);

    T max(final T x, final T y);

    T mod(final T x, final T y) throws CustomArithmeticException;

    T abs(final T x) throws CustomArithmeticException;

    T negate(final T x) throws CustomArithmeticException;

    T count(final T x) throws CustomArithmeticException;

    T square(final T x) throws CustomArithmeticException;

    T parse2Digit( final String str) throws ParserException;
}
