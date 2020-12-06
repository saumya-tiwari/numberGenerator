package com.assignment.numberGenerator.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Entity
public class NumberGeneratorRequestWrapper extends NumberGeneratorRequest {

    @javax.persistence.Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"

    )
    @Column(name="id",updatable = false,nullable = false)
    private UUID id;

    @NotBlank(message = "step is mandatory")
    @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "step should be a number")
    private String step;

    @NotBlank(message = "goal is mandatory")
    @Pattern(regexp="^(0|[1-9][0-9]*)$",message = "goal should be a number")
    private String goal;

    public enum StatusEnum {
        SUCCESS("success"),

        IN_PROGRESS("in_progress"),

        ERROR("error");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static NumberGeneratorRequestWrapper.StatusEnum fromValue(String text) {
            for (NumberGeneratorRequestWrapper.StatusEnum b : NumberGeneratorRequestWrapper.StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    private NumberGeneratorRequestWrapper.StatusEnum statusEnum;

    public NumberGeneratorRequestWrapper.StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(NumberGeneratorRequestWrapper.StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    @ElementCollection
    private List<Integer> numList;

    public List<Integer> getNumList() {
        return numList;
    }

    public void setNumList(List<Integer> numList) {
        this.numList = numList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
