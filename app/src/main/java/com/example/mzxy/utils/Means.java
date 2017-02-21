package com.example.mzxy.utils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class Means {

    public List<MeansInfoBean> MeansInfo;

    public List<MeansInfoBean> getMeansInfo() {
        return MeansInfo;
    }

    public void setMeansInfo(List<MeansInfoBean> MeansInfo) {
        this.MeansInfo = MeansInfo;
    }

    public static class MeansInfoBean {
        /**
         * ImageMeans : http://file.bmob.cn/M01/9E/7D/oYYBAFbFeKWAUU69AAAIRGjHmVM514.png
         * TextMeans : 邀请好友，您和您的好友各得30元洗衣优惠劵礼包；
         */

        private String ImageMeans;
        private String TextMeans;

        public String getImageMeans() {
            return ImageMeans;
        }

        public void setImageMeans(String ImageMeans) {
            this.ImageMeans = ImageMeans;
        }

        public String getTextMeans() {
            return TextMeans;
        }

        public void setTextMeans(String TextMeans) {
            this.TextMeans = TextMeans;
        }
    }
}
