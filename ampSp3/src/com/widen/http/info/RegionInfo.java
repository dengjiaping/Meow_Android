package com.widen.http.info;

import java.io.Serializable;

public class RegionInfo {
	public String id = "";
	public String name = "";
	public BaseListInfo<RegionChildInfo> regions = new BaseListInfo<RegionChildInfo>();
}
