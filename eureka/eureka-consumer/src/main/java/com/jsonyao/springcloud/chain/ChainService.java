package com.jsonyao.springcloud.chain;

/**
 * 责任链公共接口
 */
public interface ChainService {

    boolean executeTask(ChainContext context);

}
