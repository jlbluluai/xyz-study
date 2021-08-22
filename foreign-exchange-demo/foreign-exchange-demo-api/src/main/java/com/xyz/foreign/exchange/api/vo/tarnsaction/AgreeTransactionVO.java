package com.xyz.foreign.exchange.api.vo.tarnsaction;

import lombok.Data;

/**
 * @author Zhu WeiJie
 * @date 2021/8/19
 **/
@Data
public class AgreeTransactionVO {

    /**
     * 用户id
     */
    private long uid;

    /**
     * 交易单号
     */
    private long exchangeId;
}
