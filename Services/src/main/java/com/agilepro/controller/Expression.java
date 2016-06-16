package com.agilepro.controller;
/*
 * Copyright 2012 Udo Klimaschewski
 * 
 * http://UdoJava.com/
 * http://about.me/udo.klimaschewski
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * <h1>EvalEx - Java Expression Evaluator</h1>
 * 
 * <h2>Introduction</h2> EvalEx is a handy expression evaluator for Java, that
 * allows to evaluate simple mathematical and boolean expressions. <br>
 * Key Features:
 * <ul>
 * <li>Uses BigDecimal for calculation and result</li>
 * <li>Single class implementation, very compact</li>
 * <li>No dependencies to external libraries</li>
 * <li>Precision and rounding mode can be set</li>
 * <li>Supports variables</li>
 * <li>Standard boolean and mathematical operators</li>
 * <li>Standard basic mathematical and boolean functions</li>
 * <li>Custom functions and operators can be added at runtime</li>
 * </ul>
 * <br>
 * <h2>Examples</h2>
 * 
 * <pre>
 *  BigDecimal result = null;
 *  
 *  Expression expression = new Expression("1+1/3");
 *  result = expression.eval():
 *  expression.setPrecision(2);
 *  result = expression.eval():
 *  
 *  result = new Expression("(3.4 + -4.1)/2").eval();
 *  
 *  result = new Expression("SQRT(a^2 + b^2").with("a","2.4").and("b","9.253").eval();
 *  
 *  BigDecimal a = new BigDecimal("2.4");
 *  BigDecimal b = new BigDecimal("9.235");
 *  result = new Expression("SQRT(a^2 + b^2").with("a",a).and("b",b).eval();
 *  
 *  result = new Expression("2.4/PI").setPrecision(128).setRoundingMode(RoundingMode.UP).eval();
 *  
 *  result = new Expression("random() > 0.5").eval();
 * 
 *  result = new Expression("not(x<7 || sqrt(max(x,9)) <= 3))").with("x","22.9").eval();
 * </pre>
 * 
 * <br>
 * <h2>Supported Operators</h2>
 * <table>
 * <tr>
 * <th>Mathematical Operators</th>
 * </tr>
 * <tr>
 * <th>Operator</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>+</td>
 * <td>Additive operator</td>
 * </tr>
 * <tr>
 * <td>-</td>
 * <td>Subtraction operator</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>Multiplication operator</td>
 * </tr>
 * <tr>
 * <td>/</td>
 * <td>Division operator</td>
 * </tr>
 * <tr>
 * <td>%</td>
 * <td>Remainder operator (Modulo)</td>
 * </tr>
 * <tr>
 * <td>^</td>
 * <td>Power operator</td>
 * </tr>
 * </table>
 * <br>
 * <table>
 * <tr>
 * <th>Boolean Operators<sup>*</sup></th>
 * </tr>
 * <tr>
 * <th>Operator</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>=</td>
 * <td>Equals</td>
 * </tr>
 * <tr>
 * <td>==</td>
 * <td>Equals</td>
 * </tr>
 * <tr>
 * <td>!=</td>
 * <td>Not equals</td>
 * </tr>
 * <tr>
 * <td>&lt;&gt;</td>
 * <td>Not equals</td>
 * </tr>
 * <tr>
 * <td>&lt;</td>
 * <td>Less than</td>
 * </tr>
 * <tr>
 * <td>&lt;=</td>
 * <td>Less than or equal to</td>
 * </tr>
 * <tr>
 * <td>&gt;</td>
 * <td>Greater than</td>
 * </tr>
 * <tr>
 * <td>&gt;=</td>
 * <td>Greater than or equal to</td>
 * </tr>
 * <tr>
 * <td>&amp;&amp;</td>
 * <td>Boolean and</td>
 * </tr>
 * <tr>
 * <td>||</td>
 * <td>Boolean or</td>
 * </tr>
 * </table>
 * *Boolean operators result always in a BigDecimal value of 1 or 0 (zero). Any
 * non-zero value is treated as a _true_ value. Boolean _not_ is implemented by
 * a function. <br>
 * <h2>Supported Functions</h2>
 * <table>
 * <tr>
 * <th>Function<sup>*</sup></th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>NOT(<i>expression</i>)</td>
 * <td>Boolean negation, 1 (means true) if the expression is not zero</td>
 * </tr>
 * <tr>
 * <td>IF(<i>condition</i>,<i>value_if_true</i>,<i>value_if_false</i>)</td>
 * <td>Returns one value if the condition evaluates to true or the other if it
 * evaluates to false</td>
 * </tr>
 * <tr>
 * <td>RANDOM()</td>
 * <td>Produces a random number between 0 and 1</td>
 * </tr>
 * <tr>
 * <td>MIN(<i>e1</i>,<i>e2</i>, <i>...</i>)</td>
 * <td>Returns the smallest of the given expressions</td>
 * </tr>
 * <tr>
 * <td>MAX(<i>e1</i>,<i>e2</i>, <i>...</i>)</td>
 * <td>Returns the biggest of the given expressions</td>
 * </tr>
 * <tr>
 * <td>ABS(<i>expression</i>)</td>
 * <td>Returns the absolute (non-negative) value of the expression</td>
 * </tr>
 * <tr>
 * <td>ROUND(<i>expression</i>,precision)</td>
 * <td>Rounds a value to a certain number of digits, uses the current rounding
 * mode</td>
 * </tr>
 * <tr>
 * <td>FLOOR(<i>expression</i>)</td>
 * <td>Rounds the value down to the nearest integer</td>
 * </tr>
 * <tr>
 * <td>CEILING(<i>expression</i>)</td>
 * <td>Rounds the value up to the nearest integer</td>
 * </tr>
 * <tr>
 * <td>LOG(<i>expression</i>)</td>
 * <td>Returns the natural logarithm (base e) of an expression</td>
 * </tr>
 * <tr>
 * <td>LOG10(<i>expression</i>)</td>
 * <td>Returns the common logarithm (base 10) of an expression</td>
 * </tr>
 * <tr>
 * <td>SQRT(<i>expression</i>)</td>
 * <td>Returns the square root of an expression</td>
 * </tr>
 * <tr>
 * <td>SIN(<i>expression</i>)</td>
 * <td>Returns the trigonometric sine of an angle (in degrees)</td>
 * </tr>
 * <tr>
 * <td>COS(<i>expression</i>)</td>
 * <td>Returns the trigonometric cosine of an angle (in degrees)</td>
 * </tr>
 * <tr>
 * <td>TAN(<i>expression</i>)</td>
 * <td>Returns the trigonometric tangens of an angle (in degrees)</td>
 * </tr>
 * <tr>
 * <td>ASIN(<i>expression</i>)</td>
 * <td>Returns the angle of asin (in degrees)</td>
 * </tr>
 * <tr>
 * <td>ACOS(<i>expression</i>)</td>
 * <td>Returns the angle of acos (in degrees)</td>
 * </tr>
 * <tr>
 * <td>ATAN(<i>expression</i>)</td>
 * <td>Returns the angle of atan (in degrees)</td>
 * </tr>
 * <tr>
 * <td>SINH(<i>expression</i>)</td>
 * <td>Returns the hyperbolic sine of a value</td>
 * </tr>
 * <tr>
 * <td>COSH(<i>expression</i>)</td>
 * <td>Returns the hyperbolic cosine of a value</td>
 * </tr>
 * <tr>
 * <td>TANH(<i>expression</i>)</td>
 * <td>Returns the hyperbolic tangens of a value</td>
 * </tr>
 * <tr>
 * <td>RAD(<i>expression</i>)</td>
 * <td>Converts an angle measured in degrees to an approximately equivalent
 * angle measured in radians</td>
 * </tr>
 * <tr>
 * <td>DEG(<i>expression</i>)</td>
 * <td>Converts an angle measured in radians to an approximately equivalent
 * angle measured in degrees</td>
 * </tr>
 * </table>
 * *Functions names are case insensitive. <br>
 * <h2>Supported Constants</h2>
 * <table>
 * <tr>
 * <th>Constant</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>PI</td>
 * <td>The value of <i>PI</i>, exact to 100 digits</td>
 * </tr>
 * <tr>
 * <td>TRUE</td>
 * <td>The value one</td>
 * </tr>
 * <tr>
 * <td>FALSE</td>
 * <td>The value zero</td>
 * </tr>
 * </table>
 * Custom operators can be added easily, simply create an instance of
 * `Expression.Operator` and add it to the expression. Parameters are the
 * operator string, its precedence and if it is left associative. The operators
 * `eval()` method will be called with the BigDecimal values of the operands.
 * All existing operators can also be overridden. <br>
 * For example, add an operator `x >> n`, that moves the decimal point of _x_
 * _n_ digits to the right:
 * Adding custom functions is as easy as adding custom operators. Create an
 * instance of `Expression.Function`and add it to the expression. Parameters are
 * the function name and the count of required parameters. The functions
 * `eval()` method will be called with a list of the BigDecimal parameters. All
 * existing functions can also be overridden. <br>
 * A <code>-1</code> as the number of parameters denotes a variable number of
 * arguments.<br>
 * For example, add a function `average(a,b,c)`, that will calculate the average
 * value of a, b and c: <br>
 * The software is licensed under the MIT Open Source license (see LICENSE
 * file). <br>
 * <li>The *power of* operator (^) implementation was copied from [Stack
 * Overflow
 * ](http://stackoverflow.com/questions/3579779/how-to-do-a-fractional-power
 * -on-bigdecimal-in-java) Thanks to Gene Marin</li>
 * <li>The SQRT() function implementation was taken from the book [The Java
 * Programmers Guide To numerical
 * Computing](http://www.amazon.de/Java-Number-Cruncher
 * -Programmers-Numerical/dp/0130460419) (Ronald Mak, 2002)</li>
 * 
 * 
 * @author Udo Klimaschewski (http://about.me/udo.klimaschewski)
 */
