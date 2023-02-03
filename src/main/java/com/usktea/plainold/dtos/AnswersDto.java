package com.usktea.plainold.dtos;

import java.util.List;

public class AnswersDto {
    private List<AnswerDto> answers;

    public AnswersDto() {
    }

    public AnswersDto(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }
}
