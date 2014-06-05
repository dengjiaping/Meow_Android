package com.widen.http.parsers;

import org.xml.sax.helpers.DefaultHandler;

public abstract class AbsHandler<T> extends DefaultHandler {

	public abstract T getParseredData();
}
