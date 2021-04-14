// Generated from /Users/schrod/prog/ITMO/term_5/TM/lab_4/source/GrammarGeneratingFile.g4 by ANTLR 4.8
package generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarGeneratingFileLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, WS=6, EMPTY=7, SKIPP=8, STRING=9, 
		CODE=10, PARAM=11, IDENTIFIER=12, LEXEM=13, GRAMMAR_NAME=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "WS", "EMPTY", "SKIPP", "STRING", 
			"CODE", "PARAM", "LOWER_LETTER", "UPPER_LETTER", "DIGIT", "IDENTIFIER", 
			"LEXEM", "GRAMMAR_NAME"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'header'", "':'", "'|'", "'returns'", null, "'EMPTY'", 
			"'skip'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "WS", "EMPTY", "SKIPP", "STRING", 
			"CODE", "PARAM", "IDENTIFIER", "LEXEM", "GRAMMAR_NAME"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GrammarGeneratingFileLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GrammarGeneratingFile.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20\u0089\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\6\nN\n\n\r\n\16\nO\3\n\3\n\3\13\3\13\7\13V"+
		"\n\13\f\13\16\13Y\13\13\3\13\3\13\3\f\3\f\7\f_\n\f\f\f\16\fb\13\f\3\f"+
		"\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\7\20p\n\20\f\20\16"+
		"\20s\13\20\3\21\3\21\3\21\3\21\7\21y\n\21\f\21\16\21|\13\21\3\22\3\22"+
		"\5\22\u0080\n\22\3\22\3\22\3\22\7\22\u0085\n\22\f\22\16\22\u0088\13\22"+
		"\4W`\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\2\33\2"+
		"\35\2\37\16!\17#\20\3\2\7\5\2\13\f\17\17\"\"\3\2))\4\2aac|\4\2C\\aa\3"+
		"\2\62;\2\u0093\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2"+
		"\2\7.\3\2\2\2\t\60\3\2\2\2\13\62\3\2\2\2\r:\3\2\2\2\17>\3\2\2\2\21D\3"+
		"\2\2\2\23I\3\2\2\2\25S\3\2\2\2\27\\\3\2\2\2\31e\3\2\2\2\33g\3\2\2\2\35"+
		"i\3\2\2\2\37k\3\2\2\2!t\3\2\2\2#\177\3\2\2\2%&\7=\2\2&\4\3\2\2\2\'(\7"+
		"j\2\2()\7g\2\2)*\7c\2\2*+\7f\2\2+,\7g\2\2,-\7t\2\2-\6\3\2\2\2./\7<\2\2"+
		"/\b\3\2\2\2\60\61\7~\2\2\61\n\3\2\2\2\62\63\7t\2\2\63\64\7g\2\2\64\65"+
		"\7v\2\2\65\66\7w\2\2\66\67\7t\2\2\678\7p\2\289\7u\2\29\f\3\2\2\2:;\t\2"+
		"\2\2;<\3\2\2\2<=\b\7\2\2=\16\3\2\2\2>?\7G\2\2?@\7O\2\2@A\7R\2\2AB\7V\2"+
		"\2BC\7[\2\2C\20\3\2\2\2DE\7u\2\2EF\7m\2\2FG\7k\2\2GH\7r\2\2H\22\3\2\2"+
		"\2IM\7)\2\2JN\n\3\2\2KL\7^\2\2LN\7)\2\2MJ\3\2\2\2MK\3\2\2\2NO\3\2\2\2"+
		"OM\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QR\7)\2\2R\24\3\2\2\2SW\7}\2\2TV\13\2\2"+
		"\2UT\3\2\2\2VY\3\2\2\2WX\3\2\2\2WU\3\2\2\2XZ\3\2\2\2YW\3\2\2\2Z[\7\177"+
		"\2\2[\26\3\2\2\2\\`\7]\2\2]_\13\2\2\2^]\3\2\2\2_b\3\2\2\2`a\3\2\2\2`^"+
		"\3\2\2\2ac\3\2\2\2b`\3\2\2\2cd\7_\2\2d\30\3\2\2\2ef\t\4\2\2f\32\3\2\2"+
		"\2gh\t\5\2\2h\34\3\2\2\2ij\t\6\2\2j\36\3\2\2\2kq\5\31\r\2lp\5\31\r\2m"+
		"p\5\35\17\2np\7a\2\2ol\3\2\2\2om\3\2\2\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2"+
		"qr\3\2\2\2r \3\2\2\2sq\3\2\2\2tz\5\33\16\2uy\5\33\16\2vy\5\35\17\2wy\7"+
		"a\2\2xu\3\2\2\2xv\3\2\2\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{\"\3"+
		"\2\2\2|z\3\2\2\2}\u0080\5\33\16\2~\u0080\5\31\r\2\177}\3\2\2\2\177~\3"+
		"\2\2\2\u0080\u0086\3\2\2\2\u0081\u0085\5\33\16\2\u0082\u0085\5\31\r\2"+
		"\u0083\u0085\5\35\17\2\u0084\u0081\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0083"+
		"\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"$\3\2\2\2\u0088\u0086\3\2\2\2\16\2MOW`oqxz\177\u0084\u0086\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}