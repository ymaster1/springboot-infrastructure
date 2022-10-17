package cn.me.ppx.infrastructure.mybatis.mapper;


import tk.mybatis.mapper.common.*;

/**
 * 自定义通用 Mapper
 *
 * @param <T>
 * @author panda
 */
public interface CommonMapper<T> extends
        BaseMapper<T>,
        MySqlMapper<T>,
        IdsMapper<T>,
        ExampleMapper<T>,
        RowBoundsMapper<T> {
}
