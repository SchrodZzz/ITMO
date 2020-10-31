// Generated from /Users/schrod/prog/ITMO/term_5/TM/lab_3/MyGrammar.g4 by ANTLR 4.8
package gen;

    import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MyGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MyGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(MyGrammarParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MyGrammarParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MyGrammarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MyGrammarParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(MyGrammarParser.NumberContext ctx);
}