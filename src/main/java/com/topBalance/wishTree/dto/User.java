package com.topBalance.wishTree.dto;
// javax -> jakarta

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id // primary key
    private int userId;
    private String userPassword;
    private String userName;
    private String userPhone;
    private String userBirthdate;
    private String userGender;
    private int ranking;
    private int totalScore;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date Game_date;
    private int dailyVisit;
}
