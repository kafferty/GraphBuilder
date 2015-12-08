package com.lidochka.graph.service.ParserExpression.Operation;

import com.lidochka.graph.service.ParserExpression.Token;
import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public abstract class AOperation extends Token{
    protected int priority, countArgs;
    public abstract TokenNumber invoke(TokenNumber[] params);
    public int getPriority(){return priority;}
    public int getCountArgs(){return countArgs;}

}
