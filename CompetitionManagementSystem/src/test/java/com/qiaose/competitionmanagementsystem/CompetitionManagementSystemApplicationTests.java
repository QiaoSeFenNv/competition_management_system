package com.qiaose.competitionmanagementsystem;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@Slf4j
@SpringBootTest()
class CompetitionManagementSystemApplicationTests {




    @Test
    void contextLoads() {
        HashMap<String, Object> stringIntegerHashMap = new HashMap<>();

        stringIntegerHashMap.put("amount",4108);
        stringIntegerHashMap.put("moth",2);




    }




}
