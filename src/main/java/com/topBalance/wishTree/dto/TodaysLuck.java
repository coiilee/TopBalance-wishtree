package com.topBalance.wishTree.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TodaysLuck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int index;
    private CardType cardType;
    private int cardNumber;
    private String cardResult;
}
