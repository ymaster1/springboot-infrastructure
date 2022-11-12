-- 获取旧值，设置新值的过期时间
local key = KEYS[1]
local val = ARGV[1]
local ttl = ARGV[2]

local oldVal = redis.call('GET', key);
redis.call('SETEX', key, ttl, val);
return oldVal;