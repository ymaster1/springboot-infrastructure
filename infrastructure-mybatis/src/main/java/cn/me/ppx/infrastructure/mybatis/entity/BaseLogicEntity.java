package cn.me.ppx.infrastructure.mybatis.entity;

import cn.me.ppx.infrastructure.common.dto.BaseObject;
import cn.me.ppx.infrastructure.common.serializer.JsonDateSerialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
public class BaseLogicEntity extends BaseObject {

    /**
     * 实体Id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    /**
     * 创建时间
     */

    @Column(name = "gmt_create")
    @Temporal(TemporalType.TIMESTAMP) // 实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型
    @JsonSerialize(using = JsonDateSerialize.class) //后台到前台的序列化，功能更丰富
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 前台到后台的时间格式转换
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //后台到前台的时间格式转换 两个之一都行
    private Date gmtCreate;


    /**
     * 更新时间
     */

    @Column(name = "gmt_modified")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;


    /**
     * 是否是可用数据
     * Byte 数据可以直接转
     * 0-可用
     * 1-不可用
     */

    @Column(name = "is_delete")
    private Byte isDelete;
}

