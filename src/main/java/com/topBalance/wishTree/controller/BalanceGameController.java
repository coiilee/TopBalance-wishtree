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

    // 예비 출력구분 -> 점수 제대로 적용됐는지 확인 구분
    @GetMapping("/gameresult")
    public void gameResult(Model model) {
        Logger log = LoggerFactory.getLogger(BalanceGameController.class);
        //int spade = gamescores.getSpadeScore()
        log.info("gameresult : " + gamescores.toString());
    }

    @PostMapping("/gameresult")
    public String gameResult(@RequestParam Map<String, String> userAnswers, Model model) {
        // 선택한 목록에 따른 s, c, h, d 점수 변동
        balanceQuestionService.calculatingScores(userAnswers, gamescores);

        // total 점수 구현 및 model 에 데이터 입력
        int totalScore = gameResultService.totalScore(gamescores);
        model.addAttribute("totalScore", totalScore);

        // 각 점수 model 에 입력
        model.addAttribute("categoryScore", gamescores);

        // 카드 max, min 찾아서 점심 값 넣기
        String todaysLunch = gameResultService.todaysLunch(CardType.SPADE, CardType.HEART);
        model.addAttribute("lunchMax", todaysLunch);
        model.addAttribute("lunchMin", todaysLunch);

        // 각 카드 값 변동
        gameResultService.changingCardNumber(gamescores);

        // 트럼프 이미지 경로 model에 넣기
        Map<String, Object> cardPath = gameResultService.balanceTrump(gamescores);
        model.addAttribute("cardPath", cardPath);

        //s,c,h,d 별 운세 문장 출력



        return "gameresult";


    }





    @PostMapping("/insertWish")
    public String insertWish(@RequestParam String userWish , Model model, HttpSession session) {
        Object loginUser = session.getAttribute("loggedInUser");
        if(loginUser != null) {
            return "redirect:/"; // 로그인이 안된 상태에서 댓글을 입력할 경우 로그인 페이지로 돌려보내기
        }


        WishTree wishTree = new WishTree();
        wishTree.setUserId("testId"); // 추후 로그인 연동하면 로그인했을 때 세션으로 가져온 유저아이디로 변경할 것
        wishTree.setUserWish(userWish);
        System.out.println("wishTree : " + wishTree);
        wishTreeService.insertWish(wishTree);
        return "redirect:/";
    }
}
