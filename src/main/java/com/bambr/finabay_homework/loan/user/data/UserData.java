package com.bambr.finabay_homework.loan.user.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Daniil on 10.09.2015.
 */
@Entity
public class UserData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String surname;

    @Column(nullable = false, unique = true)
    String personalId;

    @Column(nullable = true, insertable = false)
    Boolean locked = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getLocked() {
        return locked == null ? false : locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
