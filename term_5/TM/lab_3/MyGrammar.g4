grammar MyGrammar;

// https://blog.dgunia.de/2017/10/26/creating-and-testing-an-antlr-parser-with-intellij-idea-or-android-studio/

@header {
    import java.util.*;
}

@members {
    private Map<String, Integer> holder = new HashMap();
    public List<Map.Entry<String, Integer>> assignments = new ArrayList();
}



// MARK: - Parser

main : (assignment ';')*;

assignment returns [int val]
    : IDENTIFIER '=' expression {
            holder.put($IDENTIFIER.text, $expression.val);
            assignments.add(new AbstractMap.SimpleEntry<>($IDENTIFIER.text, $expression.val));
            $val = $expression.val;
    };

// + ... | - ... => a = 2 + 3 - 5 + 100 == -100
expression returns [int val]
    : IDENTIFIER { $val = holder.get($IDENTIFIER.text); }
    | number { $val = $number.val; }
    | MINUS expression { $val = -$expression.val; }
    | assignment { $val = $assignment.val; }
    | <assoc=right> left=expression POW rigth=expression { $val = (int) Math.pow($left.val, $rigth.val); }
    | left=expression op=(MUL|DIV) rigth=expression { $val = $op.type == MUL ? $left.val * $rigth.val : $left.val / $rigth.val; }
    | left=expression op=(PLUS|MINUS) rigth=expression { $val = $op.type == PLUS ? $left.val + $rigth.val : $left.val - $rigth.val; }
    | expression ESTER expression { $val = 1337; }
    | LBRACER expression RBRACER { $val = $expression.val; }
    ;

number returns [int val]
    : NUMBER { $val = Integer.parseInt($NUMBER.text); }
    | MINUS NUMBER { $val = Integer.parseInt('-' + $NUMBER.text); }
    ;




// MARK: - Lexer

WHITESPACES : [ \n\t\r] -> skip;

PLUS        : '+';
MINUS       : '-';
MUL         : '*';
POW         : '**';
DIV         : '/';
ESTER       : '@';
LBRACER     : '(';
RBRACER     : ')';
NUMBER      : [0-9]+;
IDENTIFIER  : [_A-Za-z] ([_A-Za-z] | [0-9])*;