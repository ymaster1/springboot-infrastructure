package test.dao.mapper;

import cn.me.ppx.infrastructure.mybatis.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import test.entity.App;

@Mapper
public interface AppMapper extends CommonMapper<App> {
}