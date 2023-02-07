package com.rapipay.wrapperutility.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

public class RedisUtility {
    private static int port = 6379;
    private static String url = "127.0.0.1";
    private static Logger log = LogManager.getLogger(RedisUtility.class);

    @Autowired
    private RedisTemplate<String, String> template;
    public static String getRedisKeyData(String rKey, Boolean printableLog){
        String data = "";
        try(Jedis jedis=new Jedis(url, port)) {
            jedis.connect();
            data = jedis.get(rKey);
            if (Boolean.TRUE.equals(printableLog)) {
                log.info(" Getting Data from Reddis against '{}' key : {}",  rKey, data);
            }
            if (Boolean.FALSE.equals(printableLog)) {
                log.info("Getting Data from Reddis: {}",  rKey);
            }
        } catch (Exception var7) {
            log.error("Exception caught in get data from Reddis: %s",  var7);
            throw var7;
        }
        return data;
    }

    public static boolean setRedisKeyData(String rKey, String keyData, Boolean printableLog){
        if (Boolean.TRUE.equals(printableLog)) {
            log.info(" Setting data in Reddis against '{}' key: {}", rKey, keyData);
        }
        if (Boolean.FALSE.equals(printableLog)) {
            log.info(" Setting data in Reddis: {}", rKey);
        }
        Boolean result = false;
        try (Jedis jedis=new Jedis(url, port)){
            jedis.connect();
            jedis.set(rKey, keyData);
            result = true;
        } catch (Exception var8) {
            log.error(" Exception caught Setting Data In Redis: {}",  var8.getMessage());
        }
        return result;
    }

    public static boolean deleteRedisKey(String rKey, Boolean printableLog){
        if (Boolean.TRUE.equals(printableLog)) {
            log.info("Delete Data from Reddis against '{}' Key",  rKey);
        }

        if (Boolean.FALSE.equals(printableLog)) {
            log.info("Delete Data from Reddis");
        }
        Boolean result = false;

        try(Jedis jedis=new Jedis(url, port)){
            jedis.connect();
            jedis.del(rKey);
            result = true;
        } catch (Exception var7) {
            log.error(" Exception caught Delete Data from Reddis >>>>> {}", var7.getMessage());
        }
        return result;
    }

    public static boolean setRedisKeyDataExpireTime( String rKey, String keyData, int expireTime, Boolean printableLog){
        if (Boolean.TRUE.equals(printableLog)) {
            log.info("Setting Data In Redis against '{}' Key with Expire Time '{}' Data:{} ",  rKey, keyData, expireTime);
        }

        if (Boolean.FALSE.equals(printableLog)) {
            log.info("Setting Data In Redis '{}' with Expire Time '{}' ", rKey, expireTime);
        }
        Boolean result = false;
        try(Jedis jedis=new Jedis(url, port)) {
            jedis.connect();
            jedis.set(rKey, keyData);
            jedis.expire(rKey, expireTime);
            result = true;
        } catch (Exception var9) {
            log.error(" Exception Caught Setting data In Redis with expirationTime {}", var9.getMessage());
        }
        return result;
    }
}
