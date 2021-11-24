package com.xyz.study.common.work;

import com.xyz.study.common.utils.FileUtils;

import java.util.List;

/**
 * @author Zhu WeiJie
 * @date 2021/9/23
 **/
public class Main {

    public static void main(String[] args) {
        String sql = "update idcard_info set del_flag=1 where user_id = %s order by id asc limit 1;";

        List<String> s = FileUtils.localFileToList("/Users/zhuweijie/filehome/上汽/111.txt");

        StringBuilder v = new StringBuilder();
        for (String s1 : s) {
            v.append(String.format(sql,s1)).append("\n");
        }

        FileUtils.writeToLocal("/Users/zhuweijie/filehome/上汽", "222.txt", v.toString());
    }
}
