/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.z.monit.test.base;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.jar.JarFile;


public class InstrumentationForTest implements Instrumentation {

	public void addTransformer(ClassFileTransformer transformer, boolean canRetransform) {

	}

	public void addTransformer(ClassFileTransformer transformer) {

	}

	public boolean removeTransformer(ClassFileTransformer transformer) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRetransformClassesSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public void retransformClasses(Class<?>... classes) throws UnmodifiableClassException {
		// TODO Auto-generated method stub
		
	}

	public boolean isRedefineClassesSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public void redefineClasses(ClassDefinition... definitions)
			throws ClassNotFoundException, UnmodifiableClassException {
		// TODO Auto-generated method stub
		
	}

	public boolean isModifiableClass(Class<?> theClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Class[] getAllLoadedClasses() {
		// TODO Auto-generated method stub
		return new Class[0];
	}

	public Class[] getInitiatedClasses(ClassLoader loader) {
		// TODO Auto-generated method stub
		return new Class[0];
	}

	public long getObjectSize(Object objectToSize) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void appendToBootstrapClassLoaderSearch(JarFile jarfile) {
		// TODO Auto-generated method stub
		
	}

	public void appendToSystemClassLoaderSearch(JarFile jarfile) {
		// TODO Auto-generated method stub
		
	}

	public boolean isNativeMethodPrefixSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNativeMethodPrefix(ClassFileTransformer transformer, String prefix) {
		
	}
}
