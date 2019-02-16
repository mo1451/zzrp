package com.zzrq.excel.service.impl;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.excel.service.IExcelService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServiceImpl implements IExcelService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public ResponseData generateExcel(List<String> title, String mapper) {
        SqlSession session = sqlSessionFactory.openSession();
        List<Object> list = session.selectList(mapper);
        List<List<String>> valueList = new ArrayList<>();
        for (Object t : list) {
            try {
                Map<String, String> map = BeanUtils.describe(t);
                List<String> values = new ArrayList<>();
                for (String s : title) {
                    values.add(map.get(s));
                }
                valueList.add(values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        session.close();

        return null;
    }

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param valueList 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    private HSSFWorkbook getHSSFWorkbook(String sheetName, List<String> title, List<List<String>> valueList, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < valueList.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < valueList.get(i).size(); j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(valueList.get(i).get(j));
            }
        }
        return wb;
    }

}
