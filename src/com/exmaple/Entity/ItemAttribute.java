package com.exmaple.Entity;

public enum ItemAttribute {

    basic(19, 1) {
        public void describe() {
            System.out.println("It is good");
        }
    },

    starter(29, 7) {
        public void describe() {
            System.out.println("It is medium");
        }
    },

    premium(49, 30) {
        public void describe() {
            System.out.println("It is bad");
        }
    },

    enterprise(79, 365) {
        public void describe() {
            System.out.println("It is bad");
        }
    };


    String adDescribe;
    int money, days;

    ItemAttribute(int money, int days) {
        this.money = money;
        this.days = days;
    }
    public int getMoney () {
        return money;
    }
    public int getDays () {
        return days;
    }
    public abstract void describe();

}
