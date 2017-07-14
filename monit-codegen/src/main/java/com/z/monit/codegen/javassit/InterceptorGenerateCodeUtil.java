package com.z.monit.codegen.javassit;

public class InterceptorGenerateCodeUtil {

	private static final String BEFORE_INTERCEPTOR_FORMAT = "%s.getInterceptor(\"%s\").before($0,$args);";
	
	//Object target, Object args, Object result, Throwable throwable
	private static final String AFTER_INTERCEPTOR_FORMAT = "%s.getInterceptor(\"%s\").after($0,$args,null,null);";

	public static void main(String[] args) {
		String res = generateAfterInterceptor("com.t.regsiter", "AbstactProvider", 1);
		System.out.println(res);
	}

	public static String generateBeforeInterceptor(final String interceptorRegisterClassName,
			final String interceptorName, int paramNums) {
		StringBuffer codeContent = new StringBuffer();

		codeContent.append(String.format(BEFORE_INTERCEPTOR_FORMAT, interceptorRegisterClassName, interceptorName));

		return codeContent.toString();
	}
	
	public static String generateAfterInterceptor(final String interceptorRegisterClassName,
			final String interceptorName, int paramNums) {
		StringBuffer codeContent = new StringBuffer();
		codeContent.append(String.format(AFTER_INTERCEPTOR_FORMAT, interceptorRegisterClassName, interceptorName));
		return codeContent.toString();
	}

}
