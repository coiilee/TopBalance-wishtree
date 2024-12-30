package com.topBalance.wishTree.service;

import com.topBalance.wishTree.dto.BalanceQ;
import com.topBalance.wishTree.vo.GameScores;
import com.topBalance.wishTree.mapper.BalanceQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BalanceQuestionServiceImpl implements BalanceQuestionService{
    // 밸런스 게임 초기화
    private GameScores gameScores;
    // BalanceQuestionMapper 초기화
    @Autowired
    private BalanceQuestionMapper balanceQuestionMapper;

    /**
     * 밸런스게임 DB 전체 조회
     * @return 밸런스게임 DB 전체 조회
     */
    @Override
    public List<Map<String, Object>> getAllBalanceQuestion() {
        List<BalanceQ> balanceQList = balanceQuestionMapper.getAllBalanceQs();
        return balanceQList.stream().map(balanceQ -> {
            Map<String, Object> balanceQMap = new HashMap<>();
            balanceQMap.put("index", balanceQ.getIndex());
            balanceQMap.put("questionNumber", balanceQ.getQuestionNumber());
            balanceQMap.put("answerLeft", balanceQ.getAnswerLeft());
            balanceQMap.put("scoreS", balanceQ.getScoreS());
            balanceQMap.put("scoreC", balanceQ.getScoreC());
            balanceQMap.put("scoreH", balanceQ.getScoreH());
            balanceQMap.put("scoreD", balanceQ.getScoreD());
            balanceQMap.put("problem", balanceQ.getProblem());
            balanceQMap.put("answer", balanceQ.getAnswer());
            return balanceQMap;
        }).collect(Collectors.toList());
    }


    /**
     * 질문을 questionNumber 기준으로 questionNumber 가 동일한 번호 별로 그룹화하여 반환
     * @return 질문 번호(questionNumber)를 키로 하고, 해당 질문 리스트를 값으로 갖는 Map 객체
     */
    @Override
    public Map<Integer, List<BalanceQ>> getGroupedQuestions() {
        // 모든 질문을 데이터베이스에서 조회
        List<BalanceQ> questions = balanceQuestionMapper.getAllBalanceQs();

        // questionNumber 기준으로 질문을 그룹화하여 반환
        return questions.stream()
                .collect(Collectors.groupingBy(BalanceQ::getQuestionNumber));
    }


    @Override
    public void calculatingScores(Map<String, String> userAnswers, GameScores gameScores) {
        // 모든 질문을 데이터베이스에서 조회
        List<BalanceQ> questions = balanceQuestionMapper.getAllBalanceQs();
        int index = 0;
        Logger log = LoggerFactory.getLogger(BalanceQuestionServiceImpl.class);
        // 사용자 답변 기반으로 점수 계산
        for (Map.Entry<String, String> entry : userAnswers.entrySet()) {
            // 11번째 값이 자동으로 들어오는 문제가 있어 11번째(null) 값이 들어왔을 때 종료하는 구문
            if (entry.getValue() == null) {
                break;
            }
            log.info("index : " + ++index);
            log.info("Map entry.key = " + entry.getKey() + ", value = " + entry.getValue() );
            // "userAnswer_1" 형식의 키에서 질문 번호를 추출
            int questionNumber = Integer.parseInt(entry.getKey().replace("userAnswer_", ""));

            // 사용자가 선택한 답변
            String answer = entry.getValue();

            // 질문 번호에 해당하는 질문 찾기
            BalanceQ selectedQuestion = questions.stream()
                    .filter(q -> q.getAnswerLeft().equals(entry.getValue()) && q.getQuestionNumber() == questionNumber)
                    .findFirst()
                    .orElse(null); // 질문이 없으면 null 반환

            if (selectedQuestion != null) {
                // 점수 계산 (사용자가 선택한 답변에 따라 점수를 누적)
                gameScores.setSpadeScore(gameScores.getSpadeScore() + selectedQuestion.getScoreS());
                gameScores.setCloverScore(gameScores.getCloverScore() + selectedQuestion.getScoreC());
                gameScores.setHeartScore(gameScores.getHeartScore() + selectedQuestion.getScoreH());
                gameScores.setDiamondScore(gameScores.getDiamondScore() + selectedQuestion.getScoreD());
            }
            log.info("BalanceQuestion : " + questionNumber);
            log.info("BalanceInfo : " + selectedQuestion);
            log.info("selectionQuestion : " + selectedQuestion.getScoreS() + ", " + selectedQuestion.getScoreC() + ", " + selectedQuestion.getScoreH() + ", " + selectedQuestion.getScoreD());
            log.info("gamescore : " + gameScores.getSpadeScore() + ", " + gameScores.getCloverScore() + ", " + gameScores.getHeartScore() + ", " + gameScores.getDiamondScore());
        }
    }
}
