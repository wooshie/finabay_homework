package com.bambr.finabay_homework.loan.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Daniil on 10.09.2015.
 */
@Entity
public class LoanApplicationData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotNull(message = "amount should be present")
    @JsonProperty("loan amount")
    Double loanAmount;


    @Column(nullable = false)
    @NotNull(message = "term should be present")
    String term;

    @Column(nullable = false)
    @NotEmpty(message = "name should be provided")
    String name;

    @Column(nullable = false)
    @NotEmpty(message = "surname should be provided")
    String surname;

    @Column(nullable = false)
    @NotEmpty(message = "personal id should be provided")
    String personalId;

    @Column
    Boolean approved = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("loan amount")
    public Double getLoanAmount() {
        return loanAmount;
    }

    @JsonProperty("loan amount")
    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "LoanApplicationData{" +
                "id=" + id +
                ", loanAmount=" + loanAmount +
                ", term='" + term + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personalId='" + personalId + '\'' +
                ", approved=" + approved +
                '}';
    }
}
