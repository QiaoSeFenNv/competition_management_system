package com.qiaose.competitionmanagementsystem.utils;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Random;

public class IDUtils {

    private static final long EPOCH = 1479533469598L; //开始时间,固定一个小于当前时间的毫秒数
    private static final int max12bit = 4095;
    private static final long max41bit = 1099511627775L;
    private static String machineId = ""; // 机器ID

    /**
     * 创建ID
     *
     * @return
     */
    public static Long CreateId() {

        long time = System.currentTimeMillis() - EPOCH + max41bit;
        // 二进制的 毫秒级时间戳
        String base = Long.toBinaryString(time);

        // 序列数
        String randomStr = StringUtils.leftPad(Integer.toBinaryString(new Random().nextInt(max12bit)), 12, '0');
        if (StringUtils.isNotEmpty(machineId)) {
            machineId = StringUtils.leftPad(machineId, 10, '0');
        }

        //拼接
        String appendStr = base + machineId + randomStr;
        // 转化为十进制 返回
        BigInteger bi = new BigInteger(appendStr, 2);

        return Long.valueOf(bi.toString());
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Integer getUUIDInOrderId() {
        Integer orderId = UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
        return orderId;
    }

    public static void main(String[] args) {

    }

}


