package com.xyz.foreign.exchange.api.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 币种枚举
 *
 * @author Zhu WeiJie
 * @date 2021/8/20
 **/
public enum CurrencyEnum {

    /**
     * 人民币
     */
    RMB(0, 1),

    /**
     * 美元
     */
    DOLLAR(1, 7),
    ;

    private final int code;

    /**
     * demo写死回汇率 所有汇率以rmb1元为基准兑换 例如美元的7代表1美元兑换7rmb
     */
    private final double rate;

    private static Map<Integer, CurrencyEnum> map = Arrays.stream(CurrencyEnum.values())
            .collect(Collectors.toMap(CurrencyEnum::getCode, e -> e));

    CurrencyEnum(int code, double rate) {
        this.code = code;
        this.rate = rate;
    }

    public int getCode() {
        return code;
    }

    public double getRate() {
        return rate;
    }

    public static CurrencyEnum resolve(int code) {
        return map.get(code);
    }
}
