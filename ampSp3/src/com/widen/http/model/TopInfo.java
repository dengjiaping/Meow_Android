package com.widen.http.model;

public class TopInfo {
	public BaseListInfo<BannerInfo> bannerInfos = new BaseListInfo<BannerInfo>(); 
	public ProductionsInfo2 topInfo2 = new ProductionsInfo2();
	public String code = "";
	
	public String AccruedEarnings = "";
	public String AppEearningSpeed = "";
	public String DefeatedPercent = "";
	public String EarningsAgain = "";
	
	public String EarningsAgainDuration = "";
	
	public Boolean HasOrders = false;
	public Boolean HasShown = false;
	public String InterestPerSecond = "";
	public String TimeForOneCNY = "";

}
