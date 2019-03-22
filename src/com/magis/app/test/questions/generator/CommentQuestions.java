package com.magis.app.test.questions.generator;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class CommentQuestions {
    Random rand;

    private static String chapterTitle = "Comments";

    private String[] commentQuestions = {
            "This is a comment",
            "This is\nA comment",
            "Author: Student\nClass: COMP 101\nDescription: ---",
            "This code will do [x]",
            "This code will do [x]\nIt will also do [y]"
    };
    private String[] generalCommentAnswers = {"Single-Line Comment", "Multi-Line Comment", "Java-Doc Comment", "Unknown"};
    private ArrayList<String> answers;

    private String correctAnswer;
    private String question = "";

    public CommentQuestions(){
        rand = new Random();
        answers = new ArrayList<>(Arrays.asList(generalCommentAnswers));
    }

    public void generateGeneralCommentQuestion(){
        question = "";
        int random;

        do {
            random = rand.nextInt(5);
            question = commentQuestions[random];
            commentQuestions[random] = "";
        }while(question.equals(""));

        question = question+"\n\nWhat would you use to comment the sentence above?";

        switch(random){
            case 0: correctAnswer = generalCommentAnswers[0];
                break;
            case 1: correctAnswer = generalCommentAnswers[1];
                break;
            case 2: correctAnswer = generalCommentAnswers[2];
                break;
            case 3: correctAnswer = generalCommentAnswers[0];
                break;
            case 4: correctAnswer = generalCommentAnswers[1];
                break;
        }
    }

    public ArrayList<String> getCommentAnswers(){ return answers; }

    public String getQuestion(){
        return question;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public static String getChapter(){
        return chapterTitle;
    }
}