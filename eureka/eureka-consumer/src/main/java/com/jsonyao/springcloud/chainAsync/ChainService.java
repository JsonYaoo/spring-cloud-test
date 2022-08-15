package com.jsonyao.springcloud.chainAsync;

/**
 * 责任链公共接口
 */
public interface ChainService {

    boolean executeTask(ChainContext context);

}
