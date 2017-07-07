//package org.dubbo.plugin;
//
//import java.io.IOException;
//
//import com.z.monit.bootstrap.dubbo.interceptor.DubboProvideInterceptor;
//import com.z.monit.codegen.javassit.InstrumentCodeUtil;
//
//import javassist.CannotCompileException;
//import javassist.CtClass;
//
//public class JavassitTest {
//
//	public static void main(String[] args)
//			throws InstantiationException, IllegalAccessException, CannotCompileException, IOException {
//		String className = "org.dubbo.plugin.A";
//		CtClass ctClass = InstrumentCodeUtil.addBeforeInterceptor(className, "show",
//				new DubboProvideInterceptor());
//
//		Object object = new Object();
//		Object[] objArgs = new Object[1];
//		objArgs[0] = new Object();
//		System.out.println(ctClass.toString());
//
//		ctClass.writeFile("/Users/zhaozhenzuo/Documents/temp/");
//
//		Class claz = ctClass.toClass();
//
////		A a = (A) claz.newInstance();
////
////		a.show(object, objArgs);
//
//	}
//
//}
