package cn.me.ppx.infrastructure.mybatis.model;

import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoModel<T>  {

    /**
     * 当前页号（前端传入）
     */
    private Integer page;

    /**
     * 每页条数（前端传入）
     */

    private Integer count;

    /**
     * 当前查询条件下：一共多少条数据
     */

    private Long total;

    /**
     * 当前页的数据
     */

    private List<T> list;

    public static <T> PageInfoModel<T> valueOf(Page<T> page) {
        return new PageInfoModel<>(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getResult());
    }

    public static <T> PageInfoModel<T> valueOf(List<T> list) {
        if (list instanceof Page) {
            return valueOf((Page<T>) list);
        } else {
            return new PageInfoModel<>(1, list.size(), (long) list.size(), list);
        }
    }
}

