package com.topBalance.wishTree.service;

import com.topBalance.wishTree.dto.BalanceQ;
import com.topBalance.wishTree.dto.Bamboo;
import com.topBalance.wishTree.mapper.BambooChattingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BambooChattingServiceImpl implements BambooChattingService {

    @Autowired
    private BambooChattingMapper bambooChattingMapper;

    @Override
    public List<Map<String, Object>> getAllBambooChattings() {
        List<Bamboo> BambooList = bambooChattingMapper.getAllBamboos();
        Logger logger = LoggerFactory.getLogger(BambooChattingServiceImpl.class);
        return BambooList.stream().map(bamboo -> {
            logger.info("userId : " + bamboo.getUserId());
            logger.info("comment : " + bamboo.getComment());
            logger.info("commentDate : " + bamboo.getCommentDate());
            Map<String, Object> bambooMap = new HashMap<>();
            bambooMap.put("userId", bamboo.getUserId());
            bambooMap.put("comment", bamboo.getComment());
            bambooMap.put("commentDate", bamboo.getCommentDate());
            return bambooMap;
        }).collect(Collectors.toList());
    }

    @Override
    public void updatingBamboo(String userId, String newbamboo) {
        List<Bamboo> BambooList = bambooChattingMapper.getAllBamboos();

        if (BambooList.size() == 10) {
            bambooChattingMapper.deleteRecentBamboo();
        }
        Bamboo bamboo = new Bamboo(userId, newbamboo);
        bambooChattingMapper.insertBamboo(bamboo);
    }

    @Override
    public Date test() {
        return bambooChattingMapper.test();
    }
}
