package com.lidochka.graph.service.ParserExpression.Operation;

import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public class Operator extends AOperation {
    public Operator(char token){
        this.token = token;
        this.countArgs = 2;
        switch (token){
            case '*' :
                this.priority=1; break;
            case '-' :
                this.priority=2; break;
            case '+' :
                this.priority=2; break;
            case '/' :
                this.priority=1; break;
            case '%' :
                this.priority=1; break;
            case '^' :
                this.priority=0; break;
        }
    }
    @Override
    public TokenNumber invoke(TokenNumber[] params) {
        switch (token){
            case '*' :
                return new TokenNumber(params[1].getValue() * params[0].getValue());
            case '-' :
                return new TokenNumber(params[1].getValue() - params[0].getValue());
            case '+' :
                return new TokenNumber(params[1].getValue() + params[0].getValue());
            case '/' :
                return new TokenNumber(params[1].getValue() / params[0].getValue());
            case '%' :
                return new TokenNumber(params[1].getValue() % params[0].getValue());
            case '^' :
                return new TokenNumber(Math.pow(params[1].getValue(), params[0].getValue()));
        }
        return null;
    }
}
