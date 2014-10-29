package com.widen.http;

public interface IHttpCallback {

	void onGetData(Object data);

	void onError(Object reason);

}
