package com.lidochka.graph.service.ParserExpression.Operation;

import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public class OpeningBracket extends AOperation {
    public OpeningBracket(){
        this.token = '(';
        this.priority = Integer.MAX_VALUE;
        this.countArgs=0;
    }
    @Override
    public TokenNumber invoke(TokenNumber[] params) {
        return null;
    }
}
