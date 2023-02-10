package com.example.obsyeni;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Student extends Person{
    String  babaAdı , telefon , dogumTarihi ,  dogumYeri ,adres , kayıtSınıfı , ogrenciNo;
    Button button;
    RadioButton radioButton;
    String adSoyad;
    String devamsızlıkGun , devamsızlıkAy , devamsızlıkYıl;




    public Student(String devamsızlıkGun, String devamsızlıkAy, String devamsızlıkYıl) {
        this.devamsızlıkGun = devamsızlıkGun;
        this.devamsızlıkAy = devamsızlıkAy;
        this.devamsızlıkYıl = devamsızlıkYıl;
    }
    public String getDevamsızlıkGun() {
        return devamsızlıkGun;
    }
    public void setDevamsızlıkGun(String devamsızlıkGun) {
        this.devamsızlıkGun = devamsızlıkGun;
    }
    public String getDevamsızlıkAy() {
        return devamsızlıkAy;
    }

    public void setDevamsızlıkAy(String devamsızlıkAy) {
        this.devamsızlıkAy = devamsızlıkAy;
    }

    public String getDevamsızlıkYıl() {
        return devamsızlıkYıl;
    }

    public void setDevamsızlıkYıl(String devamsızlıkYıl) {
        this.devamsızlıkYıl = devamsızlıkYıl;
    }



    public Student(String tcNo, String isim, String babaAdı, String telefon, String dogumTarihi, String soyisim, String dogumYeri, String adres , String kayıtSınıfı , String ogrenciNo , Button button) {
        this.tcNo = tcNo;
        this.isim = isim;
        this.babaAdı = babaAdı;
        this.telefon = telefon;
        this.dogumTarihi = dogumTarihi;
        this.soyisim = soyisim;
        this.dogumYeri = dogumYeri;
        this.adres = adres;
        this.kayıtSınıfı = kayıtSınıfı;
        this.ogrenciNo = ogrenciNo;
        this.button =  button;
        this.button.setText("Sil");
    }

    public Student(String kayıtSınıfı ,  String tcNo , String ogrenciNo , String isim , String soyisim , RadioButton radioButton){
        this.kayıtSınıfı = kayıtSınıfı;
        this.tcNo = tcNo;
        this.ogrenciNo = ogrenciNo;
        this.isim = isim;
        this.soyisim = soyisim;
        this.radioButton = radioButton;
        this.radioButton.setText("Gelmedi");
    }

    public Student(String tcNo, String isim, String babaAdı, String telefon, String dogumTarihi, String soyisim, String dogumYeri, String adres, String kayıtSınıfı, String ogrenciNo) {
        this.tcNo = tcNo;
        this.isim = isim;
        this.babaAdı = babaAdı;
        this.telefon = telefon;
        this.dogumTarihi = dogumTarihi;
        this.soyisim = soyisim;
        this.dogumYeri = dogumYeri;
        this.adres = adres;
        this.kayıtSınıfı = kayıtSınıfı;
        this.ogrenciNo = ogrenciNo;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getKayıtSınıfı() {
        return kayıtSınıfı;
    }

    public void setKayıtSınıfı(String kayıtSınıfı) {
        this.kayıtSınıfı = kayıtSınıfı;
    }

    public String getOgrenciNo() {
        return ogrenciNo;
    }

    public void setOgrenciNo(String ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
    }


    public String getBabaAdı() {
        return babaAdı;
    }

    public void setBabaAdı(String babaAdı) {
        this.babaAdı = babaAdı;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getDogumYeri() {
        return dogumYeri;
    }

    public void setDogumYeri(String dogumYeri) {
        this.dogumYeri = dogumYeri;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }


}
