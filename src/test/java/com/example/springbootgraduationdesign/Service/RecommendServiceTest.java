package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.service.RecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecommendServiceTest {
    @Autowired
    private RecommendService recommendService;

    @Test
    public void testJ(){
        int sid = 1;
        recommendService.getJobRecommends(sid);
    }

    @Test
    public void testR(){
        int cid = 1;
        recommendService.getResumeRecommends(cid);
    }
}
