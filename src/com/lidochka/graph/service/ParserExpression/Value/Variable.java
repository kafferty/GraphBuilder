package com.lidochka.graph.service.ParserExpression.Value;

import com.lidochka.graph.service.ParserExpression.Token;


public class Variable extends Token {
    public Variable(){
        this.token='x';
    }
    public TokenNumber getNumber (double x){
        return new TokenNumber(x);
    }
}
