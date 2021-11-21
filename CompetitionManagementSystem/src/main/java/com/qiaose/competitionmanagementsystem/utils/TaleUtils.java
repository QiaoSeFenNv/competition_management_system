package com.qiaose.competitionmanagementsystem.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.qiaose.competitionmanagementsystem.controller.AttachController;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

public class TaleUtils {
    /**
     * 获取保存文件的位置,jar所在目录的路径
     *
     * @return
     */
    public static String getUplodFilePath() {
        String path = TaleUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File("");
        return file.getAbsolutePath() + "\\CompetitionManagementSystem\\src\\main\\resources\\static"+"/";
    }

    /**
     * 判断文件类型是否为图片
     * @param name
     * @return
     */
    public static String getImagesKey(String name) {
        String prefix = "/images";
        return getString(name, prefix);
    }
    /**
     * 判断文件类型是否为文件
     * @param name
     * @return
     */
    public static String getFileKey(String name) {
        String prefix = "/files";
        return getString(name, prefix);
    }

    private static String getString(String name, String prefix) {
        if (!new File(AttachController.CLASSPATH + prefix).exists()) {
            new File(AttachController.CLASSPATH + prefix).mkdirs();
        }

        name = StringUtils.trimToNull(name);
        if (name == null) {
            return prefix + "/" + UUID.randomUUID() + "." + null;
        } else {
            name = name.replace('\\', '/');
            name = name.substring(name.lastIndexOf("/") + 1);
            int index = name.lastIndexOf(".");
            String ext = null;
            if (index >= 0) {
                ext = StringUtils.trimToNull(name.substring(index + 1));
            }
            return prefix + "/" + UUID.randomUUID() + "." + (ext == null ? null : (ext));
        }
    }


    public static String filterFieldsJson(Object src, Class<?> clazz, String... args)
    {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz, args);
        return JSON.toJSONString(src, filter);
    }




}


