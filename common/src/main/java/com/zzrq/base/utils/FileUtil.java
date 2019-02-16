package com.zzrq.base.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    public static List<String> readFromFile(String path) {
        List<String> stringList = new ArrayList<>();
        try (FileReader reader = new FileReader(path);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                String[] inputdatas = line.split(" ");
                for (String inputdata : inputdatas) {
                    stringList.add(inputdata);
                }
                for (String s : inputdatas) {
                    System.out.print("'" + s + "' ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }


    static void writeToFile(String path, String data) {
        try {
            File writeName = new File(path); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!writeName.getParentFile().exists()) {
                writeName.getParentFile().mkdirs();
            }
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(data);
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
