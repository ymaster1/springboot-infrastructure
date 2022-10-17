package test.entity;

import cn.me.ppx.infrastructure.mybatis.entity.BaseLogicEntity;
import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@Table(name = "free_app")
public class App extends BaseLogicEntity {
    /**
     * 租户code
     */
    @Column(name = "customer_code")
    private String customerCode;

    /**
     * 租户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * appKey
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * appSecret
     */
    @Column(name = "app_secret")
    private String appSecret;

    /**
     * 充值分配总额（单位：分）
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 余额（单位：分）
     */
    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * 是否可用 0-可用 1-不可用
     */
    @Column(name = "is_enable")
    private Byte isEnable;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("customerCode", customerCode)
                .add("customerName", customerName)
                .add("appName", appName)
                .add("appKey", appKey)
                .add("appSecret", appSecret)
                .add("totalAmount", totalAmount)
                .add("balance", balance)
                .add("isEnable", isEnable)
                .toString();
    }
}