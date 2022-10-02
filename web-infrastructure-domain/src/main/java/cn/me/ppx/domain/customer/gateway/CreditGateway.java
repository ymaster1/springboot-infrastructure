package cn.me.ppx.domain.customer.gateway;

import cn.me.ppx.domain.customer.Credit;

//Assume that the credit info is in another distributed Service
public interface CreditGateway {
    Credit getCredit(String customerId);
}
