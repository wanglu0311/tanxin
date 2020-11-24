package com.tanxin.framework.domain.order.response;

import com.tanxin.framework.domain.order.TxOrders;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/26.
 */
@Data
@ToString
public class OrderResult extends ResponseResult {
    private TxOrders xcOrders;
    public OrderResult(ResultCode resultCode, TxOrders xcOrders) {
        super(resultCode);
        this.xcOrders = xcOrders;
    }


}
