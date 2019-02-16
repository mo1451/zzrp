package com.zzrq.excel.service;


import com.zzrq.base.dto.ResponseData;

import java.util.List;

public interface IExcelService {
    ResponseData generateExcel(List<String> title,String mapper);
}
