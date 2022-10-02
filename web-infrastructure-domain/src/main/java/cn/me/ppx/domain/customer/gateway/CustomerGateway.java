package cn.me.ppx.domain.customer.gateway;

import cn.me.ppx.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);
}
