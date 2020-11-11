// Generated from /Users/schrod/prog/ITMO/term_5/TM/lab_3/MyGrammar.g4 by ANTLR 4.8
package gen;

    import java.util.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MyGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WHITESPACES=3, PLUS=4, MINUS=5, MUL=6, POW=7, DIV=8, ESTER=9, 
		LBRACER=10, RBRACER=11, NUMBER=12, IDENTIFIER=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "WHITESPACES", "PLUS", "MINUS", "MUL", "POW", "DIV", 
			"ESTER", "LBRACER", "RBRACER", "NUMBER", "IDENTIFIER"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'='", null, "'+'", "'-'", "'*'", "'**'", "'/'", "'@'", 
			"'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "WHITESPACES", "PLUS", "MINUS", "MUL", "POW", "DIV", 
			"ESTER", "LBRACER", "RBRACER", "NUMBER", "IDENTIFIER"
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


	    private Map<String, Integer> holder = new HashMap();
	    public List<Map.Entry<String, Integer>> assignments = new ArrayList();


	public MyGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MyGrammar.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17B\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5"+
		"\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\6\r"+
		"8\n\r\r\r\16\r9\3\16\3\16\7\16>\n\16\f\16\16\16A\13\16\2\2\17\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\3\2\6\5\2\13\f"+
		"\17\17\"\"\3\2\62;\5\2C\\aac|\6\2\62;C\\aac|\2C\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\3\35\3\2\2\2\5\37\3\2\2\2\7!\3\2\2\2\t%\3\2\2\2\13\'\3\2\2\2\r)\3"+
		"\2\2\2\17+\3\2\2\2\21.\3\2\2\2\23\60\3\2\2\2\25\62\3\2\2\2\27\64\3\2\2"+
		"\2\31\67\3\2\2\2\33;\3\2\2\2\35\36\7=\2\2\36\4\3\2\2\2\37 \7?\2\2 \6\3"+
		"\2\2\2!\"\t\2\2\2\"#\3\2\2\2#$\b\4\2\2$\b\3\2\2\2%&\7-\2\2&\n\3\2\2\2"+
		"\'(\7/\2\2(\f\3\2\2\2)*\7,\2\2*\16\3\2\2\2+,\7,\2\2,-\7,\2\2-\20\3\2\2"+
		"\2./\7\61\2\2/\22\3\2\2\2\60\61\7B\2\2\61\24\3\2\2\2\62\63\7*\2\2\63\26"+
		"\3\2\2\2\64\65\7+\2\2\65\30\3\2\2\2\668\t\3\2\2\67\66\3\2\2\289\3\2\2"+
		"\29\67\3\2\2\29:\3\2\2\2:\32\3\2\2\2;?\t\4\2\2<>\t\5\2\2=<\3\2\2\2>A\3"+
		"\2\2\2?=\3\2\2\2?@\3\2\2\2@\34\3\2\2\2A?\3\2\2\2\6\29=?\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}