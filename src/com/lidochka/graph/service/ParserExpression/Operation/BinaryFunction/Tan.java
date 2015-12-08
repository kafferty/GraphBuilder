package com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction;

import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public class Tan extends ABinaryFunction {
    @Override
    public TokenNumber invoke(TokenNumber[] params) {
        return new TokenNumber(Math.tan(params[0].getValue()));
    }
}
