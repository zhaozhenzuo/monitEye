package com.z.monit.bootstrap.core;

import java.math.BigDecimal;

public class InvokeTest {

	public static void main(String[] args) {

		BuyService buyService=new BuyServiceImpl();
		
		String userId="1";
		BigDecimal amount=new BigDecimal("100");
		String productId="tb";
		buyService.buy(userId, amount, productId);

	}

}
