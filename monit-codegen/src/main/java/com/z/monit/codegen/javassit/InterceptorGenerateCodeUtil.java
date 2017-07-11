package com.z.monit.codegen.javassit;

public class InterceptorGenerateCodeUtil {

	private static final String BEFORE_INTERCEPTOR_FORMAT = "%s.getInterceptor(\"%s\").before($0,$args);";

	public static void main(String[] args) {
		String res = generateBeforeInterceptor("com.t.regsiter", "AbstactProvider", 1);
		System.out.println(res);
	}

	public static String generateBeforeInterceptor(final String interceptorRegisterClassName,
			final String interceptorName, int paramNums) {
		StringBuffer codeContent = new StringBuffer();

		codeContent.append(String.format(BEFORE_INTERCEPTOR_FORMAT, interceptorRegisterClassName, interceptorName));

		return codeContent.toString();
	}

}
