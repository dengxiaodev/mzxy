package com.example.mzxy.utils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class ListUtils {

    public List<UserInfoBean> userInfo;

    public List<UserInfoBean> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfoBean> userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * Head : http://file.bmob.cn/M01/98/85/oYYBAFbC3l-AatI0AACHHOXwnRY037.png
         * name : 布鲁克
         * signature : 我要成为一个音乐家
         */

        private String Head;
        private String name;
        private String signature;

        public String getHead() {
            return Head;
        }

        public void setHead(String Head) {
            this.Head = Head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
