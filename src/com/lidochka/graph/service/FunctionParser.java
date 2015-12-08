package com.lidochka.graph.service;

import com.lidochka.graph.model.FunctionData;

public interface FunctionParser {
	
	FunctionData parseFunction(String function, Double minX, Double maxX, int numOfPoints );
	
}
