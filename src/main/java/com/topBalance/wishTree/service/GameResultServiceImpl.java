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

    //게임 스코어 총합
    @Override
    public int totalScore(GameScores gameScores) {
        return gameScores.getSpadeScore() + gameScores.getCloverScore() + gameScores.getHeartScore() + gameScores.getDiamondScore();
    }

    //트럼프카드 사진 경로 점수대로 가져오는 설정
    @Override
    public Map<String, Object> balanceTrump(GameScores gameScores) {
        Map<String, Object> pathMap = new HashMap<>();
        pathMap.put("spadePath","images/trumpcard/trumpimage/S" + gameScores.getSpadeScore() + ".png");
        pathMap.put("cloverPath", "/images/trumpcard/trumpimage/C" + gameScores.getCloverScore() + ".png");
        pathMap.put("heartPath", "/images/trumpcard/trumpimage/H" + gameScores.getHeartScore() + ".png");
        pathMap.put("diamondPath", "/images/trumpcard/trumpimage/D" + gameScores.getDiamondScore() + ".png");
        return pathMap;
    }

//    @Override
//    public String todaysLuck(CardType cardType, int cardNumber) {
//        return "";
//    }

    //게임 결과로 나온 점수 카테고리(재물,연애,학업,건강)별로 불러오기
    @Override
    public Map<String, Object> getOldCardScores(GameScores gamescores) {
        Map<String, Object> categoryScore = new HashMap<>();
        categoryScore.put("spadeScore", gamescores.getSpadeScore());
        categoryScore.put("cloverScore", gamescores.getCloverScore());
        categoryScore.put("heartScore", gamescores.getHeartScore());
        categoryScore.put("diamondScore", gamescores.getDiamondScore());
        return categoryScore;
    }

