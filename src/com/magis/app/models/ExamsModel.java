package com.magis.app.models;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ExamsModel {

    protected Document document;
    protected HashMap<String, ChapterModel> chapters;
    protected String examType;


    public ChapterModel getChapter(String chapterName) {
        if (chapters.containsKey(chapterName)) return chapters.get(chapterName);
        return null;
    }

    public ExamsModel(String examType) {
        this.examType = examType;
        this.chapters = new HashMap<>();

        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/magis/app/resources/" + examType + ".xml");
//        File file = new File("src/com/magis/app/resources/quizzes.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            assert dBuilder != null;
            this.document = dBuilder.parse(file);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        assert this.document != null;
        this.document.getDocumentElement().normalize();

        NodeList chapters = this.document.getElementsByTagName("chapter");
        for (int i = 0; i < chapters.getLength(); i++) {
            Node chapter = chapters.item(i);
            ChapterModel chapterModel = new ChapterModel(chapter);
            this.chapters.put(chapter.getAttributes().getNamedItem("id").getNodeValue(), chapterModel);
        }
    }

    public class ChapterModel {
        private String chapterName;
        private ArrayList<QuestionsModel> questions;

        public ArrayList<QuestionsModel> getQuestions() {
            return questions;
        }
        public QuestionsModel getQuestion(int index) {
            if (index > questions.size() - 1) {
                return null;
            }
            return questions.get(index);
        }

        public int getNumAvailableQuestions() {
            return questions.size();
        }

        public String getChapterName() {
            return chapterName;
        }

        ChapterModel(Node chapter) {
            this.chapterName = chapter.getAttributes().getNamedItem("id").getNodeValue();
            this.questions = new ArrayList<>();
            Element chapterElement = (Element) chapter;


            Element questionsElement = (Element) chapterElement.getElementsByTagName("questions").item(0);
            NodeList questions = questionsElement.getElementsByTagName("question");
            for (int i = 0; i < questions.getLength(); i++) {
                Node question = questions.item(i);
                QuestionsModel questionsModel = new QuestionsModel(question);
                this.questions.add(questionsModel);
            }


        }

        public class QuestionsModel {

            private String statement;
            private boolean isWritten;
            private boolean shuffle;
            private int level;
            private ArrayList<String> answers;
            private ArrayList<String> correctAnswers;
            private ArrayList<String> incorrectAnswers;

            public String getStatement() {
                return statement;
            }

            public int getNumAnswers() {
                return incorrectAnswers.size() + correctAnswers.size();
            }

            public int getNumIncorrectAnswers() { return incorrectAnswers.size(); }

            public int getNumCorrectAnswers() { return correctAnswers.size(); }

            public ArrayList<String> getAnswers() { return answers; }

            public ArrayList<String> getCorrectAnswers() {
                return correctAnswers;
            }

            public ArrayList<String> getIncorrectAnswers() {
                return incorrectAnswers;
            }

            public int getLevel() {
                return level;
            }

            public boolean isShuffle() { return shuffle; }

            public boolean isWritten() { return isWritten; }

            QuestionsModel(Node question) {
                this.answers = new ArrayList<>();
                this.incorrectAnswers = new ArrayList<>();
                this.correctAnswers = new ArrayList<>();
                Element questionElement = (Element) question;
                this.isWritten = questionElement.getElementsByTagName("written").item(0) != null && Boolean.parseBoolean(questionElement.getElementsByTagName("written").item(0).getTextContent());
                this.shuffle = questionElement.getElementsByTagName("shuffle").item(0) == null || Boolean.parseBoolean(questionElement.getElementsByTagName("shuffle").item(0).getTextContent());
                this.level = questionElement.getElementsByTagName("level").item(0) != null ? Integer.parseInt(questionElement.getElementsByTagName("level").item(0).getTextContent()) : 1;
                this.statement = questionElement.getElementsByTagName("statement").item(0).getTextContent();
                NodeList answers = questionElement.getElementsByTagName("answer");
                for (int i = 0; i < answers.getLength(); i++) {
                    Element answer = (Element) answers.item(i);
                    this.answers.add(answer.getTextContent());
                    if (answer.hasAttribute("id")) {
                        if (answer.getAttributes().getNamedItem("id").getNodeValue().equals("correct")) {
                            this.correctAnswers.add(answer.getTextContent());
                        } else {
                            System.err.println("FAILED to add \"" + answer.getNodeValue() + "\" to list of answer choices. Unknown answer ID with question \"" + statement + "\"");
                        }
                    } else {
                        incorrectAnswers.add(answer.getTextContent());
                    }
                }
                if (isWritten && incorrectAnswers.size() > 1) {
                    System.err.println("Question of type \"written\" should ALL be marked as correct answer. Please fix the question with the statement: \"" + statement + "\"");
                }
                if (incorrectAnswers.size() == 0  && !isWritten) {
                    System.err.println("The following question: \"" + statement + "\"\n\n" +
                            "does not have any incorrect answers. Did you forget to add '<written>true</written>' ?");
                }

            }
        }
    }
}
