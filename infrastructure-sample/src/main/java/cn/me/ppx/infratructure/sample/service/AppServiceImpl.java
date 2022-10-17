package cn.me.ppx.infratructure.sample.service;

import cn.me.ppx.infratructure.sample.entity.SysApp;
import cn.me.ppx.infratructure.sample.entity.Test;
import cn.me.ppx.infratructure.sample.repository.SysAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ym
 * @date 2022/10/12 11:52
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private SysAppRepository repository;

    @Override
    public Test test1() {
        Test test = new Test();
        test.setGmtCreate(new Date());
        return test;
    }

    @Override
    public SysApp test() {
        return repository.selectByPk(1);
    }
}
