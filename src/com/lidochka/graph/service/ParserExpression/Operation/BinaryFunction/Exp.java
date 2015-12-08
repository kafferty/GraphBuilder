package com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction;

import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;

public class Exp extends ABinaryFunction {
    @Override
    public TokenNumber invoke(TokenNumber[] params) {
        return new TokenNumber(Math.exp(params[0].getValue()));
    }
}