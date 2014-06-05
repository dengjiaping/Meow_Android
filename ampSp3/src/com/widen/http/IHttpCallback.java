package com.widen.http;

import org.json.JSONObject;

public interface IHttpCallback {

	void onGetData(Object data);

	void onError(Object reason);

}
