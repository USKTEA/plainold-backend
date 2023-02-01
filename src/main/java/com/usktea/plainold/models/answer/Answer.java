package com.usktea.plainold.models.answer;

import com.amazonaws.services.ec2.model.CpuOptionsRequest;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private Long inquiryId;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Answer() {
    }

    public void delete() {
        this.status = Status.DELETED;
    }
}
