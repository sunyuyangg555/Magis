package com.magis.app.test.questions.generator;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class VariableQuestions {
    Random rand;

    private static String chapterTitle = "Variables";

    private String[] names1 = {"calculation", "value", "num1", "product", "quotient", "publicNumber", "interest_on_loan",
            "sum", "difference_of_nums", "totalSum", "num3", "square_root", "x", "y"};
    private String[] names2 = {"4sum", "public", "void", "return", "32unit", "difference of nums", "Public Number", "interest on loan",
            " ", "2power", "<greeting>"};

    private ArrayList<String> correctVariables;
    private ArrayList<String> wrongVariables;

    private String[] dataTypeMatchingAnswers = {"int", "double", "String", "char", "None"};
    private ArrayList<String> answers;
    private char[] characters = {'+','-','*','/','%','<','=','>'};
    private String correctAnswer;
    private String question = "";

    public VariableQuestions(){
        rand = new Random();
        correctVariables = new ArrayList<>(Arrays.asList(names1));
        wrongVariables = new ArrayList<>(Arrays.asList(names2));
        answers = new ArrayList<>();
    }

    public void getVariableNameQuestion(){
        answers.clear();
        question = "";
        answers.add("True");
        answers.add("False");
        answers.add("Unknown");

        int rightWrong = rand.nextInt(2);
        int selector;
        String word = "";

        switch(rightWrong){
            case 0:
                selector = rand.nextInt(correctVariables.size());
                word = correctVariables.get(selector);
                correctVariables.remove(selector);
                correctAnswer = answers.get(0);
                question = "<datatype> "+word+" = ...;\n\n";
                break;
            case 1:
                selector = rand.nextInt(wrongVariables.size());
                word = wrongVariables.get(selector);
                wrongVariables.remove(selector);
                correctAnswer = answers.get(1);
                question = "<datatype> "+word+" = ...;\n\n";
                break;
        }
        question+="True or False, is \""+word+"\" a proper name for a variable?";
    }

    public void getInstanceVariableQuestion(){
        question = "";
        ArrayList<String> shuffler = new ArrayList<>();

        int selector = rand.nextInt(names1.length);
        String word = names1[selector];

        question = "private int "+word+";\n\n" +
                "public void setThing(int "+word+"){\n\n}\n\n" +
                "How would you set the instance variable \""+word+"\" equal to the local variable \""+word+"\"?";

        shuffler.add("this."+word+" = " +word+";");
        shuffler.add(word+" = "+word+";");
        shuffler.add("int value = "+word+";\n"+word+" = value;");
        correctAnswer = shuffler.get(0);
        Collections.shuffle(shuffler);
        shuffler.add("Unknown");

        answers = shuffler;
    }

    public ArrayList<String> getAnswers() { return answers; }

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
