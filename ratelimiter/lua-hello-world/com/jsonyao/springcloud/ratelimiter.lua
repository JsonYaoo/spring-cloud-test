-- 模拟限流Lua脚本
local key = 'My Key';   -- 用于限流的Key
local limit = 2;        -- 限流的最大阈值
local currentLimit = 0; -- 当前流量大小

-- 判断如果增加当前请求是否超出限流标准
if currentLimit+1 > limit then
    print 'reject';
    return false;
else
    print 'accept';
    return true;
end