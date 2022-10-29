package cn.me.ppx.infrastructure.mybatis.util;

import cn.me.ppx.infrastructure.common.dto.BasePageRequest;
import com.github.pagehelper.PageHelper;

/**
 * @author ym
 * @date 2022/10/29 10:41
 */
public class PageUtils {
    private PageUtils() {

    }

    /**
     * mybatis分页
     * 单页最大不超过100条
     * 默认20条
     */
    public static void startPage(BasePageRequest request) {
        PageHelper.startPage(request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage(), request.getCount() == null || request.getCount() <= 0 || request.getCount() > 100 ? 20 : request.getCount());
    }
}
