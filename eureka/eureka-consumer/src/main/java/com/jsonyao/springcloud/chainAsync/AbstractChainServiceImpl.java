package com.jsonyao.springcloud.chainAsync;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 抽象责任链实现类
 */
public abstract class AbstractChainServiceImpl implements ChainService, Callable<Boolean> {

    protected ChainService next;

    protected ChainContext context;

    protected ExecutorService executor;

    public AbstractChainServiceImpl(ExecutorService executor) {
        this.executor = executor;
    }

    public void next(ChainService next) {
        this.next = next;
    }

    @Override
    public boolean executeTask(ChainContext context) {
        this.context = context;
        if(!isRun(context)) {
            Future<Boolean> future = executor.submit(this);
            try {
                return next != null? next.executeTask(context) && future.get() : future.get();
            } catch (Exception e) {
                return false;
            }
        }

        return next == null || next.executeTask(context);
    }

    protected abstract boolean isRun(ChainContext context);
}
