package com.jsonyao.springcloud.chain;

/**
 * 历程
 */
public class CourseChainServiceImpl extends AbstractChainServiceImpl{

    @Override
    protected boolean isRun(ChainContext context) {
        return false;
    }

    @Override
    public boolean executeTask(ChainContext context) {
        if(!isRun(context)) {
            selectCourse(context.getVendorCode());
        }
        if(next == null) {
            return true;
        }
        return next.executeTask(context) && isOk;
    }

    private void selectCourse(String vendorCode) {
        isOk = true;
    }
}
