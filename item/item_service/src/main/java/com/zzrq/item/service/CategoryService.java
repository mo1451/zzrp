package com.zzrq.item.service;

import com.zzrq.item.dto.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryListByParent(Long pid);
}
