package com.topBalance.wishTree.service;

import com.topBalance.wishTree.dto.CardType;
import com.topBalance.wishTree.vo.GameScores;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface GameResultService {


        // 트럼프 카드 + 운세점수 출력 - score_s / score_c / score_h /score_d
//    String balanceTrump(int score_s, int score_c, int score_d, int score_h, MultipartFile trumpImagePath);
    List<Map<String, Object>> balanceTrump(GameScores gameScores);

    // 오늘의 운세 문구 (건강,재물,연애,학업별 문장 출력)
    String todaysLuck(CardType cardType, int cardNumber);

    // 오늘의 추천메뉴 사진+메뉴(lunchResult)출력
    String todaysLunch(CardType lunchMax, CardType lunchMin);

    int totalScore(GameScores gameScores);

    void changingCardNumber(GameScores gameScores);

    int getChaingNumber(int cardNumber);
}
