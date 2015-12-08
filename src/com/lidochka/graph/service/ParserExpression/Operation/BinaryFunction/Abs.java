package com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction;

import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public class Abs extends ABinaryFunction {
    @Override
    public TokenNumber invoke(TokenNumber[] params) {
        return new TokenNumber(Math.abs(params[0].getValue()));
    }}
