package com.topBalance.wishTree.mapper;

import com.topBalance.wishTree.dto.WishTree;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WishTreeMapper {

    //소원 한줄 입력받기
    void insertWish(WishTree wishTree);


}
