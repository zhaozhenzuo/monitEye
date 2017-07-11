package com.z.monit.codegen.javassit;

import com.z.monit.bootstrap.core.agent.DefaultAgentParam;
import com.z.monit.bootstrap.core.constants.MonitConstants;
import com.z.monit.bootstrap.core.intercept.Interceptor;
import com.z.monit.bootstrap.core.intercept.register.InterceptorRegister;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class InstrumentCodeUtil {

	public static void main(String[] args) throws NotFoundException {
		ClassPool classPool = ClassPool.getDefault();
		classPool.appendClassPath("/Users/zhaozhenzuo/Documents/monit/*");
		
		

	}

	/**
	 * 在对应类对应方法前面加入interceptor的before方法
	 * 
	 * @param className
	 * @param method
	 * @param interceptor
	 * @return
	 */
	public static CtClass addBeforeInterceptor(String className, Interceptor interceptor, String method,
			Class<?>... paramTypeNames) {
		System.out.println(
				"=====addBeforeInterceptor cur thread classloader[" + Thread.currentThread().getContextClassLoader());
		System.out.println("===InstrumentCodeUtil classloader[" + InstrumentCodeUtil.class.getClassLoader());

		/**
		 * 1.将interceptor注册
		 */
		String interceptorName = interceptor.getClass().getName();
		String interceptorRegisterClassName = InterceptorRegister.class.getName();
		InterceptorRegister.register(interceptorName, interceptor);

		/**
		 * 2.生成新方法，会调用原方法，并在调用前加入interceptor before的调用
		 */
		ClassPool classPool = ClassPool.getDefault();
		try {
			classPool.appendClassPath((String) DefaultAgentParam.configMap.get(MonitConstants.bootstrapCoreDir) + "/*");
			classPool.appendClassPath((String) DefaultAgentParam.configMap.get(MonitConstants.pluginDir));
			classPool.appendClassPath((String) DefaultAgentParam.configMap.get(MonitConstants.defaultLibDir));

		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CtClass ctClass = null;
		try {
			ctClass = classPool.get(className);

			CtMethod oldMethod = getCtMethod(classPool, ctClass, method, paramTypeNames);

			// 拷贝旧方法
			CtMethod newMehtod = CtNewMethod.copy(oldMethod, ctClass, null);

			// 原方法改名为＝原方法名$$Old
			String oldMethodName = method + "$$Old";
			oldMethod.setName(oldMethodName);

			// 新方法需要旧方法逻辑前加上codeContent代码
			StringBuilder sb = new StringBuilder(100);
			sb.append("{\n");

			/**
			 * before拦截器代码
			 */
			int paramNums = 0;
			if (paramTypeNames != null) {
				paramNums = paramTypeNames.length;
			}

			String beforeInterceptorCode = InterceptorGenerateCodeUtil
					.generateBeforeInterceptor(interceptorRegisterClassName, interceptorName, paramNums);
			sb.append(beforeInterceptorCode);

			sb.append("return "+oldMethodName + "($$);\n");
			sb.append("}");
			newMehtod.setBody(sb.toString());
			newMehtod.setName(method);
			ctClass.addMethod(newMehtod);

			System.out.println("==codeRes[" + sb + "]");

			return ctClass;
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
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

	public static CtClass addBefore(String className, String method, String codeContent) {
		System.out.println(">addBefore");
		ClassPool classPool = ClassPool.getDefault();

		CtClass ctClass = null;
		try {
			ctClass = classPool.get(className);

			CtMethod oldMethod = ctClass.getDeclaredMethod(method);

			// 拷贝旧方法
			CtMethod newMehtod = CtNewMethod.copy(oldMethod, ctClass, null);

			// 原方法改名为＝原方法名$$Old
			String oldMethodName = method + "$$Old";
			oldMethod.setName(oldMethodName);

			// 新方法需要旧方法逻辑前加上codeContent代码
			StringBuilder sb = new StringBuilder(100);
			sb.append("{\n");
			sb.append(codeContent);
			sb.append(oldMethodName + "($$);\n");
			sb.append("}");
			newMehtod.setBody(sb.toString());
			newMehtod.setName(method);
			ctClass.addMethod(newMehtod);

			System.out.println("==codeRes[" + sb + "]");

			return ctClass;
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}

		return null;

	}

}
