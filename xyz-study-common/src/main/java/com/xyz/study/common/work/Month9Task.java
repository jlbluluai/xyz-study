package com.xyz.study.common.work;

import com.xyz.study.common.utils.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhu WeiJie
 * @date 2021/9/3
 **/
public class Month9Task {

    /**
     * 先跑出目前所有最后一条常用租车人信息（手机号匹配用户手机号）
     */
    public static void main(String[] args) {
        /*
            SELECT
                r.user_id,r.uuid,r.maxus_id,AES_DECRYPT(r.name,'saicmotor_rv'),r.credential_type,AES_DECRYPT(r.credential_num,'saicmotor_rv'),r.first_name,r.last_name,r.credential_period
            FROM
                user_base_info u,
                ( SELECT * FROM ( SELECT * FROM renter WHERE del_flag = 0 ORDER BY create_time DESC LIMIT 10000 ) tmp GROUP BY user_id ) r
            WHERE
                u.del_flag = 0
                AND u.user_id = r.user_id
                AND u.phone = r.phone;
         */

        List<String> list = FileUtils.localFileToList("/Users/zhuweijie/filehome/上汽/常用租车人.txt");

        List<String> userIds = new ArrayList<>();
        list.forEach(e->{
            String[] split = e.split("\t");

            userIds.add(split[0]);
        });

        System.out.println(StringUtils.join(userIds,","));
    }
}
