package com.qiaose.competitionmanagementsystem.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.sql.Array;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        Boolean belong = isBelong(new Date(), "yyyy-MM-dd", "2022-03-01","2022-03-03");
        System.out.println(belong);
    }
    //判断时间是否在一个范围内
    public static Boolean isBelong(Date date,String format,String startTimeStr,String endTimeStr){
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date beginTime = null;
        Date endTime = null;
        try {
            date = df.parse(df.format(date));
            beginTime = df.parse(startTimeStr);
            endTime = df.parse(endTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar dateC = Calendar.getInstance();
        dateC.setTime(date);

        Calendar beginC = Calendar.getInstance();
        beginC.setTime(beginTime);

        Calendar endC = Calendar.getInstance();
        endC.setTime(endTime);


        if (dateC.after(beginC) && dateC.before(endC)) {
            return true;
        } else {
            return false;
        }
    }
}


