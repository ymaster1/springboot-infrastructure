package cn.me.ppx.infratructure.sample.mapper;

import cn.me.ppx.infrastructure.mybatis.mapper.CommonMapper;
import cn.me.ppx.infratructure.sample.entity.SysApp;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ym
 * @date 2022/10/12 11:58
 */
@Mapper
public interface SysAppMapper extends CommonMapper<SysApp> {
}
