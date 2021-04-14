// Generated from /Users/schrod/prog/ITMO/term_5/TM/lab_4/source/GrammarGeneratingFile.g4 by ANTLR 4.8
package generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarGeneratingFileParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarGeneratingFileVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarGeneratingFileParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(GrammarGeneratingFileParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarGeneratingFileParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(GrammarGeneratingFileParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarGeneratingFileParser#rule_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRule_(GrammarGeneratingFileParser.Rule_Context ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarGeneratingFileParser#variant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariant(GrammarGeneratingFileParser.VariantContext ctx);
}