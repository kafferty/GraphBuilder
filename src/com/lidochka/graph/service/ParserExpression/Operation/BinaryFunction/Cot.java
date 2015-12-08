package com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction;

import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public class Cot extends ABinaryFunction {
    @Override
    public TokenNumber invoke(TokenNumber[] params) {
        return new TokenNumber(1.0/Math.tan(params[0].getValue()));
    }
}
