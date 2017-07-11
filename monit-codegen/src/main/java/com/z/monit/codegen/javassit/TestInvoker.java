package com.z.monit.codegen.javassit;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;

public class TestInvoker implements Invocation {

	private final Object proxy;

	private final Class type;

	private final URL url;

	public TestInvoker(Object proxy, Class type, URL url) {
		if (proxy == null) {
			throw new IllegalArgumentException("proxy == null");
		}
		if (type == null) {
			throw new IllegalArgumentException("interface == null");
		}
		if (!type.isInstance(proxy)) {
			throw new IllegalArgumentException(proxy.getClass().getName() + " not implement interface " + type);
		}
		this.proxy = proxy;
		this.type = type;
		this.url = url;
	}

	public Class getInterface() {
		return type;
	}

	public URL getUrl() {
		return url;
	}

	public boolean isAvailable() {
		return true;
	}

	public void destroy() {
	}

	public Result invoke(Invocation invocation) throws RpcException {
		try {
			return new RpcResult(doInvoke(proxy, invocation.getMethodName(), invocation.getParameterTypes(),
					invocation.getArguments()));
		} catch (InvocationTargetException e) {
			return new RpcResult(e.getTargetException());
		} catch (Throwable e) {
			throw new RpcException("Failed to invoke remote proxy method " + invocation.getMethodName() + " to "
					+ getUrl() + ", cause: " + e.getMessage(), e);
		}
	}

	protected Object doInvoke(Object proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments)
			throws Throwable {
		return null;
	}

	@Override
	public String toString() {
		return getInterface() + " -> " + getUrl() == null ? " " : getUrl().toString();
	}

	public String getMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<?>[] getParameterTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getArguments() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> getAttachments() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAttachment(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAttachment(String key, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public Invoker<?> getInvoker() {
		// TODO Auto-generated method stub
		return null;
	}

}
