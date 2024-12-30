package com.topBalance.wishTree.mapper;

import com.topBalance.wishTree.dto.BalanceQ;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BalanceQuestionMapper {
    List<BalanceQ> getAllBalanceQs();

    List<Integer> getScores(String answer);
}
