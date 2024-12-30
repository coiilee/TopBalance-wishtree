package com.topBalance.wishTree.mapper;

import com.topBalance.wishTree.dto.CardType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TodaysLunchMapper {

    // 오늘의 추천메뉴 사진+메뉴(lunch_result)출력
    String todaysLunch(CardType lunchMax, CardType lunchMin);

}
