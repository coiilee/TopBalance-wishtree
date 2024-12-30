package com.topBalance.wishTree.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GameScores {
    private int spadeScore;
    private int cloverScore;
    private int heartScore;
    private int diamondScore;

    public GameScores() {
        this.spadeScore = 15;
        this.cloverScore = 15;
        this.heartScore = 15;
        this.diamondScore = 15;
    }

    public void increaseSpadeScore() {
        this.spadeScore += 1;
    }

    public void increaseCloverScore() {
        this.cloverScore += 1;
    }

    public void increaseHeartScore() {
        this.heartScore += 1;
    }

    public void increaseDiamondScore() {
        this.diamondScore += 1;
    }

    public void decreaseSpadeScore() {
        this.spadeScore -= 1;
    }

    public void decreaseCloverScore() {
        this.cloverScore -= 1;
    }

    public void decreaseHeartScore() {
        this.heartScore -= 1;
    }

    public void decreaseDiamondScore() {
        this.diamondScore -= 1;
    }
}
