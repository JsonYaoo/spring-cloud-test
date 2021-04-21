# 大象可用数初始化为1
UPDATE `seata-zoo`.zoo zoo
SET zoo.available = 1;

# 冰箱初始化为可放入且门关闭
UPDATE `seata-fridge`.fridge fridge
SET fridge.animal_id = NULL AND fridge.door_opened = 0;

# 管理员删除记录
DELETE FROM `seata-housework`.housework;