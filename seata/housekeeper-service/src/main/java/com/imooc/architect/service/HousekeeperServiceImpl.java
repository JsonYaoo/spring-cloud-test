package com.imooc.architect.service;

import com.imooc.architect.dao.HouseworkDao;
import com.imooc.architect.entity.Housework;
import com.imooc.architect.feign.FridgeService;
import com.imooc.architect.feign.ZooService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 姚半仙.
 */
@Service("housekeeperService")
@Slf4j
public class HousekeeperServiceImpl implements HousekeeperService {

    @Autowired
    private FridgeService fridgeService;

    @Autowired
    private ZooService zooService;

    @Autowired
    private HouseworkDao houseworkDao;

    @Override
    @GlobalTransactional(name = "housekeeper-homework",rollbackFor = Exception.class)
    public void putElephantIntoFridge(Long fridgeId, Long animalId) {
        Housework housework = Housework.builder()
                .animalId(animalId)
                .fridgeId(fridgeId)
                .statusId(1).build();

        // 测试本地事务异常
        // 测试结果: 本地事务成功回滚, 这里是本地事务异常(未使用服务调用), 不会记录undo_log
        log.info("start housework - fridgeId={}, animalId={}", fridgeId, animalId);
        houseworkDao.start(housework);
//        int a1 = 1 / 0;

        // 测试fridge事务执行后异常
        // 测试结果: 会记录undo_log
        //     1、这里是由于house的事务异常, fridge事务成功回滚(异步删除undo_log, 因为站在fridge的角度他自己是执行成功了的, 所以会删除undo_log)
        //     2、而如果是由于fridge自己服务异常, 则它的RM会使用自己的undo_log回滚(这时不删除)
        fridgeService.open(fridgeId);
        log.info("fridge is opened - fridgeId={}", fridgeId);
//        int a2 = 1 / 0;

        // 测试zoo事务执行后异常
        // 测试结果: 会记录undo_log
        //     1、这里是由于house的事务异常, fridge事务成功回滚(异步删除undo_log, 因为站在fridge的角度他自己是执行成功了的, 所以会删除undo_log)
        //     2、而如果是由于zoo自己服务异常, 则它的RM会使用自己的undo_log回滚(这时不删除)
        zooService.takeAway(animalId);
        log.info("elephant is ready - animalId={}", animalId);
//        int a3 = 1 / 0;

        // 测试fridge事务执行后异常
        // 测试结果: 会记录undo_log
        //     1、这里是由于house的事务异常, fridge事务成功回滚(异步删除undo_log, 因为站在fridge的角度他自己是执行成功了的, 所以会删除undo_log)
        //     2、而如果是由于fridge自己服务异常, 则它的RM会使用自己的undo_log回滚(这时不删除)
        fridgeService.putAnimal(fridgeId, animalId);
        log.info("elephant is in place in the fridage - fridgeId={}, animalId={}", animalId, fridgeId);
//        int a4 = 1 / 0;

        // 测试fridge事务执行后异常
        // 测试结果: 会记录undo_log
        //     1、这里是由于house的事务异常, fridge事务成功回滚(异步删除undo_log, 因为站在fridge的角度他自己是执行成功了的, 所以会删除undo_log)
        //     2、而如果是由于fridge自己服务异常, 则它的RM会使用自己的undo_log回滚(这时不删除)
        fridgeService.close(fridgeId);
        log.info("fridge closed - fridgeId={}", fridgeId);
//        int a5 = 1 / 0;

        // 最后再测试本地事务异常
        // 测试结果: 本地事务成功回滚, 会记录undo_log, 使用undo_log回滚
        //     1、这里是由于已经调用过了远程服务, 已经生成了undo_log, 这时再在本地事务异常, 则会使用undo_log进行回滚, 所以没有删除undo_log
        houseworkDao.finish(housework.getId());
        log.info("work done - id={}", housework.getId());
//        int a6 = 1 / 0;

        // => 结论: 每个涉及到的表都需要有undo_log, 且根据异常情况利用undo_log回滚(这时不删除)或者成功提交(这时异步删除undo_log)
    }










//        log.info("start housework - fridgeId={}, animalId={}", fridgeId, animalId);
//        houseworkDao.start(housework);
//
//        fridgeService.open(fridgeId);
//        log.info("fridge is opened - fridgeId={}", fridgeId);
//
//        zooService.takeAway(animalId);
//        log.info("elephant is ready - animalId={}", animalId);
//
//        fridgeService.putAnimal(fridgeId, animalId);
//        log.info("elephant is in place in the fridage - fridgeId={}, animalId={}", animalId, fridgeId);
//
//        fridgeService.close(fridgeId);
//        log.info("fridge closed - fridgeId={}", fridgeId);
//
//        houseworkDao.finish(housework.getId());
//        log.info("finished cleaning the zoo - id={}", housework.getId());
}
