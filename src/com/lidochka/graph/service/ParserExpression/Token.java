package com.lidochka.graph.service.ParserExpression;

import com.lidochka.graph.service.ParserExpression.Operation.ClosingBraket;
import com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction.*;
import com.lidochka.graph.service.ParserExpression.Operation.OpeningBracket;
import com.lidochka.graph.service.ParserExpression.Operation.Operator;
import com.lidochka.graph.service.ParserExpression.Value.*;
import com.lidochka.graph.service.ParserExpression.Value.TokenNumber;


public class Token {
    protected char token;
    public static boolean matchToken (char token){
        switch (token){
            case 'x':
            case '(':
            case ')':
            case '*':
            case '-':
            case '/':
            case '+':
            case '^':
            case '%':
                return true;
            default:
                return false;
        }
    }
    public static boolean matchToken (String token){
        switch (token){
            case "sin":
            case "cos":
            case "tan":
            case "tg":
            case "cot":
            case "log":
            case "exp":
            case "sqrt":
            case "abs":
                return true;
            default: return false;
        }
    }
    public char getTokenChar(){
        return token;
    }
}
