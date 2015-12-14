package com.lidochka.graph.events;

public class ErrorEvent {
    public static final int PARAMS_ERROR = 1;
    public static final int FUNC_ERROR = 2;
    int type;
    public ErrorEvent(int type){
        this.type=type;
    }
    public int getType(){
        return type;
    }
}
