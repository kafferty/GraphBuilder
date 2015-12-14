package com.lidochka.graph.service;

import java.util.*;
import java.util.List;

import com.lidochka.graph.events.ErrorEvent;
import com.lidochka.graph.events.FunctionDataEvent;
import com.lidochka.graph.events.FunctionParamsEvent;
import com.lidochka.graph.model.FunctionData;
import com.lidochka.graph.model.FunctionPoint;
import com.lidochka.graph.service.ParserExpression.Operation.AOperation;
import com.lidochka.graph.service.ParserExpression.Operation.Operator;
import com.lidochka.graph.service.ParserExpression.Parser;
import com.lidochka.graph.service.ParserExpression.Token;
import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;

public class FunctionParserImpl implements AppEventListener {
	public FunctionData parseFunction(final String[] functions, final Double minX, final Double maxX, final double minY, final double maxY, final int numOfPoints) {
		List<FunctionPoint>[] listPoints= new ArrayList[functions.length];
		for(int i=0; i<functions.length; i++) {
			listPoints[i] = new ArrayList<>();
			String expression = functions[i].toLowerCase();
			ArrayList<Token> tokens = Parser.parseTokens(expression);
			double step = (maxX - minX) / numOfPoints;
			double x = minX;
			Stack<TokenNumber> numbers = new Stack<>();
			Stack<AOperation> operations = new Stack<>();
			try {
				for (int j = 0; j <= numOfPoints; j++, x += step) {
					for (Token token : tokens) {
						switch (token.getTokenChar()) {
							case 'x':
								numbers.add(new TokenNumber(x));
								break;
							case 0:
								numbers.add((TokenNumber) token);
								break;
							case '(':
								operations.add((AOperation) token);
								break;
							case ')':
								do {
									TokenNumber[] params = new TokenNumber[operations.peek().getCountArgs()];
									for (int k = 0; k < params.length; k++)
										params[k] = numbers.pop();
									if (params.length > 0) {
										numbers.add(operations.peek().invoke(params));
										if (Double.isNaN(numbers.peek().getValue()))
											throw new IllegalArgumentException();
									}
								} while (operations.pop().getTokenChar() != '(');

								break;
							default:

								while (operations.peek().getPriority() <= ((Operator) token).getPriority()) {
									TokenNumber[] params = new TokenNumber[operations.peek().getCountArgs()];
									for (int k = 0; k < params.length; k++)
										params[k] = numbers.pop();
									if (params.length > 0)
										numbers.add(operations.pop().invoke(params));
								}
								operations.add((Operator) token);
						}
					}

					if (numbers.peek().getValue() >= minY && numbers.peek().getValue() <= maxY)
						listPoints[i].add(new FunctionPoint(x, numbers.pop().getValue()));
				}
			}
			catch (Exception e){
				EventsBus.publishEvent(ErrorEvent.FUNC_ERROR);
			}
		}
		return new FunctionData(functions, listPoints, minX, maxX, minY, maxY);
	}

	@Override
	public void onApplicationEvent(Object event) {
		

		if (event instanceof FunctionParamsEvent) {		
			FunctionParamsEvent fpEvent = (FunctionParamsEvent)event;
			try {
				FunctionData functionData = parseFunction(fpEvent.getNotations(), fpEvent.getMinX(), fpEvent.getMaxX(),fpEvent.getMinY(), fpEvent.getMaxY(), fpEvent.getNumsOfPoints());
				EventsBus.publishEvent(new FunctionDataEvent(functionData));
			}
			catch (IllegalArgumentException e){
				EventsBus.publishEvent(new ErrorEvent(ErrorEvent.FUNC_ERROR));
				return;
			}

		}
		
	}

}
