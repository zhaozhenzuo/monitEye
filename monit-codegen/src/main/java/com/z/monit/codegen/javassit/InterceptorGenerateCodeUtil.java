package com.z.monit.codegen.javassit;

public class InterceptorGenerateCodeUtil {

	private static final String BEFORE_INTERCEPTOR_FORMAT = "%s.getInterceptor(\"%s\").before($1,$2);";

	public static String generateBeforeInterceptor(final String interceptorRegisterClassName,
			final String interceptorName) {
		StringBuffer codeContent = new StringBuffer();
		codeContent.append(String.format(BEFORE_INTERCEPTOR_FORMAT, interceptorRegisterClassName, interceptorName));
		
		return codeContent.toString();
	}

}
