package cn.me.ppx.infratructure.sample.mybatis.entity;

import cn.me.ppx.infrastructure.common.serializer.JsonDateSerialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author ym
 * @date 2022/10/14 10:32
 */
@Data
@ApiModel("TestDTO")
public class Test {
    @Column(name = "gmt_create")
    @Temporal(TemporalType.TIMESTAMP) // 实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型
    @JsonSerialize(using = JsonDateSerialize.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 前台到后台的时间格式转换
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") //后台到前台的时间格式转换
    @ApiModelProperty("创建时间")
    private Date gmtCreate;


}
