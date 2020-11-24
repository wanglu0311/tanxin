package com.tanxin.framework.domain.order.response;

import com.tanxin.framework.domain.order.TxOrdersPay;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/27.
 */
@Data
@ToString
public class PayOrderResult extends ResponseResult {
    public PayOrderResult(ResultCode resultCode) {
        super(resultCode);
    }
    public PayOrderResult(ResultCode resultCode,TxOrdersPay xcOrdersPay) {
        super(resultCode);
        this.xcOrdersPay = xcOrdersPay;
    }
    private TxOrdersPay xcOrdersPay;
    private String orderNumber;

    // 当tradeState为NOTPAY（未支付）时显示支付二维码
    private String codeUrl;
    private Float money;


}