public class Expression
{

	/**
	 * Definition of PI as a constant, can be used in expressions as variable.
	 */
	public static final BigDecimal PI = new BigDecimal("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679");

	/**
	 * The BigDecimal representation of the left parenthesis, used for parsing
	 * varying numbers of function parameters.
	 */
	private static final BigDecimal PARAMS_START = new BigDecimal(0);

	/**
	 * The Constant WORD_PATTERN.
	 */
	private static final Pattern WORD_PATTERN = Pattern.compile("\\w+");

	/**
	 * The brace.
	 */
	private static final String BRACE = "(";

	/**
	 * The close brace.
	 */
	private static final String CLOSE_BRACE = ")";

	/**
	 * The comma.
	 */
	private static final String COMMA = ",";

	/**
	 * What character to use for decimal separators.
	 */
	private static final char DEC_SEPARATOR = '.';

	/**
	 * What character to use for minus sign (negative values).
	 */
	private static final char MINUS_SIGN = '-';

	/**
	 * The {@link MathContext} to use for calculations.
	 */
	private MathContext mc = null;

	/**
	 * The original infix expression.
	 */
	private String expression = null;

	/**
	 * The cached RPN (Reverse Polish Notation) of the expression.
	 */
	private List<String> rpn = null;

	/**
	 * All defined operators with name and implementation.
	 */
	private Map<String, Operator> operators = new HashMap<String, Expression.Operator>();

