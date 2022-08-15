package com.jsonyao.springcloud.chain;

/**
 * 责任链实现类工厂
 */
public class ChainServiceImplFactory {

    public AbstractChainServiceImpl getChainService(TypeEnum typeEnum) {
        switch (typeEnum) {
            case ICON:
                return new IconChainServiceImpl();
            case CO:
                return new CoChainServiceImpl();
            case COURSE:
                return new CourseChainServiceImpl();
            default:
                return null;
        }
    }
}
