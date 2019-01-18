package com.zzrq.item.service.impl;

import com.zzrq.item.dto.Category;
import com.zzrq.item.mapper.CategoryMapper;
import com.zzrq.item.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Cacheable(value = "categories",key = "#pid")
    public List<Category> queryListByParent(Long pid) {
        System.out.println("test-----------------------");
        Category category = new Category();
        category.setParentId(pid);
        return this.categoryMapper.select(category);
    }
}
