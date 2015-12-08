package com.lidochka.graph.service.ParserExpression;

import com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction.*;
import com.lidochka.graph.service.ParserExpression.Operation.ClosingBraket;
import com.lidochka.graph.service.ParserExpression.Operation.OpeningBracket;
import com.lidochka.graph.service.ParserExpression.Operation.Operator;
import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;
import com.lidochka.graph.service.ParserExpression.Value.Variable;


public class TokenFactory {
    public Token getToken(char token){
        switch (token){
            case 'x':
                return new Variable();
            case '(':
                return new OpeningBracket();
            case ')':
                return new ClosingBraket();
            default:
                return new Operator(token);
        }
    }
    public Token getToken(String token){
        switch (token){
            case "sin":
                return new Sin();
            case "cos":
                return new Cos();
            case "tg":
            case "tan":
                return new Tan();
            case "cot":
                return new Cot();
            case "log":
                return new Log();
            case "e":
                return new Exp();
            case "sqrt":
                return new Sqrt();
            case "abs":
                return new Abs();
            default:
                return new TokenNumber(token);
        }
    }
}
