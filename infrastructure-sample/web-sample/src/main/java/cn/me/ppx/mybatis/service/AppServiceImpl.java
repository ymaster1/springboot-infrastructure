package cn.me.ppx.mybatis.service;

import cn.me.ppx.mybatis.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ym
 * @date 2022/10/12 11:52
 */
@Service
public class AppServiceImpl implements AppService {

    @Override
    public Test test1() {
        Test test = new Test();
        test.setGmtCreate(new Date());
        return test;
    }

}
