package com.z.monit.bootstrap.core;

import java.math.BigDecimal;

public interface BuyService {

	public boolean buy(String userId, BigDecimal amount, String productId);

}
