package com.xyz.foreign.exchange.api;

import com.xyz.foreign.exchange.api.vo.account.FreezeVO;
import com.xyz.foreign.exchange.api.vo.account.TransferRollbackVO;
import com.xyz.foreign.exchange.api.vo.account.TransferVO;

/**
 * 账户
 *
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
public interface AccountInterface {

    /**
     * 转账（限定双方均开通该币种账户）
     *
     * @param paramVO
     */
    void transfer(TransferVO paramVO);

    /**
     * 冻结
     *
     * @param paramVO
     */
    void freeze(FreezeVO paramVO);

    /**
     * 尝试回滚交易
     *
     * @param paramVO
     */
    void tryRollbackTransfer(TransferRollbackVO paramVO);

}
