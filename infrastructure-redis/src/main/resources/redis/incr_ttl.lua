local key = KEYS[1]
local ttl = ARGV[1]

if redis.call('EXISTS', key) == 0 then
    redis.call('SETEX', key, ttl, 1)
    return 1
else
    return tonumber(redis.call('INCR', key))
end