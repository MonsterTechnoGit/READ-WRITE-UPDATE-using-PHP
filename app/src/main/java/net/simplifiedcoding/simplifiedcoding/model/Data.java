package net.simplifiedcoding.simplifiedcoding.model;

public class Data {

    String questions;
    String answer_yes;
    String answer_no;
    String answer_one;
    String answer_two;
    String answer_three;
    String answer_four;

    public Data(){}

    public Data(String questions, String answer_yes, String answer_no, String answer_one, String answer_two, String answer_three, String answer_four) {
        this.questions = questions;
        this.answer_yes = answer_yes;
        this.answer_no = answer_no;
        this.answer_one = answer_one;
        this.answer_two = answer_two;
        this.answer_three = answer_three;
        this.answer_four = answer_four;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer_yes() {
        return answer_yes;
    }

    public void setAnswer_yes(String answer_yes) {
        this.answer_yes = answer_yes;
    }

    public String getAnswer_no() {
        return answer_no;
    }

    public void setAnswer_no(String answer_no) {
        this.answer_no = answer_no;
    }

    public String getAnswer_one() {
        return answer_one;
    }

    public void setAnswer_one(String answer_one) {
        this.answer_one = answer_one;
    }

    public String getAnswer_two() {
        return answer_two;
    }

    public void setAnswer_two(String answer_two) {
        this.answer_two = answer_two;
    }

    public String getAnswer_three() {
        return answer_three;
    }

    public void setAnswer_three(String answer_three) {
        this.answer_three = answer_three;
    }

    public String getAnswer_four() {
        return answer_four;
    }

    public void setAnswer_four(String answer_four) {
        this.answer_four = answer_four;
    }
}
