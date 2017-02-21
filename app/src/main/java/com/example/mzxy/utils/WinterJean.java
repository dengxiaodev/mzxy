package com.example.mzxy.utils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class WinterJean {
    /**
     * washInfo : [{"WashHead":"http://192.168.1.33:8080/demo/image10.png","WashName":"帽子","Amount":"￥5"},{"WashHead":"http://192.168.1.33:8080/demo/image11.png","WashName":"衬衣","Amount":"￥10"},{"WashHead":"http://192.168.1.33:8080/demo/image12.png","WashName":"皮衣","Amount":"￥100"},{"WashHead":"http://192.168.1.33:8080/demo/image13.png","WashName":"羽绒服","Amount":"￥80"},{"WashHead":"http://192.168.1.33:8080/demo/image14.png","WashName":"唐装","Amount":"￥50"},{"WashHead":"http://192.168.1.33:8080/demo/image15.png","WashName":"西服","Amount":"￥150"},{"WashHead":"http://192.168.1.33:8080/demo/image16.png","WashName":"长袖风衣","Amount":"￥218"}]
     */
    public List<WashInfoEntity> washInfo;
    public static class WashInfoEntity {
        /**
         * WashHead : http://192.168.1.33:8080/demo/image10.png
         * WashName : 帽子
         * Amount : ￥5
         */
        private String WashHead;
        private String WashName;
        private String Amount;

        public void setWashHead(String WashHead) {
            this.WashHead = WashHead;
        }

        public void setWashName(String WashName) {
            this.WashName = WashName;
        }

        public void setAmount(String Amount) {
            this.Amount = Amount;
        }

        public String getWashHead() {
            return WashHead;
        }

        public String getWashName() {
            return WashName;
        }

        public String getAmount() {
            return Amount;
        }
    }
}
