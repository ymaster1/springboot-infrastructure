package cn.me.ppx.infrastructure.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author ym
 * @date 2022/10/12 10:09
 */
@Data
public class BasePageRequest extends BaseObject {

    private Integer page;

    private Integer count;

    private Date gmtCreate;

    private Date gmtModified;
}
