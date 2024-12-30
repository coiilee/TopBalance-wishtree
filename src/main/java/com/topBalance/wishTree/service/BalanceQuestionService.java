package com.topBalance.wishTree.service;

import com.topBalance.wishTree.dto.BalanceQ;
import com.topBalance.wishTree.vo.GameScores;

import java.util.List;
import java.util.Map;

public interface BalanceQuestionService {
    List<Map<String, Object>> getAllBalanceQuestion();

    Map<Integer, List<BalanceQ>> getGroupedQuestions();

    void calculatingScores(Map<String, String> userAnswers, GameScores gameScores);
}
