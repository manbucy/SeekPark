package net.manbucy.seekpark.common;

/**
 * 常量
 * Created by yang on 2017/3/20.
 */

public class Constant {
    /**
     * 订单状态 已取消
     */
    public static final int ORDER_STATUS_CANCEL = 0;
    /**
     * 订单状态 正在进行中
     */
    public static final int ORDER_STATUS_ING = 1;
    /**
     * 订单状态 成功
     */
    public static final int ORDER_STATUS_SUCCESS = 2;

    /**
     * 车位类型 普通车位
     */
    public static final int PARK_TYPE_NORMAL = 0;
    /**
     * 车位类型 充电车位
     */
    public static final int PARK_TYPE_CHARGING = 1;


    /**
     * TextInputLayout 实体类型  用于显示错误信息
     */
    public class InputLayoutEntry {
        /**
         * 用户名输入框
         */
        public static final int USERNAME_INPUT = 100;
        /**
         * 密码输入框
         */
        public static final int PASSWORD_INPUT = 101;
        /**
         * 确认密码输入框
         */
        public static final int PASSWORD_AGAIN_INPUT = 102;
        /**
         * 手机号码输入框
         */
        public static final int PHONE_INPUT = 103;
        /**
         * 验证码输入框
         */
        public static final int VERIFY_INPUT = 104;
        /**
         * 停车场名称
         */
        public static final int PARK_NAME = 105;

        /**
         * 停车场地址
         */
        public static final int PARK_ADDRESS = 106;
        /**
         * 普通停车位 数量
         */
        public static final int NORAMAL_NUN = 107;
        /**
         * 普通停车位 价格
         */
        public static final int NORMAL_PRICE = 108;
        /**
         * 充电停车位 数量
         */
        public static final int CHARGING_NUM = 109;
        /**
         * 充电停车位 价格
         */
        public static final int CHARGING_PRICE = 110;
        /**
         * 备注
         */
        public static final int REMARK = 111;
        /**
         * 经纬度
         */
        public static final int LAT_LON = 112;
    }
}

