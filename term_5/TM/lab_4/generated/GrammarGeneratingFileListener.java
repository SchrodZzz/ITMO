// Generated from /Users/schrod/prog/ITMO/term_5/TM/lab_4/source/GrammarGeneratingFile.g4 by ANTLR 4.8
package generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarGeneratingFileParser}.
 */
public interface GrammarGeneratingFileListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarGeneratingFileParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(GrammarGeneratingFileParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarGeneratingFileParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(GrammarGeneratingFileParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarGeneratingFileParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(GrammarGeneratingFileParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarGeneratingFileParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(GrammarGeneratingFileParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarGeneratingFileParser#rule_}.
	 * @param ctx the parse tree
	 */
	void enterRule_(GrammarGeneratingFileParser.Rule_Context ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarGeneratingFileParser#rule_}.
	 * @param ctx the parse tree
	 */
	void exitRule_(GrammarGeneratingFileParser.Rule_Context ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarGeneratingFileParser#variant}.
	 * @param ctx the parse tree
	 */
	void enterVariant(GrammarGeneratingFileParser.VariantContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarGeneratingFileParser#variant}.
	 * @param ctx the parse tree
	 */
	void exitVariant(GrammarGeneratingFileParser.VariantContext ctx);
}