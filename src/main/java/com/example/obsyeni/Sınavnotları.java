package com.example.obsyeni;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Sınavnotları {

    String ogrenciNo;
    String adSoyad;
    int sınav1;
    int sınav2;
    int sınav3;
    int proje1;
    int proje2;
    int proje3;
    int puan;

    String tarih;
    String sınıfSube;
    String ders;
    String tur;

    TextField sınav1TF , sınav2TF , proje1TF  , proje2TF , sınav3TF , proje3TF;

    Label puanL;
    Label sınav1l  ;
    Label sınav2l ;
    Label sınav3l ;
    Label proje1l ;
    Label proje2l ;
    Label proje3l ;


    public Sınavnotları(String ders,  Label sınav1l, Label sınav2l, Label sınav3l, Label proje1l, Label proje2l, Label proje3l , Label puanL) {
        this.ders = ders;
        this.sınav1l = sınav1l;
        this.sınav2l = sınav2l;
        this.sınav3l = sınav3l;
        this.proje1l = proje1l;
        this.proje2l = proje2l;
        this.proje3l = proje3l;
        this.puanL = puanL;

    }

    public Sınavnotları(String sınıfSube, String ders, String tur, String tarih) {
        this.sınıfSube = sınıfSube;
        this.ders = ders;
        this.tur = tur;
        this.tarih = tarih;
    }

    public Sınavnotları(String ders , String tur , String tarih){
        this.ders = ders;
        this.tur = tur;
        this.tarih = tarih;
    }


    public Sınavnotları(String ogrenciNo, String adSoyad, TextField sınav1TF, TextField sınav2TF, TextField sınav3TF,TextField proje1TF, TextField proje2TF, TextField proje3TF,Label puanL) {
        this.ogrenciNo = ogrenciNo;
        this.adSoyad = adSoyad;
        this.sınav1TF = sınav1TF;
        this.sınav2TF = sınav2TF;
        this.sınav3TF = sınav3TF;
        this.proje1TF = proje1TF;
        this.proje2TF = proje2TF;
        this.proje3TF = proje3TF;
        this.puanL = puanL;
    }

    public Sınavnotları(String ogrenciNo, int sınav , String notGirisiYapılacakDers) {
        this.ogrenciNo = ogrenciNo;
        if (notGirisiYapılacakDers == "sınav1"){
            this.sınav1 = sınav;
        }else if (notGirisiYapılacakDers == "sınav2"){
            this.sınav2 = sınav;
        } else if (notGirisiYapılacakDers == "proje1") {
            this.proje1 = sınav;
        } else if (notGirisiYapılacakDers == "proje2") {
            this.sınav2 = sınav;
        }else if(notGirisiYapılacakDers == "sınav3"){
            this.sınav3 = sınav;
        }else if(notGirisiYapılacakDers == "proje3"){
            this.proje3 = sınav;
        }

    }

  /*  public Sınavnotları(String ogrenciNo, String adSoyad, int sınav1, int sınav2, int sınav3,int proje1, int proje2,int proje3 ,int puan, TextField sınav1TF, TextField sınav2TF,TextField sınav3TF ,TextField proje1TF, TextField proje2TF , TextField proje3TF ,Label puanL) {
        this.ogrenciNo = ogrenciNo;
        this.adSoyad = adSoyad;
        this.sınav1 = sınav1;
        this.sınav2 = sınav2;
        this.sınav3 = sınav3;
        this.proje1 = proje1;
        this.proje2 = proje2;
        this.proje3 = proje3;
        this.puan = puan;
        this.sınav1TF = sınav1TF;
        sınav1TF.setMaxWidth(35);
        this.sınav2TF = sınav2TF;
        sınav2TF.setMaxWidth(35);
        this.sınav3TF = sınav3TF;
        sınav3TF.setMaxWidth(35);
        this.proje1TF = proje1TF;
        proje1TF.setMaxWidth(35);
        this.proje2TF = proje2TF;
        proje2TF.setMaxWidth(35);
        this.sınav3TF = proje3TF;
        proje3TF.setMaxWidth(35);
        this.puanL = puanL;
    }*/

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public int getSınav3() {
        return sınav3;
    }

    public void setSınav3(int sınav3) {
        this.sınav3 = sınav3;
    }

    public int getProje3() {
        return proje3;
    }

    public void setProje3(int proje3) {
        this.proje3 = proje3;
    }

    public TextField getSınav3TF() {
        return sınav3TF;
    }

    public void setSınav3TF(TextField sınav3TF) {
        this.sınav3TF = sınav3TF;
    }

    public TextField getProje3TF() {
        return proje3TF;
    }

    public void setProje3TF(TextField proje3TF) {
        this.proje3TF = proje3TF;
    }

    public String getSınıfSube() {
        return sınıfSube;
    }

    public void setSınıfSube(String sınıfSube) {
        this.sınıfSube = sınıfSube;
    }

    public String getDers() {
        return ders;
    }

    public void setDers(String ders) {
        this.ders = ders;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public Label getSınav1l() {
        return sınav1l;
    }

    public void setSınav1l(Label sınav1l) {
        this.sınav1l = sınav1l;
    }

    public Label getSınav2l() {
        return sınav2l;
    }

    public void setSınav2l(Label sınav2l) {
        this.sınav2l = sınav2l;
    }

    public Label getSınav3l() {
        return sınav3l;
    }

    public void setSınav3l(Label sınav3l) {
        this.sınav3l = sınav3l;
    }

    public Label getProje1l() {
        return proje1l;
    }

    public void setProje1l(Label proje1l) {
        this.proje1l = proje1l;
    }

    public Label getProje2l() {
        return proje2l;
    }

    public void setProje2l(Label proje2l) {
        this.proje2l = proje2l;
    }

    public Label getProje3l() {
        return proje3l;
    }

    public void setProje3l(Label proje3l) {
        this.proje3l = proje3l;
    }

    public TextField getSınav1TF() {
        return sınav1TF;
    }

    public void setSınav1TF(TextField sınav1TF) {
        this.sınav1TF = sınav1TF;
    }

    public TextField getSınav2TF() {
        return sınav2TF;
    }

    public void setSınav2TF(TextField sınav2TF) {
        this.sınav2TF = sınav2TF;
    }

    public TextField getProje1TF() {
        return proje1TF;
    }

    public void setProje1TF(TextField proje1TF) {
        this.proje1TF = proje1TF;
    }

    public TextField getProje2TF() {
        return proje2TF;
    }

    public void setProje2TF(TextField proje2TF) {
        this.proje2TF = proje2TF;
    }

    public Label getPuanL() {
        return puanL;
    }

    public void setPuanL(Label puanL) {
        this.puanL = puanL;
    }

    public Sınavnotları(String ogrenciNo, String adSoyad) {
        this.ogrenciNo = ogrenciNo;
        this.adSoyad = adSoyad;
    }

    public String getOgrenciNo() {
        return ogrenciNo;
    }

    public void setOgrenciNo(String ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public int getSınav1() {
        return sınav1;
    }

    public void setSınav1(int sınav1) {
        this.sınav1 = sınav1;
    }

    public int getSınav2() {
        return sınav2;
    }

    public void setSınav2(int sınav2) {
        this.sınav2 = sınav2;
    }

    public int getProje1() {
        return proje1;
    }

    public void setProje1(int proje1) {
        this.proje1 = proje1;
    }

    public int getProje2() {
        return proje2;
    }

    public void setProje2(int proje2) {
        this.proje2 = proje2;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }
}
