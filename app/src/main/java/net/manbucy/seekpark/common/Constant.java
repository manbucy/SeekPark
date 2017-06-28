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
        public static final int USERNAME_INPUT = 0;
        /**
         * 密码输入框
         */
        public static final int PASSWORD_INPUT = 1;
        /**
         * 确认密码输入框
         */
        public static final int PASSWORD_AGAIN_INPUT = 2;
        /**
         * 手机号码输入框
         */
        public static final int PHONE_INPUT = 3;
        /**
         * 验证码输入框
         */
        public static final int VERIFY_INPUT = 4;

    }
}

