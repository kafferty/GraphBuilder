package com.lidochka.graph.events;

import com.lidochka.graph.model.FunctionData;

public class FunctionDataEvent {

	private FunctionData functionData;
	
	public FunctionDataEvent(FunctionData functionData) {
		this.functionData = functionData;
	}
	
	public FunctionData getFunctionData() {
		return functionData;
	}	
	
}
