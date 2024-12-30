package com.topBalance.wishTree.service;

import com.topBalance.wishTree.dto.BalanceQ;
import com.topBalance.wishTree.dto.Bamboo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BambooChattingService {
    // service로 사용할 기능 설정

    // html로 서비스 기능을 통해 나타날 결과를 보여줄 기능들 작성

    // 모든 유저 보기 기능
    List<Map<String, Object>> getAllBambooChattings();

    void updatingBamboo(String userId, String newbamboo);

    Date test();
}
