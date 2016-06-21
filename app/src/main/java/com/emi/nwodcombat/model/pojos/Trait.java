package com.emi.nwodcombat.model.pojos;

/**
 * Created by emiliano.desantis on 21/06/2016.
 */
public class Trait {
    private String kind;
    private String category1;
    private String category2;
    private int priority;

    public Trait(String kind, String category1, String category2) {
        this.kind = kind;
        this.category1 = category1;
        this.category2 = category2;
    }

    public Trait(String kind, String category1) {
        this.kind = kind;
        this.category1 = category1;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
