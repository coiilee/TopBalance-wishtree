package com.topBalance.wishTree.controller;

import com.topBalance.wishTree.dto.BalanceQ;
import com.topBalance.wishTree.dto.CardType;
import com.topBalance.wishTree.dto.User;
import com.topBalance.wishTree.dto.WishTree;
import com.topBalance.wishTree.service.BalanceQuestionService;
import com.topBalance.wishTree.service.GameResultService;
import com.topBalance.wishTree.service.WishTreeService;
import com.topBalance.wishTree.service.WishTreeServiceImpl;
import com.topBalance.wishTree.vo.GameScores;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class BalanceGameController {

    private GameScores gamescores;

    @Autowired
    private BalanceQuestionService balanceQuestionService;

    @Autowired
    private GameResultService gameResultService;

    @Autowired
    private WishTreeService wishTreeService;

    /**
     * balancegame 시작시 필요한 초기 설정 진행
     * 1. GameScores 초기화
     * 2. BalanceQ를 DB에서 불러와 model에 데이터 저장 및 /balancegame에 랜더링.
     */
    @GetMapping("/balancegame")
    public void balanceGame(Model model) {
        gamescores = new GameScores();
        Map<Integer, List<BalanceQ>> groupedQuestions = balanceQuestionService.getGroupedQuestions();
        model.addAttribute("groupedQuestions", groupedQuestions);
    }

    /**
     * balancegame.html에서 유저가 선택한 answer를 통해 실질적인 GameScore를 변동하는 곳
     * 완료 후 gameresult로 값을 보내준다.
     * @return
     */
    /*
    @PostMapping("calculatinggamescore")
    public String calculatingGameScore(@RequestParam Map<String, String> userAnswers, Model model) {
        balanceQuestionService.calculatingScores(userAnswers, gamescores);
        model.addAttribute("gamescores", gamescores);
        System.out.println("calculation : " + gamescores.toString());

        return "gameresult";
    }*/

    @PostMapping("/gameresult")
    public String gameResult(@RequestParam Map<String, String> userAnswers, Model model) {
        // 선택한 목록에 따른 s, c, h, d 점수 변동
        balanceQuestionService.calculatingScores(userAnswers, gamescores);

        // total 점수 구현 및 model 에 데이터 입력
        int totalScore = gameResultService.totalScore(gamescores);
        model.addAttribute("totalScore", totalScore);

        // category(건강,재물,연애,학업)별 게임 결과 점수 model에 입력
        Map<String, Object> categoryScore = gameResultService.getOldCardScores(gamescores);
        model.addAttribute("categoryScore", categoryScore);

        // 카드 max, min 찾아서 점심 값 넣기
        CardType MAX = gameResultService.getMaxCategory(gamescores);
        CardType MIN = gameResultService.getMinCategory(gamescores);
        String todaysLunch = gameResultService.todaysLunch(MAX, MIN);
        model.addAttribute("todaysLunch", todaysLunch);

        // MAX 와 MIN에 따른 점심메뉴 이미지 경로 찾기 및 model에 넣기
        String lunchPath = gameResultService.getTodaysLunchPath(MAX, MIN);
        model.addAttribute("lunchPath", lunchPath);

        // 게임점수를 카드점수로 변동 --> 트럼프 이미지 경로, 카테고리별 문장 출력은 카드점수 변동 후에 작성해야함!!!!!!!!!!
        gameResultService.changingCardNumber(gamescores);

        // 트럼프 이미지 경로 model에 넣기
        Map<String, Object> cardPath = gameResultService.balanceTrump(gamescores);
        model.addAttribute("cardPath", cardPath);

        // 각 카테고리 점수별 운세 문장 출력
        Map<String, Object> categoryResult = gameResultService.getCategoryResult(gamescores);
        model.addAttribute("categoryResult", categoryResult);

        return "gameresult";
    }


    @PostMapping("/insertWish")
    public String insertWish(@RequestParam String userWish , Model model, HttpSession session) {
        Object loginUser = session.getAttribute("loggedInUser");
        if(loginUser != null) {
            return "redirect:/"; // 로그인이 안된 상태에서 댓글을 입력할 경우 로그인 페이지로 돌려보내기
        }


        WishTree wishTree = new WishTree();
        wishTree.setUserId("testId1"); // 추후 로그인 연동하면 로그인했을 때 세션으로 가져온 유저아이디로 변경할 것
        wishTree.setUserWish(userWish);
        System.out.println("wishTree : " + wishTree);
        wishTreeService.insertWish(wishTree);
        return "redirect:/";
    }
}
