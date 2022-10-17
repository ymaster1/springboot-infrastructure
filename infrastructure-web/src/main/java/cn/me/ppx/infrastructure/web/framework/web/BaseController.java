package cn.me.ppx.infrastructure.web.framework.web;

import cn.me.ppx.infrastructure.common.dto.BasePageRequest;
import cn.me.ppx.infrastructure.common.dto.BaseResponse;
import com.github.pagehelper.PageHelper;

/**
 * @author ym
 * @date 2022/10/12 9:53
 */
public class BaseController {
    /**
     * 构建统一响应
     * @param data
     * @return
     * @param <T>
     */
    protected <T> BaseResponse<T> build(T data) {
        return BaseResponse.build(data);
    }
    /**
     * 没有返回值的请求也需要统一响应
     * @return
     * @param <T>
     */
    protected <T> BaseResponse<T> ok() {
        return BaseResponse.success();
    }

    /**
     * mybatis分页
     * 单页最大不超过100条
     * 默认20条
     */
    protected void startPage(BasePageRequest request){
        PageHelper.startPage(request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage(), request.getCount() == null || request.getCount() <= 0 || request.getCount() > 100 ? 20 : request.getCount());
    }

}
