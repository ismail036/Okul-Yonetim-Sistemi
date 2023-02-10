package com.example.obsyeni;

import javafx.scene.control.Button;

public class Teacher extends Person {
   String brans,email,sifre;

    public Teacher(String tcNo, String isim, String soyisim, String brans, String email, String sifre) {
        this.tcNo = tcNo;
        this.isim = isim;
        this.soyisim = soyisim;
        this.brans = brans;
        this.email = email;
        this.sifre = sifre;
    }


    public String getBrans() {
        return brans;
    }

    public void setBrans(String brans) {
        this.brans = brans;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
