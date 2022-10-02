package cn.me.ppx.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import cn.me.ppx.dto.CustomerAddCmd;
import cn.me.ppx.dto.CustomerListByNameQry;
import cn.me.ppx.dto.data.CustomerDTO;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd customerAddCmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry);
}
