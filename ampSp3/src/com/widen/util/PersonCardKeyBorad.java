package com.widen.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

public class PersonCardKeyBorad extends Keyboard{
	
	
	private Key enterKey;

	public PersonCardKeyBorad(Context context, int xmlLayoutResId) {
		super(context, xmlLayoutResId);
		// TODO Auto-generated constructor stub
	}

	private PersonCardKeyBorad(Context context, int layoutTemplateResId,
			CharSequence characters, int columns, int horizontalPadding) {
		super(context, layoutTemplateResId, characters, columns, horizontalPadding);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Key createKeyFromXml(Resources res, Row parent, int x, int y,
			XmlResourceParser parser) {
		// TODO Auto-generated method stub
		Key key = new Key(res,parent,x,y,parser);
		  if (key.codes[0] == 10) {
	            enterKey = key;
	        }
	        

		return key;
	}
	
	
	
	

}
