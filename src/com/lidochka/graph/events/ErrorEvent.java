package com.lidochka.graph.events;

public class ErrorEvent {
    int type;
    public ErrorEvent(int type){
        this.type=type;
    }
    public int getType(){
        return type;
    }
}
