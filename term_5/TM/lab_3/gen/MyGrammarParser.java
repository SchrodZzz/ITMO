// Generated from /Users/schrod/prog/ITMO/term_5/TM/lab_3/MyGrammar.g4 by ANTLR 4.8
package gen;

    import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MyGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WHITESPACES=3, PLUS=4, MINUS=5, MUL=6, POW=7, DIV=8, ESTER=9, 
		LBRACER=10, RBRACER=11, NUMBER=12, IDENTIFIER=13;
	public static final int
		RULE_main = 0, RULE_assignment = 1, RULE_expression = 2, RULE_number = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"main", "assignment", "expression", "number"
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

	@Override
	public String getGrammarFileName() { return "MyGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    private Map<String, Integer> holder = new HashMap();
	    public List<Map.Entry<String, Integer>> assignments = new ArrayList();

	public MyGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class MainContext extends ParserRuleContext {
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitMain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitMain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_main);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(8);
				assignment();
				setState(9);
				match(T__0);
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public int val;
		public Token IDENTIFIER;
		public ExpressionContext expression;
		public TerminalNode IDENTIFIER() { return getToken(MyGrammarParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			((AssignmentContext)_localctx).IDENTIFIER = match(IDENTIFIER);
			setState(17);
			match(T__1);
			setState(18);
			((AssignmentContext)_localctx).expression = expression(0);

			            holder.put((((AssignmentContext)_localctx).IDENTIFIER!=null?((AssignmentContext)_localctx).IDENTIFIER.getText():null), ((AssignmentContext)_localctx).expression.val);
			            assignments.add(new AbstractMap.SimpleEntry<>((((AssignmentContext)_localctx).IDENTIFIER!=null?((AssignmentContext)_localctx).IDENTIFIER.getText():null), ((AssignmentContext)_localctx).expression.val));
			            ((AssignmentContext)_localctx).val =  ((AssignmentContext)_localctx).expression.val;
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public int val;
		public ExpressionContext left;
		public Token IDENTIFIER;
		public NumberContext number;
		public ExpressionContext expression;
		public AssignmentContext assignment;
		public ExpressionContext rigth;
		public Token op;
		public TerminalNode IDENTIFIER() { return getToken(MyGrammarParser.IDENTIFIER, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(MyGrammarParser.MINUS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public TerminalNode LBRACER() { return getToken(MyGrammarParser.LBRACER, 0); }
		public TerminalNode RBRACER() { return getToken(MyGrammarParser.RBRACER, 0); }
		public TerminalNode POW() { return getToken(MyGrammarParser.POW, 0); }
		public TerminalNode MUL() { return getToken(MyGrammarParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(MyGrammarParser.DIV, 0); }
		public TerminalNode PLUS() { return getToken(MyGrammarParser.PLUS, 0); }
		public TerminalNode ESTER() { return getToken(MyGrammarParser.ESTER, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(22);
				((ExpressionContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 ((ExpressionContext)_localctx).val =  holder.get((((ExpressionContext)_localctx).IDENTIFIER!=null?((ExpressionContext)_localctx).IDENTIFIER.getText():null)); 
				}
				break;
			case 2:
				{
				setState(24);
				((ExpressionContext)_localctx).number = number();
				 ((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).number.val; 
				}
				break;
			case 3:
				{
				setState(27);
				match(MINUS);
				setState(28);
				((ExpressionContext)_localctx).expression = expression(7);
				 ((ExpressionContext)_localctx).val =  -((ExpressionContext)_localctx).expression.val; 
				}
				break;
			case 4:
				{
				setState(31);
				((ExpressionContext)_localctx).assignment = assignment();
				 ((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).assignment.val; 
				}
				break;
			case 5:
				{
				setState(34);
				match(LBRACER);
				setState(35);
				((ExpressionContext)_localctx).expression = expression(0);
				setState(36);
				match(RBRACER);
				 ((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).expression.val; 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(63);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(61);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(41);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(42);
						match(POW);
						setState(43);
						((ExpressionContext)_localctx).rigth = ((ExpressionContext)_localctx).expression = expression(5);
						 ((ExpressionContext)_localctx).val =  (int) Math.pow(((ExpressionContext)_localctx).left.val, ((ExpressionContext)_localctx).rigth.val); 
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(46);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(47);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(48);
						((ExpressionContext)_localctx).rigth = ((ExpressionContext)_localctx).expression = expression(5);
						 ((ExpressionContext)_localctx).val =  (((ExpressionContext)_localctx).op!=null?((ExpressionContext)_localctx).op.getType():0) == MUL ? ((ExpressionContext)_localctx).left.val * ((ExpressionContext)_localctx).rigth.val : ((ExpressionContext)_localctx).left.val / ((ExpressionContext)_localctx).rigth.val; 
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(51);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(52);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(53);
						((ExpressionContext)_localctx).rigth = ((ExpressionContext)_localctx).expression = expression(4);
						 ((ExpressionContext)_localctx).val =  (((ExpressionContext)_localctx).op!=null?((ExpressionContext)_localctx).op.getType():0) == PLUS ? ((ExpressionContext)_localctx).left.val + ((ExpressionContext)_localctx).rigth.val : ((ExpressionContext)_localctx).left.val - ((ExpressionContext)_localctx).rigth.val; 
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(56);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(57);
						match(ESTER);
						setState(58);
						((ExpressionContext)_localctx).expression = expression(3);
						 ((ExpressionContext)_localctx).val =  1337; 
						}
						break;
					}
					} 
				}
				setState(65);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public int val;
		public Token NUMBER;
		public TerminalNode NUMBER() { return getToken(MyGrammarParser.NUMBER, 0); }
		public TerminalNode MINUS() { return getToken(MyGrammarParser.MINUS, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyGrammarListener ) ((MyGrammarListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyGrammarVisitor ) return ((MyGrammarVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_number);
		try {
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				((NumberContext)_localctx).NUMBER = match(NUMBER);
				 ((NumberContext)_localctx).val =  Integer.parseInt((((NumberContext)_localctx).NUMBER!=null?((NumberContext)_localctx).NUMBER.getText():null)); 
				}
				break;
			case MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				match(MINUS);
				setState(69);
				((NumberContext)_localctx).NUMBER = match(NUMBER);
				 ((NumberContext)_localctx).val =  Integer.parseInt('-' + (((NumberContext)_localctx).NUMBER!=null?((NumberContext)_localctx).NUMBER.getText():null)); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\17L\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\3\2\3\2\7\2\16\n\2\f\2\16\2\21\13\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\5\4*\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4@\n\4\f\4\16\4C\13\4\3\5\3\5\3\5\3\5"+
		"\3\5\5\5J\n\5\3\5\2\3\6\6\2\4\6\b\2\4\4\2\b\b\n\n\3\2\6\7\2Q\2\17\3\2"+
		"\2\2\4\22\3\2\2\2\6)\3\2\2\2\bI\3\2\2\2\n\13\5\4\3\2\13\f\7\3\2\2\f\16"+
		"\3\2\2\2\r\n\3\2\2\2\16\21\3\2\2\2\17\r\3\2\2\2\17\20\3\2\2\2\20\3\3\2"+
		"\2\2\21\17\3\2\2\2\22\23\7\17\2\2\23\24\7\4\2\2\24\25\5\6\4\2\25\26\b"+
		"\3\1\2\26\5\3\2\2\2\27\30\b\4\1\2\30\31\7\17\2\2\31*\b\4\1\2\32\33\5\b"+
		"\5\2\33\34\b\4\1\2\34*\3\2\2\2\35\36\7\7\2\2\36\37\5\6\4\t\37 \b\4\1\2"+
		" *\3\2\2\2!\"\5\4\3\2\"#\b\4\1\2#*\3\2\2\2$%\7\f\2\2%&\5\6\4\2&\'\7\r"+
		"\2\2\'(\b\4\1\2(*\3\2\2\2)\27\3\2\2\2)\32\3\2\2\2)\35\3\2\2\2)!\3\2\2"+
		"\2)$\3\2\2\2*A\3\2\2\2+,\f\7\2\2,-\7\t\2\2-.\5\6\4\7./\b\4\1\2/@\3\2\2"+
		"\2\60\61\f\6\2\2\61\62\t\2\2\2\62\63\5\6\4\7\63\64\b\4\1\2\64@\3\2\2\2"+
		"\65\66\f\5\2\2\66\67\t\3\2\2\678\5\6\4\689\b\4\1\29@\3\2\2\2:;\f\4\2\2"+
		";<\7\13\2\2<=\5\6\4\5=>\b\4\1\2>@\3\2\2\2?+\3\2\2\2?\60\3\2\2\2?\65\3"+
		"\2\2\2?:\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\7\3\2\2\2CA\3\2\2\2DE"+
		"\7\16\2\2EJ\b\5\1\2FG\7\7\2\2GH\7\16\2\2HJ\b\5\1\2ID\3\2\2\2IF\3\2\2\2"+
		"J\t\3\2\2\2\7\17)?AI";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}