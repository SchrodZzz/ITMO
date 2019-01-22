package parser;

enum Token {
    EXPRESSION_BEGIN,
    EXPRESSION_END,
    CLOSE_BRACE,
    OPEN_BRACE,

    CONST,
    VARIABLE,

    UNARY_MINUS,

    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,

    LOG10,
    POW10,
    LOG,
    POW,

    ABS,
    SQRT,
    MIN,
    MAX
}