//    @Override
//    public String todaysLuck(CardType cardType, int cardNumber) {
//        String result = todaysLuckMapper.todaysLuck(cardType, cardNumber);
//        return result;
//    }

    // 게임결과 점수는 25점까지이기 때문에, 점수를 카드점수로(13까지)로 변환해주는 기능
    @Override
    public int getChangingNumber(int cardNumber) {
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

    // 카테고리별로 운세 한문장 출력하기
    @Override
    public Map<String, Object> getCategoryResult(GameScores gameScores) {
        Map<String, Object> categoryResult = new HashMap<>();
        categoryResult.put("spadeResult", todaysLuckMapper.todaysLuck(CardType.SPADE, gameScores.getSpadeScore()));
        categoryResult.put("cloverResult", todaysLuckMapper.todaysLuck(CardType.CLOVER, gameScores.getCloverScore()));
        categoryResult.put("heartResult", todaysLuckMapper.todaysLuck(CardType.HEART, gameScores.getHeartScore()));
        categoryResult.put("diamondResult", todaysLuckMapper.todaysLuck(CardType.DIAMOND, gameScores.getDiamondScore()));

        return categoryResult;
    }

    //todayslunch 오늘의 추천메뉴 max, min
    @Override
    public String todaysLunch(CardType lunchMax, CardType lunchMin) {
        return todaysLunchMapper.todaysLunch(lunchMax, lunchMin);
    }
    //게임점수인 gameScores.getSpadeScore()를 카드숫자로 변환(getChangingNumber)하고,
    //변환된 값은 set 으로 다시 저장
    @Override
    public void changingCardNumber(GameScores gameScores) {
        gameScores.setSpadeScore(getChangingNumber(gameScores.getSpadeScore()));
        gameScores.setCloverScore(getChangingNumber(gameScores.getCloverScore()));
        gameScores.setHeartScore(getChangingNumber(gameScores.getHeartScore()));
        gameScores.setDiamondScore(getChangingNumber(gameScores.getDiamondScore()));
    }

    @Override
    public CardType getMinCategory(GameScores gameScores) {
        Map<Integer, CardType> allScores = new HashMap<>();
        allScores.put(gameScores.getSpadeScore(), CardType.SPADE);
        allScores.put(gameScores.getCloverScore() , CardType.CLOVER);
        allScores.put(gameScores.getHeartScore(), CardType.HEART);
        allScores.put(gameScores.getDiamondScore(), CardType.DIAMOND);

        int MinValue = Math.min(Math.min(gameScores.getSpadeScore(), gameScores.getCloverScore()), Math.min(gameScores.getHeartScore(), gameScores.getDiamondScore()));
        System.out.println("Min : " + MinValue);
        return allScores.get(MinValue);

//        Map<Integer, CardType> treemap = new TreeMap<>((o1, o2) -> o1.compareTo(o2)); //내림차순
//        treemap.put(gameScores.getSpadeScore(), CardType.SPADE);
//        treemap.put(gameScores.getCloverScore(), CardType.CLOVER);
//        treemap.put(gameScores.getHeartScore(), CardType.HEART);
//        treemap.put(gameScores.getDiamondScore(), CardType.DIAMOND);
//
//        Map.Entry<Integer, CardType> minCardType = treemap.entrySet().iterator().next();
//        System.out.println("Min : " + minCardType.getValue());
//        return minCardType.getValue();
    }


    @Override
    public CardType getMaxCategory(GameScores gameScores) {
        Map<Integer, CardType> allScores = new HashMap<>();
        allScores.put(gameScores.getSpadeScore(), CardType.SPADE);
        allScores.put(gameScores.getCloverScore() , CardType.CLOVER);
        allScores.put(gameScores.getHeartScore(), CardType.HEART);
        allScores.put(gameScores.getDiamondScore(), CardType.DIAMOND);

        int MaxValue = Math.max(Math.max(gameScores.getSpadeScore(), gameScores.getCloverScore()), Math.max(gameScores.getHeartScore(), gameScores.getDiamondScore()));
        System.out.println("Max : " + MaxValue);
        return allScores.get(MaxValue);
//        Map<Integer, CardType> treemap = new TreeMap<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2.compareTo(o1); // 올림차순
//            }
//        });
//        treemap.put(gameScores.getSpadeScore(), CardType.SPADE);
//        treemap.put(gameScores.getCloverScore(), CardType.CLOVER);
//        treemap.put(gameScores.getHeartScore(), CardType.HEART);
//        treemap.put(gameScores.getDiamondScore(), CardType.DIAMOND);
//
//        Map.Entry<Integer, CardType> minCardType = treemap.entrySet().iterator().next();
//        System.out.println("Max : " + minCardType.getValue());
//        return minCardType.getValue();
    }

    @Override
    public String getTodaysLunchPath(CardType MAX, CardType MIN) {
        String answer = "/images/lunchmenu/";
        if (MAX.equals(CardType.SPADE)) {
            if (MIN.equals(CardType.CLOVER)) answer += "yakinikku.jpg";
            if (MIN.equals(CardType.HEART)) answer += "jjajangmyeon.jpg";
            if (MIN.equals(CardType.DIAMOND)) answer += "kimbap.jpg";
        }
        if (MAX.equals(CardType.CLOVER)) {
            if (MIN.equals(CardType.SPADE)) answer += "godeungeojorim.jpg";
            if (MIN.equals(CardType.HEART)) answer += "gookbap.jpg";
            if (MIN.equals(CardType.DIAMOND)) answer += "cupramen.jpg";
        }
        if (MAX.equals(CardType.HEART)) {
            if (MIN.equals(CardType.SPADE)) answer += "bibimbob.jpg";
            if (MIN.equals(CardType.CLOVER)) answer += "gomtang.jpg";
            if (MIN.equals(CardType.DIAMOND)) answer += "tteokbokki.jpg";
        }
        if (MAX.equals(CardType.DIAMOND)) {
            if (MIN.equals(CardType.SPADE)) answer += "grilledduck.jpg";
            if (MIN.equals(CardType.CLOVER)) answer += "samgyetang.jpeg";
            if (MIN.equals(CardType.HEART)) answer += "sushi.jpg";
        }

        return answer;
    }

}