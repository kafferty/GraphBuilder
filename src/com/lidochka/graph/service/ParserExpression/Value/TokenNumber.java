package com.lidochka.graph.service.ParserExpression.Value;
import com.lidochka.graph.service.ParserExpression.Token;


public class TokenNumber extends Token {
    double value;
    {this.token =0;}
    public TokenNumber(String value){
        this.value = Double.parseDouble(value);

    }
    public TokenNumber(double value){
        this.value = value;
    }
    public double getValue(){
        return value;
    }
}
