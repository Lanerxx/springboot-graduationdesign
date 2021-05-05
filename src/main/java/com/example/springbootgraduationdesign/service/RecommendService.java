package com.example.springbootgraduationdesign.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class RecommendService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StudentService studentService;
}
