package com.xyz.study.hmily.demo.api;

import com.xyz.study.hmily.demo.api.vo.OrderAddVO;
import org.dromara.hmily.annotation.Hmily;

/**
 * @author Zhu WeiJie
 * @date 2021/8/15
 **/
public interface OrderInterface {

    @Hmily
    void placeAnOrder(OrderAddVO addVO);
}