	/**
	 * All defined functions with name and implementation.
	 */
	private Map<String, Function> functions = new TreeMap<String, Expression.Function>();

	/**
	 * All defined variables with name and value.
	 */
	private Map<String, BigDecimal> variables = new HashMap<String, BigDecimal>();

	/**
	 * The expression evaluators exception class.
	 */
	public static class ExpressionException extends RuntimeException
	{

		/**
		 * The Constant serialVersionUID.
		 */
		private static final long serialVersionUID = 1118142866870779047L;

		/**
		 * Instantiates a new expression exception.
		 *
		 * @param message
		 *            the message
		 */
		public ExpressionException(String message)
		{
			super(message);
		}
	}

	/**
	 * Abstract definition of a supported expression function. A function is
	 * defined by a name, the number of parameters and the actual processing
	 * implementation.
	 */
	public abstract class Function
	{
		/**
		 * Name of this function.
		 */
		private String name;
		/**
		 * Number of parameters expected for this function. <code>-1</code>
		 * denotes a variable number of parameters.
		 */
		private int numParams;

		/**
		 * Description about the function.
		 */
		private String description;

		/**
		 * Creates a new function with given name and parameter count.
		 * 
		 * @param name
		 *            The name of the function.
		 * @param numParams
		 *            The number of parameters for this function.
		 *            <code>-1</code> denotes a variable number of parameters.
		 * @param description
		 *            the function description
		 */
		public Function(String name, int numParams, String description)
		{
			this.name = name.toUpperCase(Locale.ROOT);
			this.numParams = numParams;
			this.description = description;
		}

		/**
		 * Gets the name of this function.
		 *
		 * @return the name of this function
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * Gets the number of parameters expected for this function.
		 *
		 * @return the number of parameters expected for this function
		 */
		public int getNumParams()
		{
			return numParams;
		}

		/**
		 * Num params varies.
		 *
		 * @return true, if successful
		 */
		public boolean numParamsVaries()
		{
			return numParams < 0;
		}

		/**
		 * Gets the description about the function.
		 *
		 * @return the description about the function
		 */
		public String getDescription()
		{
			return description;
		}

		/**
		 * Implementation for this function.
		 * 
		 * @param parameters
		 *            Parameters will be passed by the expression evaluator as a
		 *            {@link List} of {@link BigDecimal} values.
		 * @return The function must return a new {@link BigDecimal} value as a
		 *         computing result.
		 */
		public abstract BigDecimal eval(List<BigDecimal> parameters);
	}

	/**
	 * Abstract definition of a supported operator. An operator is defined by
	 * its name (pattern), precedence and if it is left- or right associative.
	 */
	public abstract class Operator
	{
		/**
		 * This operators name (pattern).
		 */
		private String oper;
		/**
		 * Operators precedence.
		 */
		private int precedence;
		/**
		 * Operator is left associative.
		 */
		private boolean leftAssoc;

		/**
		 * Creates a new operator.
		 * 
		 * @param oper
		 *            The operator name (pattern).
		 * @param precedence
		 *            The operators precedence.
		 * @param leftAssoc
		 *            <code>true</code> if the operator is left associative,
		 *            else <code>false</code>.
		 */
		public Operator(String oper, int precedence, boolean leftAssoc)
		{
			this.oper = oper;
			this.precedence = precedence;
			this.leftAssoc = leftAssoc;
		}

		/**
		 * Gets the this operators name (pattern).
		 *
		 * @return the this operators name (pattern)
		 */
		public String getOper()
		{
			return oper;
		}

		/**
		 * Gets the operators precedence.
		 *
		 * @return the operators precedence
		 */
		public int getPrecedence()
		{
			return precedence;
		}

		/**
		 * Checks if is operator is left associative.
		 *
		 * @return the operator is left associative
		 */
		public boolean isLeftAssoc()
		{
			return leftAssoc;
		}

		/**
		 * Implementation for this operator.
		 * 
		 * @param v1
		 *            Operand 1.
		 * @param v2
		 *            Operand 2.
		 * @return The result of the operation.
		 */
		public abstract BigDecimal eval(BigDecimal v1, BigDecimal v2);
	}

