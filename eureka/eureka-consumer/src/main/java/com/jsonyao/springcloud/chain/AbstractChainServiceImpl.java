package com.jsonyao.springcloud.chain;

/**
 * 抽象责任链实现类
 */
public abstract class AbstractChainServiceImpl implements ChainService {

    protected boolean isOk;

    protected ChainService next;

    protected abstract boolean isRun(ChainContext context);

    public void next(ChainService next) {
        this.next = next;
    }
}
