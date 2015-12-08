package com.lidochka.graph.service.ParserExpression;

import java.util.ArrayList;


public class Parser {
    public static ArrayList<Token> parseTokens(String expression){
        ArrayList<Token> tokens = new ArrayList<>();
        TokenFactory tokenFactory = new TokenFactory();
        tokens.add(tokenFactory.getToken('('));
        expression+=')';
        StringBuilder word = new StringBuilder();
        char bufChar;
        for (int i=0; i<expression.length(); i++){
            bufChar = expression.charAt(i);
            if(Token.matchToken(bufChar) || bufChar == ' ' ){
                if(word.length()!=0){
                    tokens.add(tokenFactory.getToken(word.toString()));
                    word = new StringBuilder();
                }
                if(bufChar == ' ')
                    continue;
                tokens.add(tokenFactory.getToken(bufChar));
            }
            else
                word.append(expression.charAt(i));
        }
        return tokens;
    }
}
