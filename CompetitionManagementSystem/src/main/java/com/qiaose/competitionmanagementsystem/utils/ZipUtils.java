package com.qiaose.competitionmanagementsystem.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip压缩工具类
 */
public class ZipUtils {

    /**
     * zipFolder 压缩文件夹
     * srcFolder 源文件夹
     * destZipFile 目标文件
     */
    public static void zipFolder(String srcFolder, String destZipFile) throws Exception {
        //zip输出流
        ZipOutputStream zip = null;
        //文件输出流
        FileOutputStream fileWriter = null;
        //先压缩文件
        fileWriter = new FileOutputStream(destZipFile);
        //在压缩文件夹
        zip = new ZipOutputStream(fileWriter);
        //开始进行压缩
        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
    }

    /**
     * zipFile 压缩文件
     * @param filePath   要压缩的文件（附带路径）
     * @param zipPath    输出文件的名称（附带路径）
     * @throws Exception
     */
    public static void zipFile(String filePath, String zipPath) throws Exception{
        //创建buffer进行压缩
        byte[] buffer = new byte[1024];
        //文件输出指定位置
        FileOutputStream fos = new FileOutputStream(zipPath);
        //将file流进行zip压缩
        ZipOutputStream zos = new ZipOutputStream(fos);
        //设置解压名称
        ZipEntry ze= new ZipEntry("spy.log");
        //进行压缩条码设置
        zos.putNextEntry(ze);
        //输入流将要压缩的文件输入
        FileInputStream in = new FileInputStream(filePath);
        int len;
        while ((len = in.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
        }
        in.close();
        zos.closeEntry();
        //remember close it
        zos.close();
        fos.close();
        in.close();
    }

    public static void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    /**
     * addFolderToZip 开始文件夹过程
     * @param path  存放路径
     * @param srcFolder 源文件夹
     * @param zip   zip流对象里面存放了目标文件
     * @throws Exception
     */
    public static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        //创建文件对象
        File folder = new File(srcFolder);
        if (null != path && folder.isDirectory()) {
            for (String fileName : folder.list()) {
                if (path.equals("")) {
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
                } else {
                    addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
                }
            }
        }
    }

    public static void main(String[] args) {

    }

} 