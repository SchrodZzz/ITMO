package tabulator;

import exceptions.CustomArithmeticException;
import exceptions.ParserException;
import exceptions.UnsupportedTypeException;
import expressions.TripleExpression;
import generics.*;
import parser.ExpressionParser;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private Map<String, Types<?>> MODES = Map.of(
            "i", new TypeInteger(true),
            "d", new TypeDouble(),
            "bi", new TypeBigInteger(),
            "u", new TypeInteger(false),
            "b", new TypeByte(),
            "f", new TypeFloat(),
            "s", new TypeShort(),
            "l", new TypeLong()
    );

    public Object[][][] tabulate(final String mode, final String exprStr, final int x1, final int x2, final int y1,
                                 final int y2, final int z1, final int z2) throws UnsupportedTypeException, ParserException {
        return tabulate(getOperation(mode), exprStr, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] tabulate(final Types<T> op, final String exprStr, final int x1, final int x2,
                                      final int y1, final int y2, final int z1, final int z2)
            throws ParserException {

        final TripleExpression<T> expression = new ExpressionParser<>(op).parse(exprStr);
        final int dX = x2 - x1;
        final int dY = y2 - y1;
        final int dZ = z2 - z1;
        Object[][][] result = new Object[dX + 1][dY + 1][dZ + 1];
        for (int x = 0; x <= dX; x++) {
            for (int y = 0; y <= dY; y++) {
                for (int z = 0; z <= dZ; z++) {
                    try {
                        result[x][y][z] = expression.evaluate(op.parse2Digit(Integer.toString(x + x1)),
                                op.parse2Digit(Integer.toString(y + y1)),
                                op.parse2Digit(Integer.toString(z + z1)), op);
                    } catch (ArithmeticException | CustomArithmeticException e) {
                        // null assignment was here ^_^
                    }
                }
            }
        }
        return result;
    }

    private Types<?> getOperation(final String mode) throws UnsupportedTypeException {
        Types<?> result = MODES.get(mode);
        if (result == null) {
            throw new UnsupportedTypeException(mode);
        }
        return result;
    }
}
