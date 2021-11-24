package com.xyz.study.common.work;

import com.xyz.study.common.utils.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Zhu WeiJie
 * @date 2021/9/3
 **/
public class Month9Task_1 {

    /**
     * 根据上一步跑出的用户id查出card情况 然后一并处理
     */
    public static void main(String[] args) {
        /*
        select user_id,user_action_flag
        from idcard_info
        where del_flag=0
        and user_id in (
        )
         */

        List<String> renters = FileUtils.localFileToList("/Users/zhuweijie/filehome/上汽/常用租车人.txt");
        List<String> cards = FileUtils.localFileToList("/Users/zhuweijie/filehome/上汽/card信息.txt");

        Map<String, String> cardCondition = new HashMap<>();
        cards.forEach(e -> {
            String[] split = e.split("\t");
            cardCondition.put(split[0], split[1]);
        });


        String in = "INSERT INTO `idcard_info`(`id`, `user_id`, `uuid`, `maxus_uuid`, `first_name`, `last_name`, `name`, `idcard_type`, `card_num`, `expiring_date`, `home_img`, `sub_img`, `user_action_flag`, `create_time`, `modify_time`, `del_flag`) " +
                "VALUES (null, %s, '%s', '%s', '%s', '%s', AES_ENCRYPT('%s','saicmotor_rv'), %s, AES_ENCRYPT('%s','saicmotor_rv'), '%s', '', '', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);";
        String up = "UPDATE `idcard_info` SET `first_name` = '%s',`last_name` = '%s',`name` = AES_ENCRYPT('%s','saicmotor_rv'),`idcard_type` = %s,`card_num` = AES_ENCRYPT('%s','saicmotor_rv'),`expiring_date` = '%s' WHERE user_id = %s;";


        String birthUp = "update user_personal_info set birthday='%s' where user_id=%s;";

        List<String> inUserIds = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        StringBuilder birthdaySql = new StringBuilder();
        renters.forEach(e -> {
            String[] split = e.split("\t");

            String userActionType = cardCondition.get(split[0]);
            if (userActionType == null) {
                // 新增
                String expireDate = split[8];
                if ("永久有效".equals(expireDate)) {
                    expireDate = "1970-01-01";
                }
                sql.append(String.format(in, split[0], split[1], split[2], split[6], split[7], split[3], split[4], split[5], expireDate));
                sql.append("\n");
                inUserIds.add(split[0]);
            } else if ("0".equals(userActionType)) {
                // 修改
                String expireDate = split[8];
                if ("永久有效".equals(expireDate)) {
                    expireDate = "1970-01-01";
                }
                sql.append(String.format(up, split[6], split[7], split[3], split[4], split[5], expireDate, split[0]));
                sql.append("\n");
            } else {
                // 不处理
            }

            String join = StringUtils.join(inUserIds, ",");
            FileUtils.writeToLocal("/Users/zhuweijie/filehome/上汽", "111.txt", join);

            // 匹配身份证更新生日
            try {
                if ("1".equals(split[4]) && split[5].length() == 18) {
                    String birthday = split[5].substring(6, 14);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    Date parse = simpleDateFormat.parse(birthday);
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                    String format = simpleDateFormat1.format(parse);

                    birthdaySql.append(String.format(birthUp, format, split[0]));
                    birthdaySql.append("\n");
                }
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });

//        System.out.println(StringUtils.join(inUserIds,","));
        FileUtils.writeToLocal("/Users/zhuweijie/filehome/上汽", "card更新.sql", sql.toString());

        FileUtils.writeToLocal("/Users/zhuweijie/filehome/上汽", "birth更新.sql", birthdaySql.toString());
    }
}
