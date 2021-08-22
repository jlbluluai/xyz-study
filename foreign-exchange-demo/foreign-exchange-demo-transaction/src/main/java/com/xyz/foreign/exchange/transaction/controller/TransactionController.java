package com.xyz.foreign.exchange.transaction.controller;

import com.xyz.foreign.exchange.api.TransactionInterface;
import com.xyz.foreign.exchange.api.vo.tarnsaction.AgreeTransactionVO;
import com.xyz.foreign.exchange.api.vo.tarnsaction.InitiateTransactionVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zhu WeiJie
 * @date 2021/8/21
 **/
@RestController
@RequestMapping("/foreign/exchange/transaction")
public class TransactionController {

    @Resource
    private TransactionInterface transactionInterface;

    @PostMapping("/initiate")
    public void initiateTransaction(@RequestBody InitiateTransactionVO paramVO) {
        transactionInterface.initiateTransaction(paramVO);
    }

    @PostMapping("/agree")
    public void agreeTransaction(@RequestBody AgreeTransactionVO paramVO) {
        transactionInterface.agreeTransaction(paramVO);
    }

}
