package com.widen.http.parsers;

import org.xml.sax.SAXException;

public class DescribeParser extends AbsHandler<String> {
	private StringBuilder sb = new StringBuilder();
	public String desc = "";

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if ("desc".equals(localName)) {
			desc = sb.toString().trim();
		}
		sb.setLength(0);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		sb.append(ch, start, length);
	}

	@Override
	public String getParseredData() {
		return desc;
	}

}
