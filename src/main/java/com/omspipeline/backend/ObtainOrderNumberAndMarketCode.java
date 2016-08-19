package com.omspipeline.backend;

public final class ObtainOrderNumberAndMarketCode {

	
	private static String orderNumber;
	private static int marketCodeID;
	
    private ObtainOrderNumberAndMarketCode() {
    }

    public static String getOrderNumber() {
		return orderNumber;
	}

	public static void setOrderNumber(String orderNumber) {
		ObtainOrderNumberAndMarketCode.orderNumber = orderNumber;
	}
    
    public static int getMarketCode() {
		return marketCodeID;
	}

	public static void setMarketCode(int marketCode) {
		ObtainOrderNumberAndMarketCode.marketCodeID = marketCode;
	}

}
