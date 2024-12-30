package com.topBalance.wishTree.controller;

import com.topBalance.wishTree.service.GameResultService;
import com.topBalance.wishTree.service.WishTreeService;
import com.topBalance.wishTree.vo.GameScores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultController {

    private GameScores gameScores;

    private WishTreeService wishTreeService;

    /*@GetMapping("/gameresult")
    public String gameResult() {
        int scoreS = gameScores.getSpadeScore();
        int scoreH = gameScores.getHeartScore();
        int scoreC = gameScores.getCloverScore();
        int scoreD = gameScores.getDiamondScore();
        return "";
    }
*/
}

