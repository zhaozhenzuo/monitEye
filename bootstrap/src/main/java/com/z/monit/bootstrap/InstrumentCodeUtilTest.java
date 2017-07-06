package com.z.monit.bootstrap;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class InstrumentCodeUtilTest {

	public static CtClass addBefore(String className, String method, String codeContent) {
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

			return ctClass;
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}

		return null;

	}

}
