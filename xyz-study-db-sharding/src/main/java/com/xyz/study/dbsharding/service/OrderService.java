package com.xyz.study.dbsharding.service;

import com.xyz.study.dbsharding.dao.OrderMapper;
import com.xyz.study.dbsharding.domain.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zhu WeiJie
 * @date 2021/8/14
 **/
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    public List<Order> query(long uid){
        return orderMapper.selectListByUid(uid);
    }

    public Order query(long uid, long orderId){
        return orderMapper.selectByUidAndId(uid,orderId);
    }

    public void add(Order order){
        orderMapper.insert(order);
    }
}
