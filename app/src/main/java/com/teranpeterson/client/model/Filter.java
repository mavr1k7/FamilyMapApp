package com.teranpeterson.client.model;

import android.support.annotation.NonNull;
import android.util.Log;

public class Filter {
    private static Filter sFilter;

    private boolean birth;
    private boolean baptism;
    private boolean marriage;
    private boolean death;
    private boolean father;
    private boolean mother;
    private boolean male;
    private boolean female;

    private Filter() {
        birth = false;
        baptism = false;
        marriage = false;
        death = false;
        father = false;
        mother = false;
        male = false;
        female = false;
    }

    public static Filter get() {
        if (sFilter == null) {
            sFilter = new Filter();
        }
        return sFilter;
    }

    public boolean isBirth() {
        return birth;
    }

    public void setBirth(boolean birth) {
        this.birth = birth;
    }

    public boolean isBaptism() {
        return baptism;
    }

    public void setBaptism(boolean baptism) {
        this.baptism = baptism;
    }

    public boolean isMarriage() {
        return marriage;
    }

    public void setMarriage(boolean marriage) {
        this.marriage = marriage;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public boolean isFather() {
        return father;
    }

    public void setFather(boolean father) {
        this.father = father;
    }

    public boolean isMother() {
        return mother;
    }

    public void setMother(boolean mother) {
        this.mother = mother;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    @NonNull
    @Override
    public String toString() {
        return "Birth: " + birth + ", Father: " + father + ", Male: " + male + "\n";
    }
}
