package com.jsonyao.springcloud.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 责任链测试客户端
 */
public class ChainTestClient {

    public static void main(String[] args) {
        ChainContext chainContext = new ChainContext();
        chainContext.setVendorCode("123");

        ChainServiceImplFactory factory = new ChainServiceImplFactory();
        ChainServiceImplBuilder builder = new ChainServiceImplBuilder();
        ChainService chainService = builder
                .addChainService(factory.getChainService(TypeEnum.ICON))
                .addChainService(factory.getChainService(TypeEnum.CO))
                .addChainService(factory.getChainService(TypeEnum.COURSE))
                .build();

        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(new ChainServiceRunnable(chainService, chainContext));

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future> futureList = new ArrayList<>(runnableList.size());
        for (Runnable runnable : runnableList) {
            futureList.add(executorService.submit(runnable));
        }

        for (Future future : futureList) {
            try {
                Object o = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                // xxx计算失败!
            }
        }
    }
}

class ChainServiceRunnable implements Runnable {

    private ChainService chainService;
    private ChainContext context;

    public ChainServiceRunnable(ChainService chainService, ChainContext context) {
        this.chainService = chainService;
        this.context = context;
    }

    @Override
    public void run() {
        if(chainService.executeTask(context)) {
            System.err.println("成功");
        }
    }
}
