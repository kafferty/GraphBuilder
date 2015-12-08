package com.lidochka.graph.service;

import java.awt.*;
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

public class FunctionParserImpl implements FunctionParser, AppEventListener {
	
	@Override
	public FunctionData parseFunction(final String function, final Double minX, final Double maxX, final int numOfPoints) {
		String expression=function.toLowerCase();
		ArrayList<Token>  tokens = Parser.parseTokens(expression);
		List<FunctionPoint> points = new LinkedList<>();
		double step = (maxX-minX)/numOfPoints;
		double x=minX;
		Stack<TokenNumber> numbers = new Stack<>();
		Stack<AOperation> operations = new Stack<>();
		for(int i=0; i<=numOfPoints; i++,x+=step){
			for (Token token : tokens){
				switch (token.getTokenChar()){
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
						do{
							TokenNumber[] params = new TokenNumber[operations.peek().getCountArgs()];
							for(int j=0; j<params.length;j++)
								params[j] = numbers.pop();
							if (params.length>0) {
								numbers.add(operations.peek().invoke(params));
								if(Double.isNaN(numbers.peek().getValue()))
									throw new IllegalArgumentException();
							}
						}while (operations.pop().getTokenChar() != '(');

						break;
					default:

						while (operations.peek().getPriority() <= ((Operator)token).getPriority()){
							TokenNumber[] params = new TokenNumber[operations.peek().getCountArgs()];
							for(int j=0; j<params.length;j++)
								params[j] = numbers.pop();
							if (params.length>0)
							numbers.add(operations.pop().invoke(params));
						}
						operations.add((Operator) token);
					}
				}
			points.add(new FunctionPoint(x, numbers.pop().getValue()));
		}
		return new FunctionData(expression, points);
	}

	@Override
	public void onApplicationEvent(Object event) {
		

		if (event instanceof FunctionParamsEvent) {		
			FunctionParamsEvent fpEvent = (FunctionParamsEvent)event;
			try {
				FunctionData functionData = parseFunction(fpEvent.getNotation(), fpEvent.getMinX(), fpEvent.getMaxX(), fpEvent.getNumsOfPoints());
				EventsBus.publishEvent(new FunctionDataEvent(functionData));
			}
			catch (IllegalArgumentException e){
				EventsBus.publishEvent(new ErrorEvent(1));
				return;
			}

		}
		
	}

}
