package com.topBalance.wishTree.service;

import com.topBalance.wishTree.dto.CardType;
import com.topBalance.wishTree.vo.GameScores;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface GameResultService {

    //행운 총 점수 더한 total score
    int totalScore(GameScores gameScores);

    // 트럼프 카드 경로 가져노기
    Map<String, Object> balanceTrump(GameScores gameScores);

    // 오늘의 운세 문구 (건강,재물,연애,학업별 운세 한문장 출력)
//    String todaysLuck(CardType cardType, int cardNumber);

    // 오늘의 추천메뉴 사진+메뉴(lunchResult)출력
    String todaysLunch(CardType lunchMax, CardType lunchMin);

    void changingCardNumber(GameScores gameScores);

    int getChangingNumber(int cardNumber);

    Map<String, Object> getOldCardScores(GameScores gameScores);

    Map<String, Object> getCategoryResult(GameScores gameScores);

    CardType getMaxCategory(GameScores gameScores);

    CardType getMinCategory(GameScores gameScores);

    String getTodaysLunchPath(CardType MAX, CardType MIN);

}
