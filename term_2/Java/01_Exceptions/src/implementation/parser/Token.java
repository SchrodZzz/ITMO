package implementation.parser;

enum Token {
    EXPRESSION_BEGINNING,

    CLOSING_BRACE,
    OPENING_BRACE,

    CONST,
    VARIABLE,

    UNARY_MINUS,
    ABS,
    LOW,
    HIGH,

    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,

    LOG10,
    POW10,
    LOG,
    POW,

    SQRT,
    MIN,
    MAX
}
