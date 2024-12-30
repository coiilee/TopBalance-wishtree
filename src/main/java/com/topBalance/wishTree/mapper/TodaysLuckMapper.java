package com.topBalance.wishTree.mapper;

import com.topBalance.wishTree.dto.CardType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodaysLuckMapper{


    // 트럼프 카드 + 운세점수 출력 - score_s / score_c / score_h /score_d
    String balanceTrump(int scoreS, int scoreC, int scoreD, int scoreH);

    // 오늘의 운세 문구 (건강,재물,연애,학업별 문장 출력)
    String todaysLuck(CardType cardType, int cardNumber);









}




