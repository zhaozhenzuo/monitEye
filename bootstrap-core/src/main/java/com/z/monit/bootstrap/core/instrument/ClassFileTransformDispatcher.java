package com.z.monit.bootstrap.core.instrument;

import java.lang.instrument.ClassFileTransformer;

/**
 * 织入分发器<br/>
 * 基本流程: <br/>
 * 1.jvm systemClassLoader加载agent<br/>
 * 2.将plugin包所有plugin对应TransformCallBack注册到TransformRegister<br/>
 * 3.Agent实现类,实现此TransformDispatcher,并将其加入到jdk Instrument中<br/>
 *   (调用addTransformer(ClassFileTransformer transformer))
 * 
 * 
 * @author zhaozhenzuo
 *
 */
public interface ClassFileTransformDispatcher extends ClassFileTransformer {

}
