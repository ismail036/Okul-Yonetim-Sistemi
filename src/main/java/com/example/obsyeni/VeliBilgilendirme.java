package com.example.obsyeni;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class VeliBilgilendirme implements Initializable {
    @FXML
    TableView sınavNotları , sınavProjeTarihTableView , devamsızlıkTableView;
    @FXML
    Button notBilgisi , sınavTarihiButon , devamsızlıkButon;
    @FXML
    TableColumn<Sınavnotları,String> dersler;
    @FXML
    TableColumn<Sınavnotları, Label> sınav1;
    @FXML
    TableColumn<Sınavnotları, Label> sınav2;
    @FXML
    TableColumn<Sınavnotları, Label> sınav3;
    @FXML
    TableColumn<Sınavnotları, Label> proje1;
    @FXML
    TableColumn<Sınavnotları, Label> proje2;
    @FXML
    TableColumn<Sınavnotları, Label> proje3;
    @FXML
    TableColumn<Sınavnotları, Label> puan;
    @FXML
    TableColumn<Sınavnotları,String> sınavTarihiDers;
    @FXML
    TableColumn<Sınavnotları,String> sınavTarihiTur;
    @FXML
    TableColumn<Sınavnotları,String> sınavTarihiTarih   ;
    @FXML
    TableColumn<Student,String> devamsızlıkYılTableView ,devamsızlıkAyTableView , devamsızlıkGünTableView;
    @FXML
    AnchorPane ogrenciBilgiAnchorPane , sınavVeProjeTarihleriAnchorPane , devamsızlıkAnchorPane;

    @FXML
    Text userName;

    int sınav1kontrol , sınav2kontorol , sınav3kontorol , proje1kontrol , proje2kontrol , proje3kontrol , puanKontrol;

    Connection connection = DriverManager.getConnection("jdbc:mariadb://mydbinstance.csgdsm1hhtui.eu-central-1.rds.amazonaws.com/okul_yonetim?user=admin&password=123456789");

    String[] girilenDersler = {"Matematik" , "Türkçe" , "Fen Bilgisi" , "Sosyal Bilgiler" , "Resim" , "Müzik", "Yabancı Dil","Beden Eğitimi ve Spor"};


    ObservableList<Sınavnotları> sınavNotu = FXCollections.observableArrayList();
    ObservableList<Sınavnotları> sınavTarihleri = FXCollections.observableArrayList();
    ObservableList<Student> devamsızlıklar = FXCollections.observableArrayList();

    public VeliBilgilendirme() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setText(VBSGiris.adSoyad);

        sınavNotları.getItems().addAll(sınavNotu);
        dersler.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("ders"));
        sınav1.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("sınav1l"));
        sınav2.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("sınav2l"));
        sınav3.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("sınav3l"));
        proje1.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("proje1l"));
        proje2.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("proje2l"));
        proje3.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("proje3l"));
        puan.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("puanL"));
    }

    public void notBilgisi() throws SQLException {
        sınavNotları.getItems().clear();
        sınavNotu.clear();
        ogrenciBilgiAnchorPane.setVisible(true);
        sınavVeProjeTarihleriAnchorPane.setVisible(false);
        devamsızlıkAnchorPane.setVisible(false);

        for (String s : girilenDersler){
            puanKontrol(s);
            Label sınav1l  = new Label();
            if (sınav1kontrol != 0){
                sınav1l.setText(String.valueOf(sınav1kontrol));
            }
            sınav1l.setMaxWidth(33);
            sınav1l.setAlignment(Pos.CENTER_RIGHT);

            Label sınav2l  = new Label();
            if (sınav2kontorol != 0){
                sınav2l.setText(String.valueOf(sınav2kontorol));
            }
            sınav2l.setMaxWidth(33);
            sınav2l.setAlignment(Pos.CENTER_RIGHT);

            Label sınav3l  = new Label();
            if (sınav3kontorol != 0){
                sınav3l.setText(String.valueOf(sınav3kontorol));
            }
            sınav3l.setMaxWidth(33);
            sınav3l.setAlignment(Pos.CENTER_RIGHT);


            Label proje1l  = new Label();
            if (proje1kontrol != 0){
                proje1l.setText(String.valueOf(proje1kontrol));
            }
            proje1l.setMaxWidth(33);
            proje1l.setAlignment(Pos.CENTER_RIGHT);

            Label proje2l  = new Label();
            if (proje2kontrol != 0){
                proje2l.setText(String.valueOf(proje2kontrol));
            }
            proje2l.setMaxWidth(33);
            proje2l.setAlignment(Pos.CENTER_RIGHT);

            Label proje3l  = new Label();
            if (proje3kontrol != 0){
                proje3l.setText(String.valueOf(proje3kontrol));
            }
            proje3l.setMaxWidth(33);
            proje3l.setAlignment(Pos.CENTER_RIGHT);

            Label puan = new Label();
            if (puanKontrol != 0){
                puan.setText(String.valueOf(puanKontrol));
            }
            puan.setMaxWidth(33);
            puan.setAlignment(Pos.CENTER_RIGHT);




            Sınavnotları ss = new Sınavnotları(s,sınav1l, sınav2l, sınav3l,proje1l,proje2l,proje3l,puan);
            sınavNotu.add(ss);
        }

        sınavNotları.getItems().addAll(sınavNotu);
        dersler.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("ders"));
        sınav1.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("sınav1l"));
        sınav2.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("sınav2l"));
        sınav3.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("sınav3l"));
        proje1.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("proje1l"));
        proje2.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("proje2l"));
        proje3.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("proje3l"));
        puan.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("puanL"));
    }

    private  void puanKontrol(String s) throws SQLException {
        sınav1kontrol = 0;
        sınav2kontorol = 0;
        sınav3kontorol = 0;
        proje1kontrol = 0;
        proje2kontrol = 0;
        proje3kontrol = 0;
        puanKontrol = 0;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
        preparedStatement.setString(1,VBSGiris.öğrenciNo);
        preparedStatement.setString(2,s);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            sınav1kontrol = resultSet.getInt("Sınav1");
            sınav2kontorol = resultSet.getInt("Sınav2");
            sınav3kontorol = resultSet.getInt("Sınav3");
            proje1kontrol = resultSet.getInt("Proje1");
            proje2kontrol = resultSet.getInt("Proje2");
            proje3kontrol = resultSet.getInt("Proje3");
            puanKontrol = resultSet.getInt("Puan");

        }

    }

    public void sınavTarihiButon() throws SQLException {
        sınavVeProjeTarihleriAnchorPane.setVisible(true);
        ogrenciBilgiAnchorPane.setVisible(false);
        devamsızlıkAnchorPane.setVisible(false);
        devamsızlıkAnchorPane.setVisible(false);
        sınavTarihleri.clear();
        sınavProjeTarihTableView.getItems().clear();


        String sınıf = "", ders, tur, tarih;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ogrencilistesi WHERE  kimlikNo = (?)");
        preparedStatement.setString(1,VBSGiris.kimlikNo);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            sınıf = resultSet.getString("kayıtSınıfı");
        }

        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT  * FROM sınavtarihleri WHERE sınıfSube = (?)");
        preparedStatement1.setString(1,sınıf);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        while (resultSet1.next()){
            System.out.println("a");
            ders = resultSet1.getString("ders");
            tur = resultSet1.getString("tur");
            tarih = resultSet1.getString("tarih");

            Sınavnotları sınavtarih = new Sınavnotları(ders,tur,tarih);
            sınavTarihleri.add(sınavtarih);
        }

        sınavProjeTarihTableView.getItems().addAll(sınavTarihleri);
        sınavTarihiDers.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("ders"));
        sınavTarihiTur.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("tur"));
        sınavTarihiTarih.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("tarih"));





    }

    public void devamsızlıkButon() throws SQLException {
        sınavVeProjeTarihleriAnchorPane.setVisible(false);
        ogrenciBilgiAnchorPane.setVisible(false);
        devamsızlıkAnchorPane.setVisible(false);
        devamsızlıkAnchorPane.setVisible(true);

        devamsızlıklar.clear();


        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM devamsızlık WHERE  tcKimlikNO = (?) ");
        preparedStatement.setString(1,VBSGiris.kimlikNo);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String gun = resultSet.getString("gün");
            String ay = resultSet.getString("ay");
            String yıl = resultSet.getString("yıl");

            Student student = new Student(gun , ay ,yıl);
            devamsızlıklar.add(student);
        }

        devamsızlıkTableView.getItems().addAll(devamsızlıklar);
        devamsızlıkGünTableView.setCellValueFactory(new PropertyValueFactory<Student,String>("devamsızlıkGun"));
        devamsızlıkAyTableView.setCellValueFactory(new PropertyValueFactory<Student,String>("devamsızlıkAy"));
        devamsızlıkYılTableView.setCellValueFactory(new PropertyValueFactory<Student,String>("devamsızlıkYıl"));
    }
}
