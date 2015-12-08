package com.lidochka.graph.service.ParserExpression.Operation.BinaryFunction;

import com.lidochka.graph.service.ParserExpression.Operation.AOperation;

public abstract class ABinaryFunction extends AOperation{
    public ABinaryFunction(){
        this.priority = Integer.MAX_VALUE;
        this.countArgs=1;
        this.token='(';
    }
}
