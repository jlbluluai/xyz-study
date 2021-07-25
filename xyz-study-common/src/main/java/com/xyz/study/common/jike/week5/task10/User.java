package com.xyz.study.common.jike.week5.task10;

import lombok.Data;

/**
 * @author Zhu WeiJie
 * @date 2021/7/25
 **/
@Data
public class User {

    /**
     * id
     */
    private long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别 M/W
     */
    private String sex;
}
