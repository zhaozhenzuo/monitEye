package com.z.monit.codegen.javassit;

import java.io.IOException;

import com.z.monit.bootstrap.core.agent.DefaultAgentParam;
import com.z.monit.bootstrap.core.constants.MonitConstants;
import com.z.monit.bootstrap.core.intercept.Interceptor;
import com.z.monit.bootstrap.core.intercept.register.InterceptorRegister;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class InstrumentCodeUtil {

	public static void main(String[] args) throws NotFoundException {
		ClassPool classPool = ClassPool.getDefault();
		classPool.appendClassPath("/Users/zhaozhenzuo/Documents/monit/*");

	}
	
	public static byte[] ctClassToByteArr(CtClass ctClass) throws IOException, CannotCompileException{
		ctClass.detach();
		return ctClass.toBytecode();
	}

	/**
	 * 在对应类对应方法前面加入interceptor的before和after方法
	 * 
	 * @param className
	 * @param method
	 * @param interceptor
	 * @return
	 */
	public static CtClass addBeforeAndAfterInterceptor(String className, Interceptor interceptor, String method,
			Class<?>... paramTypeNames) {
		try {

			/**
			 * 1.将interceptor注册
			 */
			String interceptorName = interceptor.getClass().getName();
			String interceptorRegisterClassName = InterceptorRegister.class.getName();
			InterceptorRegister.register(interceptorName, interceptor);

			/**
			 * 2.生成新方法，会调用原方法，并在调用前加入interceptor before的调用
			 */
			CtClass ctClass = null;
			ClassPool classPool = getClassPool();
			ctClass = classPool.get(className);

			CtMethod oldMethod = getCtMethod(classPool, ctClass, method, paramTypeNames);

			/**
			 * before拦截器代码
			 */
			int paramNums = 0;
			if (paramTypeNames != null) {
				paramNums = paramTypeNames.length;
			}

			String beforeInterceptorCode = InterceptorGenerateCodeUtil
					.generateBeforeInterceptor(interceptorRegisterClassName, interceptorName, paramNums);
			oldMethod.insertBefore(beforeInterceptorCode);
			
			String afterInterceptorCode = InterceptorGenerateCodeUtil
					.generateAfterInterceptor(interceptorRegisterClassName, interceptorName, paramNums);
			oldMethod.insertAfter(afterInterceptorCode);
			
//			ctClass.writeFile("/Users/zhaozhenzuo/Documents/temp/dub");
			
			return ctClass;
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	private static CtMethod getCtMethod(ClassPool classPool, CtClass ctClass, String method, Class<?>... paramTypes)
			throws NotFoundException {
		CtMethod cm = null;

		try {
			String[] paramTypeNames = null;
			if (paramTypes != null) {
				paramTypeNames = new String[paramTypes.length];
				for (int i = 0; i < paramTypes.length; i++)
					paramTypeNames[i] = paramTypes[i].getName();
			}

			if (paramTypeNames != null) {
				cm = ctClass.getDeclaredMethod(method, classPool.get(paramTypeNames));
			} else {
				cm = ctClass.getDeclaredMethod(method);
			}

			return cm;
		} catch (NotFoundException e) {
			throw e;
		}
	}

	private static ClassPool getClassPool() {
		ClassPool classPool = ClassPool.getDefault();
		try {
			classPool.appendClassPath((String) DefaultAgentParam.configMap.get(MonitConstants.bootstrapCoreDir) + "/*");
			classPool.appendClassPath((String) DefaultAgentParam.configMap.get(MonitConstants.pluginDir));
			classPool.appendClassPath((String) DefaultAgentParam.configMap.get(MonitConstants.defaultLibDir));

		} catch (NotFoundException e1) {
			e1.printStackTrace();
		}

		return classPool;

	}

}
