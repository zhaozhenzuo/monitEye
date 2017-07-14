//package com.z.monit.bootstrap.core;
//
//import java.math.BigDecimal;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class BuyServiceImpl implements BuyService {
//
//	private static final Logger logger = LoggerFactory.getLogger(BuyServiceImpl.class);
//
//	public boolean buy(String userId, BigDecimal amount, String productId) {
//
//		/**
//		 * 1.monit begin
//		 */
//		InvokeInfo invokeInfo = null;
//		long oldTime = System.currentTimeMillis();
//		try {
//			InvokeOperInf invokeOper = InvokeOperFactory.getInvokeOper();
//			invokeInfo = invokeOper.getInvokerInfoCurThreadForAcceptor(null);
//
//			logger.info("=monit begin,role:accept,invokeUniqueKey:" + invokeInfo.getTransactionId() + ",parentId:"
//					+ invokeInfo.getParentId() + ",currentId:" + invokeInfo.getCurrentSpanId());
//
//		} catch (Exception e) {
//			logger.error("monit begin err", e);
//		}
//
//		/**
//		 * 2.do biz
//		 */
//		System.out.println("do biz");
//
//		/**
//		 * 3.monit end
//		 */
//		try {
//			if (invokeInfo != null) {
//				long cost = System.currentTimeMillis() - oldTime;
//				logger.info("=monit end,role:accept,invokeUniqueKey:" + invokeInfo.getTransactionId() + ",parentId:"
//						+ invokeInfo.getParentId() + ",currentId:" + invokeInfo.getCurrentSpanId()+ ",cost:" + cost);
//			}
//		} catch (Exception e) {
//			logger.error("monit end err", e);
//		}
//
//		return false;
//	}
//
//}
