
package cn.me.ppx.infrastructure.mybatis.repository.impl;

import cn.me.ppx.infrastructure.mybatis.entity.BaseLogicEntity;
import cn.me.ppx.infrastructure.mybatis.repository.BaseRepository;
import com.github.pagehelper.PageInfo;
import com.ovo.platform.mybatis.model.DeletedEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;



@Slf4j
public abstract class AbstractBaseLogicRepository<T extends BaseLogicEntity> extends AbstractBaseRepository<T> implements BaseRepository<T> {


/**
     * 创建一个Class的对象来获取泛型的class
     */

    private Class<?> clazz;

    protected Class<?> getClazz() {
        if (clazz == null) {
            //获取泛型的Class对象
            if (this.getClass().getGenericSuperclass() instanceof ParameterizedType) {

                clazz = ((Class<?>)
                        (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
            }
        }
        return clazz;
    }

    @Override
    public int insert(T record) {
        record.setGmtCreate(null);
        record.setGmtModified(null);
        return super.insert(record);
    }

    @Override
    public int update(T record) {
        record.setGmtModified(null);
        return super.update(record);
    }

    @Override
    public List<T> select(T param) {
        if (param != null) {
            param.setIsDelete(DeletedEnum.FALSE.getCode());
        }
        return super.select(param);
    }

    @Override
    public int delete(T param) {
        param.setIsDelete(DeletedEnum.TRUE.getCode());
        return update(param);
    }

    @Override
    public T selectByPk(Serializable pk) {
        T entity = super.selectByPk(pk);
        return entity != null && entity.getIsDelete()==DeletedEnum.FALSE.getCode() ? entity : null;
    }

    @Override
    public List<T> selectAll() {
        T param = null;
        try {
            param = (T) getClazz().newInstance();
        } catch (InstantiationException e) {
            log.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.warn(e.getMessage(), e);
        }
        return this.select(param);
    }

    @Override
    public List<T> selectByPks(Iterable<? extends Serializable> pks) {
        List<T> entities = super.selectByPks(pks);
        for (int i = entities.size() - 1; i >= 0; i --) {
            if (entities.get(i).getIsDelete()==DeletedEnum.TRUE.getCode()) {
                entities.remove(i);
            }
        }
        return entities;
    }

    @Override
    public T selectOne(T param) {
        param.setIsDelete(DeletedEnum.FALSE.getCode());
        return super.selectOne(param);
    }

    @Override
    public PageInfo<T> selectPage(T param, int pageNum, int pageSize) {
        param.setIsDelete(DeletedEnum.FALSE.getCode());
        return super.selectPage(param, pageNum, pageSize);
    }

    @Override
    public PageInfo<T> selectPageAndCount(T param, int pageNum,
            int pageSize) {
        param.setIsDelete(DeletedEnum.FALSE.getCode());
        return super.selectPageAndCount(param, pageNum, pageSize);
    }

}

