package com.hunter.helloandroid.module.update.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/2 11:00
 * <p>
 * 描    述：操作文件的工具集
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class FileUtil {

    /**
     * 检查文件是否有效
     */
    public static boolean checkFile(String fileDir, String fileName) {
        File file = new File(fileDir + fileName);
        return file.exists();
    }


    /**
     * 读取data/data/包名/files 文件夹中的文件
     */
    public static String readFilesData(Context context, String fileName) throws IOException {
        FileInputStream inStream = context.openFileInput(fileName);//只需传文件名
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();//输出到内存

        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }

        byte[] content_byte = outStream.toByteArray();
        return new String(content_byte);
    }

    /**
     * 复制Assets中的文件到/data/data/包名 下面
     */
    public static void copyAssetFileToFiles(Context context, String filename)
            throws IOException {
        InputStream is = context.getAssets().open(filename);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        File of = new File(context.getFilesDir() + "/" + filename);
        of.createNewFile();
        FileOutputStream os = new FileOutputStream(of);
        os.write(buffer);
        os.close();
    }

    public static String openAssetFile(Context context, String fileName) throws IOException {
        InputStream is = context.getAssets().open(fileName);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        return new String(buffer);
    }

    /**
     * 复制Assets中的文件
     */
    public static void copyAssetFile(Context context, String fileDir, String fileName)
            throws IOException {
        InputStream is = context.getAssets().open(fileName);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        File of = new File(fileDir, fileName);
        of.createNewFile();
        FileOutputStream os = new FileOutputStream(of);
        os.write(buffer);
        os.close();
    }

    /**
     * 描述：追加内容到文件末尾
     */
    public static void appendText(String fileName, String content, String charsetName) {
        WriteStreamAppend.appendText(fileName, content, charsetName);
    }

    public static void appendText(File file, String content) {
        WriteStreamAppend.appendText(file, content);
    }

    public static void appendText(String fileName, String content) {
        WriteStreamAppend.appendText(fileName, content);
    }


    /**
     * 描述：追加内容到文件末尾
     */
    public static class WriteStreamAppend {
        /**
         * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
         */
        public static void appendText(String fileName, String content, String charsetName) {
            BufferedWriter out = null;
            try {
                //如果文件夹不存在则先创建
                String folderPath = getFolderPath(fileName);
                File folderFile = new File(folderPath);
                if (!folderFile.exists()) {
                    boolean mkdirs = folderFile.mkdirs();
                }

                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), charsetName));
                out.write(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 追加文件：使用FileWriter
         */
        public static void appendText(File file, String content) {
            try {
                //如果文件夹不存在则先创建
                String folderPath = getFolderPath(file.getAbsolutePath());
                File folderFile = new File(folderPath);
                if (!folderFile.exists()) {
                    boolean mkdirs = folderFile.mkdirs();
                }

                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                FileWriter writer = new FileWriter(file, true);
                writer.write(content);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 追加文件：使用RandomAccessFile
         *
         * @param fileName 文件名
         * @param content  追加的内容
         */
        public static void appendText(String fileName, String content) {

            RandomAccessFile randomFile = null;
            try {
                //如果文件夹不存在则先创建
                String folderPath = getFolderPath(fileName);
                File folderFile = new File(folderPath);
                if (!folderFile.exists()) {
                    boolean mkdirs = folderFile.mkdirs();
                }

                // 打开一个随机访问文件流，按读写方式
                randomFile = new RandomAccessFile(fileName, "rw");
                // 文件长度，字节数
                long fileLength = randomFile.length();
                // 将写文件指针移到文件尾。
                randomFile.seek(fileLength);
//                randomFile.writeBytes(content);
                randomFile.write(content.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (randomFile != null) {
                        randomFile.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 从路径中获取文件名
     */
    public static String getFileName(String pathAndName) {
        String result = null;
        if (!TextUtils.isEmpty(pathAndName)) {
            int start = pathAndName.lastIndexOf("/");
            int end = pathAndName.lastIndexOf(".");
            if (start != -1 && end != -1) {
                result = pathAndName.substring(start + 1, end);
            }
        }
        return result;
    }

    /**
     * 获取文件夹路径
     */
    public static String getFolderPath(String pathAndName) {
        String result = null;
        if (!TextUtils.isEmpty(pathAndName)) {
            int end = pathAndName.lastIndexOf("/");
            if (end != -1) {
                result = pathAndName.substring(0, end);
            }
        }
        return result;
    }


}
