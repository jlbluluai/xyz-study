package com.xyz.foreign.exchange.api.vo.account;

import com.xyz.foreign.exchange.api.enums.CurrencyEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhu WeiJie
 * @date 2021/8/21
 **/
@Data
public class FreezeRollbackVO implements Serializable {

    private static final long serialVersionUID = -1543701950664312336L;

    private long uid;

    private CurrencyEnum currency;

    private long exchangeId;
}
