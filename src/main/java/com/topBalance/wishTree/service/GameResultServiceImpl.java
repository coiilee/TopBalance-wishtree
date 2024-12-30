package com.topBalance.wishTree.service;


import com.topBalance.wishTree.dto.CardType;
import com.topBalance.wishTree.mapper.TodaysLuckMapper;
import com.topBalance.wishTree.mapper.TodaysLunchMapper;
import com.topBalance.wishTree.vo.GameScores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

// @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class GameResultServiceImpl implements GameResultService {

    @Autowired
    private TodaysLuckMapper todaysLuckMapper;

    @Autowired
    private TodaysLunchMapper todaysLunchMapper;



//    @Override
//    public String balanceTrump(int score_s, int score_c, int score_d, int score_h, MultipartFile trumpImagePath) {
//        String spadeScore = "/images/trumpcard/trumpimage/S"+ score_s + ".png";
//        String cloverScore = "/images/trumpcard/trumpimage/C"+ score_c + ".png";
//        String heartScore = "/images/trumpcard/trumpimage/H"+ score_h + ".png";
//        String diamondScore = "/images/trumpcard/trumpimage/D"+ score_d + ".png";
//        return new String[4];
//    }

    @Override
    public Map<String, Object> balanceTrump(GameScores gameScores) {
        Map<String, Object> pathMap = new HashMap<>();
        pathMap.put("spadePath","images/trumpcard/trumpimage/S" + gameScores.getSpadeScore() + ".png");
        pathMap.put("cloverPath", "/images/trumpcard/trumpimage/C" + gameScores.getCloverScore() + ".png");
        pathMap.put("heartPath", "/images/trumpcard/trumpimage/H" + gameScores.getHeartScore() + ".png");
        pathMap.put("diamondPath", "/images/trumpcard/trumpimage/D" + gameScores.getDiamondScore() + ".png");
        return pathMap;
    }


    @Override
    public String todaysLuck(CardType cardType, int cardNumber) {
        String result = todaysLuckMapper.todaysLuck(cardType, cardNumber);
        return result;
    }

    @Override
   public String todaysLunch(CardType lunchMax, CardType lunchMin) {
        return todaysLunchMapper.todaysLunch(lunchMax, lunchMin);
    }



    @Override
    public int totalScore(GameScores gameScores) {
        return gameScores.getSpadeScore() + gameScores.getCloverScore() + gameScores.getHeartScore() + gameScores.getDiamondScore();
    }

    @Override
    public void changingCardNumber(GameScores gameScores) {
        gameScores.setSpadeScore(getChaingNumber(gameScores.getSpadeScore()));
        gameScores.setCloverScore(getChaingNumber(gameScores.getCloverScore()));
        gameScores.setHeartScore(getChaingNumber(gameScores.getHeartScore()));
        gameScores.setDiamondScore(getChaingNumber(gameScores.getDiamondScore()));
    }

    @Override
    public int getChaingNumber(int cardNumber) {
        if (cardNumber == 25) return 1;
        if (cardNumber >= 0 && cardNumber <= 2) return 2;
        if (cardNumber == 3 || cardNumber == 4) return 3;
        if (cardNumber == 5 || cardNumber == 6) return 4;
        if (cardNumber == 7 || cardNumber == 8) return 5;
        if (cardNumber == 9 || cardNumber == 10) return 6;
        if (cardNumber == 11 || cardNumber == 12) return 7;
        if (cardNumber == 13 || cardNumber == 14) return 8;
        if (cardNumber == 15 || cardNumber == 16) return 9;
        if (cardNumber == 17 || cardNumber == 18) return 10;
        if (cardNumber == 19 || cardNumber == 20) return 11;
        if (cardNumber == 21 || cardNumber == 22) return 12;
        if (cardNumber == 23 || cardNumber == 24) return 13;
        return 0;
    }

    @Override
    public Map<String, Object> getOldCardScores(GameScores gamescores) {
        Map<String, Object> categoryScore = new HashMap<>();
        categoryScore.put("spadeScore", gamescores.getSpadeScore());
        categoryScore.put("cloverScore", gamescores.getCloverScore());
        categoryScore.put("heartScore", gamescores.getHeartScore());
        categoryScore.put("diamondScore", gamescores.getDiamondScore());
        return categoryScore;
    }

    @Override
    public Map<String, Object> getCategoryResult(GameScores gameScores) {
        Map<String, Object> categoryResult = new HashMap<>();
        categoryResult.put("spadeResult", todaysLuckMapper.todaysLuck(CardType.SPADE, gameScores.getSpadeScore()));
        categoryResult.put("cloverResult", todaysLuckMapper.todaysLuck(CardType.CLOVER, gameScores.getCloverScore()));
        categoryResult.put("heartResult", todaysLuckMapper.todaysLuck(CardType.HEART, gameScores.getHeartScore()));
        categoryResult.put("diamondResult", todaysLuckMapper.todaysLuck(CardType.DIAMOND, gameScores.getDiamondScore()));

        return categoryResult;
    }

    @Override
    public CardType getMinCategory(GameScores gameScores) {
        Map<Integer, CardType> allScores = new HashMap<>();
        allScores.put(gameScores.getSpadeScore(), CardType.SPADE);
        allScores.put(gameScores.getCloverScore() , CardType.CLOVER);
        allScores.put(gameScores.getHeartScore(), CardType.HEART);
        allScores.put(gameScores.getDiamondScore(), CardType.DIAMOND);

        int MaxValue = Math.max(Math.max(gameScores.getSpadeScore(), gameScores.getCloverScore()), Math.max(gameScores.getHeartScore(), gameScores.getDiamondScore()));

        return allScores.get(MaxValue);
    }

    @Override
    public CardType getMaxCategory(GameScores gameScores) {
        Map<Integer, CardType> allScores = new HashMap<>();
        allScores.put(gameScores.getSpadeScore(), CardType.SPADE);
        allScores.put(gameScores.getCloverScore() , CardType.CLOVER);
        allScores.put(gameScores.getHeartScore(), CardType.HEART);
        allScores.put(gameScores.getDiamondScore(), CardType.DIAMOND);

        int MinValue = Math.min(Math.min(gameScores.getSpadeScore(), gameScores.getCloverScore()), Math.min(gameScores.getHeartScore(), gameScores.getDiamondScore()));

        return allScores.get(MinValue);
    }
}
