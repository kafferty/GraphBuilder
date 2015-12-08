package com.lidochka.graph.service.ParserExpression.Operation;

import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public class ClosingBraket extends AOperation {
    public ClosingBraket(){
        this.priority = Integer.MAX_VALUE;
        this.countArgs=0;
        this.token=')';
    }
    @Override
    public TokenNumber invoke(TokenNumber[] params) {
        return null;
    }
}
