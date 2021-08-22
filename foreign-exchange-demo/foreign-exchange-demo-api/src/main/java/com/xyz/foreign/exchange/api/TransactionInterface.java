package com.xyz.foreign.exchange.api;

import com.xyz.foreign.exchange.api.vo.tarnsaction.AgreeTransactionVO;
import com.xyz.foreign.exchange.api.vo.tarnsaction.InitiateTransactionVO;
import org.dromara.hmily.annotation.Hmily;

/**
 * 交易
 *
 * @author Zhu WeiJie
 * @date 2021/8/19
 **/
public interface TransactionInterface {

    /**
     * 发起交易
     *
     * @param paramVO
     */
    void initiateTransaction(InitiateTransactionVO paramVO);


    /**
     * 同意交易
     *
     * @param paramVO
     */
    @Hmily
    void agreeTransaction(AgreeTransactionVO paramVO);
}
