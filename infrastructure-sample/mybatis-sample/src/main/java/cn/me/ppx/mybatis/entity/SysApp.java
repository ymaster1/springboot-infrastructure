package cn.me.ppx.mybatis.entity;

import cn.me.ppx.infrastructure.mybatis.entity.BaseLogicEntity;
import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author ym
 * @date 2022/10/12 11:43
 */
@Data
@Table(name = "free_app")
public class SysApp extends BaseLogicEntity {

    private String customerCode;

    /**
     * Database Column Remarks:
     *   租户名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column free_app.customer_name
     *
     * @mbg.generated
     */
    private String customerName;

    /**
     * Database Column Remarks:
     *   应用名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column free_app.app_name
     *
     * @mbg.generated
     */
    private String appName;

    /**
     * Database Column Remarks:
     *   appKey
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column free_app.app_key
     *
     * @mbg.generated
     */
    private String appKey;

    /**
     * Database Column Remarks:
     *   appSecret
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column free_app.app_secret
     *
     * @mbg.generated
     */
    private String appSecret;

    /**
     * Database Column Remarks:
     *   充值分配总额（单位：分）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column free_app.total_amount
     *
     * @mbg.generated
     */
    private BigDecimal totalAmount;

    /**
     * Database Column Remarks:
     *   余额（单位：分）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column free_app.balance
     *
     * @mbg.generated
     */
    private BigDecimal balance;

    /**
     * Database Column Remarks:
     *   是否可用 0-可用 1-不可用
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column free_app.is_enable
     *
     * @mbg.generated
     */
    private Byte isEnable;




}
