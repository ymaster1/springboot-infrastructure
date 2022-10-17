package test.repository.impl;

import cn.me.ppx.infrastructure.mybatis.repository.impl.AbstractBaseLogicRepository;
import org.springframework.stereotype.Repository;
import test.entity.App;
import test.repository.AppRepository;

@Repository("appRepository")
public class AppRepositoryImpl extends AbstractBaseLogicRepository<App> implements AppRepository {
}