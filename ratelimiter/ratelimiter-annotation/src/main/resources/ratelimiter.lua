-- 自定义Redis+Lua限流组件: 实际限流Lua脚本

-- 获取方法签名
local methodKey = KEYS[1];
redis.log(redis.LOG_DEBUG, 'method key is', methodKey);

-- 获取调用脚本方法传入的限流大小
local limit = tonumber(ARGV[1]);

-- 获取当前流量大小
local curcount = tonumber(redis.call('get', methodKey) or "0");

-- 判断是否超出限流阈值
if curcount+1 > limit then
    -- 超出, 则拒绝服务
    return false;
else
    -- 没超出, 则增加当前访问数量 以及 过期时间
    redis.call("INCRBY", methodKey, 1);
    redis.call("EXPIRE", methodKey, 1);
    return true;
end