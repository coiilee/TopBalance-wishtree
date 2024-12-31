package com.topBalance.wishTree.mapper;

import com.topBalance.wishTree.dto.CardType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodaysLuckMapper{


    // 오늘의 운세 문구 (건강,재물,연애,학업별 문장 출력)
    String todaysLuck(CardType cardType, int cardNumber);









}