	/**
	 * Expression tokenizer that allows to iterate over a {@link String}
	 * expression token by token. Blank characters will be skipped.
	 */
	private class Tokenizer implements Iterator<String>
	{

		/**
		 * Actual position in expression string.
		 */
		private int pos = 0;

		/**
		 * The original input expression.
		 */
		private String input;
		/**
		 * The previous token or <code>null</code> if none.
		 */
		private String previousToken;

		/**
		 * Creates a new tokenizer for an expression.
		 * 
		 * @param input
		 *            The expression string.
		 */
		public Tokenizer(String input)
		{
			this.input = input.trim();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext()
		{
			return (pos < input.length());
		}

		/**
		 * Peek at the next character, without advancing the iterator.
		 * 
		 * @return The next character or character 0, if at end of string.
		 */
		private char peekNextChar()
		{
			if(pos < (input.length() - 1))
			{
				return input.charAt(pos + 1);
			}
			else
			{
				return 0;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public String next()
		{
			final char openBrace = '(';
			final char closeBrace = ')';
			final char com = ',';
			StringBuilder token = new StringBuilder();
			if(pos >= input.length())
			{
				return previousToken = null;
			}
			char ch = input.charAt(pos);
			while(Character.isWhitespace(ch) && pos < input.length())
			{
				ch = input.charAt(++pos);
			}
			if(Character.isDigit(ch))
			{
				while((Character.isDigit(ch) || ch == DEC_SEPARATOR || ch == 'e' || ch == 'E' || (ch == MINUS_SIGN && token.length() > 0 && ('e' == token.charAt(token.length() - 1) || 'E' == token.charAt(token.length() - 1))) || (ch == '+' && token.length() > 0 && ('e' == token.charAt(token.length() - 1) || 'E' == token.charAt(token.length() - 1)))) && (pos < input.length()))
				{
					token.append(input.charAt(pos++));
					ch = pos == input.length() ? 0 : input.charAt(pos);
				}
			}
			else if(ch == MINUS_SIGN && Character.isDigit(peekNextChar()) && (BRACE.equals(previousToken) || COMMA.equals(previousToken) || previousToken == null || operators.containsKey(previousToken)))
			{
				token.append(MINUS_SIGN);
				pos++;
				token.append(next());
			}
			else if(Character.isLetter(ch) || (ch == '_'))
			{
				while((Character.isLetter(ch) || Character.isDigit(ch) || (ch == '_')) && (pos < input.length()))
				{
					token.append(input.charAt(pos++));
					ch = pos == input.length() ? 0 : input.charAt(pos);
				}
			}

			else if(ch == openBrace || ch == closeBrace || ch == com)
			{
				token.append(ch);
				pos++;
			}
			else
			{
				while(!Character.isLetter(ch) && !Character.isDigit(ch) && ch != '_' && !Character.isWhitespace(ch) && ch != openBrace && ch != closeBrace && ch != com && (pos < input.length()))
				{
					token.append(input.charAt(pos));
					pos++;
					ch = pos == input.length() ? 0 : input.charAt(pos);
					if(ch == MINUS_SIGN)
					{
						break;
					}
				}
				if(!operators.containsKey(token.toString()))
				{
					throw new ExpressionException("Unknown operator '" + token + "' at position " + (pos - token.length() + 1));
				}
			}
			return previousToken = token.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove()
		{
			throw new ExpressionException("remove() not supported");
		}

		/**
		 * Get the actual character position in the string.
		 * 
		 * @return The actual character position.
		 */
		public int getPos()
		{
			return pos;
		}
	}

	/**
	 * Creates a new expression instance from an expression string with a given
	 * default match context of {@link MathContext#DECIMAL32}.
	 * 
	 * @param expression
	 *            The expression. E.g. <code>"2.4*sin(3)/(2-4)"</code> or
	 *            <code>"sin(y)>0 & max(z, 3)>3"</code>
	 */
	public Expression(String expression)
	{
		this(expression, MathContext.DECIMAL32);
	}

	/**
	 * Creates a new expression instance from an expression string with a given
	 * default match context.
	 * 
	 * @param expression
	 *            The expression. E.g. <code>"2.4*sin(3)/(2-4)"</code> or
	 *            <code>"sin(y)>0 & max(z, 3)>3"</code>
	 * @param defaultMathContext
	 *            The {@link MathContext} to use by default.
	 */
	public Expression(String expression, MathContext defaultMathContext)
	{
		final int val1 = 20;
		final int val2 = 30;
		final int val3 = 40;
		final int var4 = 4;
		final int var5 = 2;
		final int var6 = 10;
		final int var7 = 7;
		final int var8 = 3;
		final String eq = "=";
		final String neq = "!=";
		this.mc = defaultMathContext;
		this.expression = expression;
		addOperator(new Operator("+", val1, true)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.add(v2, mc);
			}
		});
		addOperator(new Operator("-", val1, true)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.subtract(v2, mc);
			}
		});
		addOperator(new Operator("*", val2, true)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.multiply(v2, mc);
			}
		});
		addOperator(new Operator("/", val2, true)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.divide(v2, mc);
			}
		});
		addOperator(new Operator("%", val2, true)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.remainder(v2, mc);
			}
		});
		addOperator(new Operator("^", val3, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				/*- 
				 * Thanks to Gene Marin:
				 * http://stackoverflow.com/questions/3579779/how-to-do-a-fractional-power-on-bigdecimal-in-java
				 */
				int signOf2 = v2.signum();
				double dn1 = v1.doubleValue();
				v2 = v2.multiply(new BigDecimal(signOf2)); // n2 is now positive
				BigDecimal remainderOf2 = v2.remainder(BigDecimal.ONE);
				BigDecimal n2IntPart = v2.subtract(remainderOf2);
				BigDecimal intPow = v1.pow(n2IntPart.intValueExact(), mc);
				BigDecimal doublePow = new BigDecimal(Math.pow(dn1, remainderOf2.doubleValue()));

				BigDecimal result = intPow.multiply(doublePow, mc);
				if(signOf2 == -1)
				{
					result = BigDecimal.ONE.divide(result, mc.getPrecision(), RoundingMode.HALF_UP);
				}
				return result;
			}
		});

		addOperator(new Operator("&&", var4, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				boolean b1 = !v1.equals(BigDecimal.ZERO);
				boolean b2 = !v2.equals(BigDecimal.ZERO);
				return b1 && b2 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		addOperator(new Operator("||", var5, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				boolean b1 = !v1.equals(BigDecimal.ZERO);
				boolean b2 = !v2.equals(BigDecimal.ZERO);
				return b1 || b2 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		addOperator(new Operator(">", var6, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.compareTo(v2) == 1 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		addOperator(new Operator(">=", var6, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.compareTo(v2) >= 0 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		addOperator(new Operator("<", var6, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.compareTo(v2) == -1 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		addOperator(new Operator("<=", var6, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.compareTo(v2) <= 0 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		addOperator(new Operator(eq, var7, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.compareTo(v2) == 0 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});
		addOperator(new Operator("==", var7, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return operators.get(eq).eval(v1, v2);
			}
		});

		addOperator(new Operator(neq, var7, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return v1.compareTo(v2) != 0 ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});
		addOperator(new Operator("<>", var7, false)
		{
			@Override
			public BigDecimal eval(BigDecimal v1, BigDecimal v2)
			{
				return operators.get(neq).eval(v1, v2);
			}
		});

		addFunction(new Function("NOT", 1, "Boolean negation, 1 (means true) if the expression is not zero")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				boolean zero = parameters.get(0).compareTo(BigDecimal.ZERO) == 0;
				return zero ? BigDecimal.ONE : BigDecimal.ZERO;
			}
		});

		addFunction(new Function("IF", var8, "Returns one value if the condition evaluates to true or the other if it evaluates to false")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				boolean isTrue = !parameters.get(0).equals(BigDecimal.ZERO);
				return isTrue ? parameters.get(1) : parameters.get(2);
			}
		});

		addFunction(new Function("RANDOM", 0, "Produces a random number between 0 and 1")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.random();
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("SIN", 1, "Returns the trigonometric sine of an angle (in degrees)")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.sin(Math.toRadians(parameters.get(0).doubleValue()));
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("COS", 1, "Returns the trigonometric cosine of an angle (in degrees)")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.cos(Math.toRadians(parameters.get(0).doubleValue()));
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("TAN", 1, "Returns the trigonometric tangens of an angle (in degrees)")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.tan(Math.toRadians(parameters.get(0).doubleValue()));
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("ASIN", 1, "Returns the angle of asin (in degrees)")
		{ // added by av
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.toDegrees(Math.asin(parameters.get(0).doubleValue()));
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("ACOS", 1, "Returns the angle of acos (in degrees)")
		{ // added by av
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double value = Math.toDegrees(Math.acos(parameters.get(0).doubleValue()));
				return new BigDecimal(value, mc);
			}
		});
		addFunction(new Function("ATAN", 1, "Returns the angle of atan (in degrees)")
		{ // added by av
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double value = Math.toDegrees(Math.atan(parameters.get(0).doubleValue()));
				return new BigDecimal(value, mc);
			}
		});
		addFunction(new Function("SINH", 1, "Returns the hyperbolic sine of a value")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.sinh(parameters.get(0).doubleValue());
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("COSH", 1, "Returns the hyperbolic cosine of a value")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.cosh(parameters.get(0).doubleValue());
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("TANH", 1, "Returns the hyperbolic tangens of a value")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.tanh(parameters.get(0).doubleValue());
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("RAD", 1, "Converts an angle measured in degrees to an approximately equivalent angle measured in radians")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double value = Math.toRadians(parameters.get(0).doubleValue());
				return new BigDecimal(value, mc);
			}
		});
		addFunction(new Function("DEG", 1, "Converts an angle measured in radians to an approximately equivalent angle measured in degrees")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double value = Math.toDegrees(parameters.get(0).doubleValue());
				return new BigDecimal(value, mc);
			}
		});
		addFunction(new Function("MAX", -1, "MAX(e1,e2, ...) - Returns the biggest of the given expressions")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				if(parameters.size() == 0)
				{
					throw new ExpressionException("MAX requires at least one parameter");
				}
				BigDecimal max = null;
				for(BigDecimal parameter : parameters)
				{
					if(max == null || parameter.compareTo(max) > 0)
					{
						max = parameter;
					}
				}
				return max;
			}
		});
		addFunction(new Function("MIN", -1, "MIN(e1,e2, ...) - Returns the smallest of the given expressions")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				if(parameters.size() == 0)
				{
					throw new ExpressionException("MIN requires at least one parameter");
				}
				BigDecimal min = null;
				for(BigDecimal parameter : parameters)
				{
					if(min == null || parameter.compareTo(min) < 0)
					{
						min = parameter;
					}
				}
				return min;
			}
		});
		addFunction(new Function("ABS", 1, "Returns the absolute (non-negative) value of the expression. ABS(-4) and ABS(4) results in 4.")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				return parameters.get(0).abs(mc);
			}
		});
		addFunction(new Function("LOG", 1, "Returns the natural logarithm (base e) of an expression")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double dval = Math.log(parameters.get(0).doubleValue());
				return new BigDecimal(dval, mc);
			}
		});
		addFunction(new Function("LOG10", 1, "Returns the common logarithm (base 10) of an expression")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				double value = Math.log10(parameters.get(0).doubleValue());
				return new BigDecimal(value, mc);
			}
		});
		addFunction(new Function("ROUND", 2, "Rounds a value to a certain number of digits, uses the current rounding mode")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				BigDecimal toRound = parameters.get(0);
				int precision = parameters.get(1).intValue();
				return toRound.setScale(precision, mc.getRoundingMode());
			}
		});
		addFunction(new Function("FLOOR", 1, "Rounds the value down to the nearest integer")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				BigDecimal toRound = parameters.get(0);
				return toRound.setScale(0, RoundingMode.FLOOR);
			}
		});
		addFunction(new Function("CEILING", 1, "Rounds the value up to the nearest integer")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				BigDecimal toRound = parameters.get(0);
				return toRound.setScale(0, RoundingMode.CEILING);
			}
		});
		addFunction(new Function("SQRT", 1, "Returns the square root of an expression")
		{
			@Override
			public BigDecimal eval(List<BigDecimal> parameters)
			{
				/*
				 * From The Java Programmers Guide To numerical Computing
				 * (Ronald Mak, 2003)
				 */
				BigDecimal deci = parameters.get(0);
				if(deci.compareTo(BigDecimal.ZERO) == 0)
				{
					return new BigDecimal(0);
				}
				if(deci.signum() < 0)
				{
					throw new ExpressionException("Argument to SQRT() function must not be negative");
				}
				BigInteger var = deci.movePointRight(mc.getPrecision() << 1).toBigInteger();

				int bits = (var.bitLength() + 1) >> 1;
				BigInteger ix = var.shiftRight(bits);
				BigInteger ixPrev;

				do
				{
					ixPrev = ix;
					ix = ix.add(var.divide(ix)).shiftRight(1);
					// Give other threads a chance to work;
					Thread.yield();
				} while (ix.compareTo(ixPrev) != 0);

				return new BigDecimal(ix, mc.getPrecision());
			}
		});

		variables.put("PI", PI);
		variables.put("TRUE", BigDecimal.ONE);
		variables.put("FALSE", BigDecimal.ZERO);
	}

	/**
	 * Is the string a number?
	 * 
	 * @param st
	 *            The string.
	 * @return <code>true</code>, if the input string is a number.
	 */
	private boolean isNumber(String st)
	{
		if(st.charAt(0) == MINUS_SIGN && st.length() == 1)
		{
			return false;
		}
		if(st.charAt(0) == '+' && st.length() == 1)
		{
			return false;
		}
		if(st.charAt(0) == 'e' || st.charAt(0) == 'E')
		{
			return false;
		}
		for(char ch : st.toCharArray())
		{
			if(!Character.isDigit(ch) && ch != MINUS_SIGN && ch != DEC_SEPARATOR && ch != 'e' && ch != 'E' && ch != '+')
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Implementation of the <i>Shunting Yard</i> algorithm to transform an
	 * infix expression to a RPN expression.
	 * 
	 * @param expression
	 *            The input expression in infx.
	 * @return A RPN representation of the expression, with each token as a list
	 *         member.
	 */
	private List<String> shuntingYard(String expression)
	{
		List<String> outputQueue = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();

		Tokenizer tokenizer = new Tokenizer(expression);
		String lastFunction = null;
		String previousToken = null;
		while(tokenizer.hasNext())
		{
			String token = tokenizer.next();
			if(isNumber(token))
			{
				outputQueue.add(token);
			}
			else if(WORD_PATTERN.matcher(token).matches())
			{
				if(variables.containsKey(token))
				{
					outputQueue.add(token);
				}
				else if(functions.containsKey(token.toUpperCase(Locale.ROOT)))
				{
					stack.push(token);
					lastFunction = token;
				}
				else
				{
					throw new RuntimeException("Invalid variable/function name specified - " + token);
				}
				/*
				 * else if(Character.isLetter(token.charAt(0))) {
				 * stack.push(token); }
				 */
			}
			else if(COMMA.equals(token))
			{
				while(!stack.isEmpty() && !BRACE.equals(stack.peek()))
				{
					outputQueue.add(stack.pop());
				}
				if(stack.isEmpty())
				{
					throw new ExpressionException("Parse error for function '" + lastFunction + "'");
				}
			}
			else if(operators.containsKey(token))
			{
				Operator o1 = operators.get(token);
				String token2 = stack.isEmpty() ? null : stack.peek();
				while(operators.containsKey(token2) && ((o1.isLeftAssoc() && o1.getPrecedence() <= operators.get(token2).getPrecedence()) || (o1.getPrecedence() < operators.get(token2).getPrecedence())))
				{
					outputQueue.add(stack.pop());
					token2 = stack.isEmpty() ? null : stack.peek();
				}
				stack.push(token);
			}
			else if(BRACE.equals(token))
			{
				if(previousToken != null)
				{
					if(isNumber(previousToken))
					{
						throw new ExpressionException("Missing operator at character position " + tokenizer.getPos());
					}
					// if the ( is preceded by a valid function, then it
					// denotes the start of a parameter list
					if(functions.containsKey(previousToken.toUpperCase(Locale.ROOT)))
					{
						outputQueue.add(token);
					}
				}
				stack.push(token);
			}
			else if(CLOSE_BRACE.equals(token))
			{
				while(!stack.isEmpty() && !BRACE.equals(stack.peek()))
				{
					outputQueue.add(stack.pop());
				}
				if(stack.isEmpty())
				{
					throw new RuntimeException("Mismatched parentheses.");
				}
				stack.pop();
				if(!stack.isEmpty() && functions.containsKey(stack.peek().toUpperCase(Locale.ROOT)))
				{
					outputQueue.add(stack.pop());
				}
			}
			previousToken = token;
		}
		while(!stack.isEmpty())
		{
			String element = stack.pop();
			if(BRACE.equals(element) || CLOSE_BRACE.equals(element))
			{
				throw new RuntimeException("Mismatched parentheses");
			}
			if(!operators.containsKey(element))
			{
				throw new RuntimeException("Unknown operator or function: " + element);
			}
			outputQueue.add(element);
		}
		return outputQueue;
	}

	/**
	 * Evaluates the expression.
	 * 
	 * @return The result of the expression.
	 */
	public BigDecimal eval()
	{

		Stack<BigDecimal> stack = new Stack<BigDecimal>();
		if(rpn == null)
		{
			getRpn();
		}
		for(String token : rpn)
		{
			if(operators.containsKey(token))
			{
				BigDecimal v1 = stack.pop();
				BigDecimal v2 = stack.pop();
				stack.push(operators.get(token).eval(v2, v1));
			}
			else if(variables.containsKey(token))
			{
				stack.push(variables.get(token).round(mc));
			}
			else if(functions.containsKey(token.toUpperCase(Locale.ROOT)))
			{
				Function func = functions.get(token.toUpperCase(Locale.ROOT));
				ArrayList<BigDecimal> arr = new ArrayList<BigDecimal>(!func.numParamsVaries() ? func.getNumParams() : 0);
				// pop parameters off the stack until we hit the start of
				// this function's parameter list
				while(!stack.isEmpty() && stack.peek() != PARAMS_START)
				{
					arr.add(0, stack.pop());
				}
				if(stack.peek() == PARAMS_START)
				{
					stack.pop();
				}
				if(!func.numParamsVaries() && arr.size() != func.getNumParams())
				{
					throw new ExpressionException("Function " + token + " expected " + func.getNumParams() + " parameters, got " + arr.size());
				}
				BigDecimal result = func.eval(arr);
				stack.push(result);
			}
			else if(BRACE.equals(token))
			{
				stack.push(PARAMS_START);
			}
			else
			{
				stack.push(new BigDecimal(token, mc));
			}
		}
		return stack.pop().stripTrailingZeros();
	}

	/**
	 * Sets the precision for expression evaluation.
	 * 
	 * @param precision
	 *            The new precision.
	 * 
	 * @return The expression, allows to chain methods.
	 */
	public Expression setPrecision(int precision)
	{
		this.mc = new MathContext(precision);
		return this;
	}

	/**
	 * Sets the rounding mode for expression evaluation.
	 * 
	 * @param roundingMode
	 *            The new rounding mode.
	 * @return The expression, allows to chain methods.
	 */
	public Expression setRoundingMode(RoundingMode roundingMode)
	{
		this.mc = new MathContext(mc.getPrecision(), roundingMode);
		return this;
	}

	/**
	 * Adds an operator to the list of supported operators.
	 * 
	 * @param operator
	 *            The operator to add.
	 * @return The previous operator with that name, or <code>null</code> if
	 *         there was none.
	 */
	public Operator addOperator(Operator operator)
	{
		return operators.put(operator.getOper(), operator);
	}

	/**
	 * Adds a function to the list of supported functions.
	 * 
	 * @param function
	 *            The function to add.
	 * @return The previous operator with that name, or <code>null</code> if
	 *         there was none.
	 */
	public Function addFunction(Function function)
	{
		return functions.put(function.getName(), function);
	}

	/**
	 * Gets the all defined functions with name and implementation.
	 *
	 * @return the all defined functions with name and implementation
	 */
	public Collection<Function> getFunctions()
	{
		return functions.values();
	}

	/**
	 * Sets a variable value.
	 * 
	 * @param variable
	 *            The variable name.
	 * @param value
	 *            The variable value.
	 * @return The expression, allows to chain methods.
	 */
	public Expression setVariable(String variable, BigDecimal value)
	{
		variables.put(variable, value);
		return this;
	}

	/**
	 * Sets a variable value.
	 * 
	 * @param variable
	 *            The variable to set.
	 * @param value
	 *            The variable value.
	 * @return The expression, allows to chain methods.
	 */
	public Expression setVariable(String variable, String value)
	{
		String str = "\\b";
		if(isNumber(value))
		{
			variables.put(variable, new BigDecimal(value));
		}
		else
		{
			expression = expression.replaceAll(str + variable + str, BRACE + value + CLOSE_BRACE);
			rpn = null;
		}
		return this;
	}

	/**
	 * Sets a variable value.
	 * 
	 * @param variable
	 *            The variable to set.
	 * @param value
	 *            The variable value.
	 * @return The expression, allows to chain methods.
	 */
	public Expression with(String variable, BigDecimal value)
	{
		return setVariable(variable, value);
	}

	/**
	 * Sets a variable value.
	 * 
	 * @param variable
	 *            The variable to set.
	 * @param value
	 *            The variable value.
	 * @return The expression, allows to chain methods.
	 */
	public Expression with(String variable, String value)
	{
		return setVariable(variable, value);
	}

	/**
	 * Sets a variable value.
	 * 
	 * @param variable
	 *            The variable to set.
	 * @param value
	 *            The variable value.
	 * @return The expression, allows to chain methods.
	 */
	public Expression and(String variable, String value)
	{
		return setVariable(variable, value);
	}

	/**
	 * Sets a variable value.
	 * 
	 * @param variable
	 *            The variable to set.
	 * @param value
	 *            The variable value.
	 * @return The expression, allows to chain methods.
	 */
	public Expression and(String variable, BigDecimal value)
	{
		return setVariable(variable, value);
	}

	/**
	 * Get an iterator for this expression, allows iterating over an expression
	 * token by token.
	 * 
	 * @return A new iterator instance for this expression.
	 */
	public Iterator<String> getExpressionTokenizer()
	{
		return new Tokenizer(this.expression);
	}

	/**
	 * Cached access to the RPN notation of this expression, ensures only one
	 * calculation of the RPN per expression instance. If no cached instance
	 * exists, a new one will be created and put to the cache.
	 * 
	 * @return The cached RPN instance.
	 */
	private List<String> getRpn() throws RuntimeException
	{
		if(rpn == null)
		{
			rpn = shuntingYard(this.expression);

			validate(rpn);
		}
		return rpn;
	}

	/**
	 * Validate expression.
	 *
	 * @throws RuntimeException
	 *             the runtime exception
	 */
	public void validateExpression() throws RuntimeException
	{
		getRpn();
	}

	/**
	 * Check that the expression have enough numbers and variables to fit the
	 * requirements of the operators and functions, also check for only 1 result
	 * stored at the end of the evaluation.
	 *
	 */
	private void validate(List<String> rpn)
	{
		/*- 
		* Thanks to Norman Ramsey:
		* http://http://stackoverflow.com/questions/789847/postfix-notation-validation
		*/
		int counter = 0;
		Stack<Integer> params = new Stack<Integer>();
		for(String token : rpn)
		{
			if(BRACE.equals(token))
			{
				// is this a nested function call?
				if(!params.isEmpty())
				{
					// increment the current function's param count
					// (the return of the nested function call
					// will be a parameter for the current function)
					params.set(params.size() - 1, params.peek() + 1);
				}
				// start a new parameter count
				params.push(0);
			}
			else if(!params.isEmpty())
			{
				if(functions.containsKey(token.toUpperCase(Locale.ROOT)))
				{
					// remove the parameters and the ( from the counter
					counter -= params.pop() + 1;
				}
				else
				{
					// increment the current function's param count
					params.set(params.size() - 1, params.peek() + 1);
				}
			}
			else if(operators.containsKey(token))
			{
				// we only have binary operators
				counter -= 2;
			}
			if(counter < 0)
			{
				throw new ExpressionException("Too many operators or functions at: " + token);
			}
			counter++;
		}
		if(counter > 1)
		{
			throw new ExpressionException("Too many numbers or variables");
		}
		else if(counter < 1)
		{
			throw new ExpressionException("Empty expression");
		}
	}

	/**
	 * Get a string representation of the RPN (Reverse Polish Notation) for this
	 * expression.
	 * 
	 * @return A string with the RPN representation for this expression.
	 */
	public String toRpn()
	{
		StringBuilder result = new StringBuilder();
		for(String st : getRpn())
		{
			if(result.length() != 0)
			{
				result.append(" ");
			}
			result.append(st);
		}
		return result.toString();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		final int val = 10;
		Expression exp = new Expression("(storageInMB - 50) * val");
		try
		{
			exp.setVariable("storageInMB", new BigDecimal(val));
			String st = exp.eval().toString();
			System.out.println(st);
		} catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
