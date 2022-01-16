package com.konferanssalonuprojem;

public class Customer extends Conference {
    private String name;
    private String surname;
    private int pay;

    public Customer() {
    }

    public Customer(String conferenceName, String conferenceTime, String fullSeat, String name, String surname, int pay) {
        super(conferenceName, conferenceTime, fullSeat);
        this.name = name;
        this.surname = surname;
        this.pay = pay;
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

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }
}
