package com.jsonyao.springcloud.chainAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 责任链实现类工厂
 */
public class ChainServiceImplFactory {

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public AbstractChainServiceImpl getChainService(TypeEnum typeEnum) {
        switch (typeEnum) {
            case ICON:
                return new IconChainServiceImpl(executorService);
            case CO:
                return new CoChainServiceImpl(executorService);
            case COURSE:
                return new CourseChainServiceImpl(executorService);
            default:
                return null;
        }
    }

    public void shutDown() {
        executorService.shutdown();
    }
}
