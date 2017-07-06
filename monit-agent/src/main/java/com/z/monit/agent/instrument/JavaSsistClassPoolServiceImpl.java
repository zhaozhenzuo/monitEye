package com.z.monit.agent.instrument;

import java.util.Map;
import java.util.WeakHashMap;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/**
 * javassist查找对应ctClass实现
 * 
 * @author zhaozhenzuo
 *
 */
public class JavaSsistClassPoolServiceImpl implements ClassPoolService<CtClass> {

	// rootClassPool,能够加载jdk,agent core,monit agent jar
	private ClassPool rootClassPool;

	// 此处使用weakHashMap,垃圾回收时会马上回收掉这部分内存,防止内存占用过多
	private Map<ClassLoader, ClassPool> childClassPool = new WeakHashMap<ClassLoader, ClassPool>();

	public JavaSsistClassPoolServiceImpl(ClassPool rootClassPool) {
		this.rootClassPool = rootClassPool;
	}

	public CtClass getCtClass(ClassLoader classLoader, String className, byte[] classfileBuffer)
			throws NotFoundException {
		ClassPool classPool = this.getClassPoolByClassLoader(classLoader);
		return classPool.get(className);
	}

	public ClassPool getClassPoolByClassLoader(ClassLoader classLoader) {
		/**
		 * 1.缓存中查找classPool,有则返回
		 */
		ClassPool classPool = childClassPool.get(classLoader);
		if (classPool != null) {
			return classPool;
		}

		/**
		 * 2.缓存中没有则创建一个,并放入缓存
		 */
		ClassPool classPoolGenerate = new ClassPool(rootClassPool);
		ClassPath classPath = new LoaderClassPath(classLoader);
		classPoolGenerate.appendClassPath(classPath);
		childClassPool.put(classLoader, classPoolGenerate);

		return classPoolGenerate;
	}

	public ClassPool getRootClassPool() {
		return rootClassPool;
	}

	public void setRootClassPool(ClassPool rootClassPool) {
		this.rootClassPool = rootClassPool;
	}

}
