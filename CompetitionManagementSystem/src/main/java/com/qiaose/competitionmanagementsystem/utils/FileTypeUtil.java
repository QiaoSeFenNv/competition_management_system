package com.qiaose.competitionmanagementsystem.utils;

public class FileTypeUtil {
    /**
     * 文档格式判断
     * @param perfix 后缀
     * @return
     */
    public static boolean fileType(String perfix){
        if(perfix.equalsIgnoreCase("txt") || perfix.equalsIgnoreCase("doc") || perfix.equalsIgnoreCase("docx")
                || perfix.equalsIgnoreCase("pdf") || perfix.equalsIgnoreCase("wps") || perfix.equalsIgnoreCase("rtf")
                || perfix.equalsIgnoreCase("pptx") || perfix.equalsIgnoreCase("ppt")
                || perfix.equalsIgnoreCase("xls") || perfix.equalsIgnoreCase("xlsx"))
            return true;
        else
            return false;
    }

    /**
     * 图片格式判断
     * @param perfix 后缀
     * @return
     */
    public static boolean imageType(String perfix) {
        if(perfix.equalsIgnoreCase("JPG") || perfix.equalsIgnoreCase("JPEG") || perfix.equalsIgnoreCase("GIF")
                || perfix.equalsIgnoreCase("PNG") || perfix.equalsIgnoreCase("BMP") || perfix.equalsIgnoreCase("PCX")
                || perfix.equalsIgnoreCase("TGA") || perfix.equalsIgnoreCase("PSD") || perfix.equalsIgnoreCase("TIFF"))
            return true;
        else
            return false;
    }


}
