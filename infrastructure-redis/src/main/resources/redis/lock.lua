---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by luofangbing.
--- DateTime: 2022/7/18 10:28 AM
---

if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then
    return redis.call('expire',KEYS[1],ARGV[2])
else
    return 0
end
