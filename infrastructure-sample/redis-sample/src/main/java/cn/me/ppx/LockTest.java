package cn.me.ppx;

import cn.me.ppx.infrastructure.redis.lock.LockRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ym
 * @date 2022/11/12 11:08
 */
@Component
public class LockTest {
    @Autowired
    private LockRedis lockRedis;
    @PostConstruct
    public void test(){
        boolean lock1 = lockRedis.lock("lock");
        System.out.println(lock1);

        boolean lock2 = lockRedis.lock("lock");
        System.out.println(lock2);

        boolean lock3 = lockRedis.unLock("lock");
        System.out.println(lock3);

        boolean lock4 = lockRedis.lock("lock");
        System.out.println(lock4);
    }
}
