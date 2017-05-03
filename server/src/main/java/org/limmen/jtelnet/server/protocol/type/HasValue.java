package org.limmen.jtelnet.server.protocol.type;

public interface HasValue<T> {
	
	int getValue();	
	
	T parseValue(int value);
}
