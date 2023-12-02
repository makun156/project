local par=ARGV[1]
local seckill_id=ARGV[2]
--local key= "test:" .. par .. ":10086:20231129"

if(tostring(redis.call('get',par)) == 'nil') then
    -- 已经没有资格秒杀
    return 1
end
if(tonumber(redis.call('decrby',seckill_id,1)) < 0) then
    --库存不足
    return 2
end
return 0