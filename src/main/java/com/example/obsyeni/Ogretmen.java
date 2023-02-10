package com.example.obsyeni;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Ogretmen  implements Initializable  {

    /*  Sınav Tarihleri */
    @FXML
    Button sınavTarihiButon ,  sınavTarihiListele , notİslemleriButon , sınavTarihiKaydet , ogrenciBilgiListele , ogrenciBilgiButon;
    @FXML
    ChoiceBox sınavTarihiSınıfSecimi , sınavTarihiTürSecim , notGirisiSınıfSecimi , ogrenciBilgisiSınıfSecim;
    @FXML
    Label sınavTarihiDers , notGirisDers;
    @FXML
    DatePicker sınavTarihiSecimi;

    @FXML
    Label userName;
    @FXML
    AnchorPane sınavTarihiKaydetAnchorPane , sınavTarihiListesi , sınıfSubeSecimiAnchorPane2 , ogrenciBilgiAnchorPane;
    @FXML
    Label sınavTarihListesiLabel;
    @FXML
    ScrollPane sınavTarihiScroll , notGirisScroll;

    @FXML
    TableView<Sınavnotları> notGirisiTableView , sınavTarihiTableView;
    @FXML
    TableColumn<Sınavnotları,String> notGirisiOgrenciNo ,norGirisiAdSoayd , sınavTarihiDersAdı , sınavTarihiTur , sınavTarihi;
    @FXML
    TableColumn<Sınavnotları, TextField> sınav1column ,sınav3column,proje3column , sınav2column , proje1column , proje2column;
    @FXML
    TableColumn<Sınavnotları, Label> puanLabelCell;

    @FXML
    TableView<Student> ogrenciBilgi;
    @FXML
    TableColumn<Student,String> tc , ogrenciNo , isim , soyİsim;

        Connection connection = DriverManager.getConnection("jdbc:mariadb://mydbinstance.csgopl1hhtui.eu-central-1.rds.amazonaws.com:3306/okul_yonetim?user=admin&password=123456789");

    public Ogretmen() throws SQLException {
    }


    ArrayList<TextField> sınav1Field = new ArrayList<>();
    ArrayList<TextField> sınav2Field = new ArrayList<>();
    ArrayList<TextField> sınav3Field = new ArrayList<>();
    ArrayList<TextField> proje1Field = new ArrayList<>();
    ArrayList<TextField> proje2Field = new ArrayList<>();
    ArrayList<TextField> proje3Field = new ArrayList<>();
    ArrayList<Label> puanLabel = new ArrayList<>();

    ObservableList<Sınavnotları> dersNotları = FXCollections.observableArrayList();
    ObservableList<Sınavnotları> sınavTarihleri = FXCollections.observableArrayList();
    ObservableList<Student> studentList = FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setText(YonetimGiris.name + " " + YonetimGiris.surname);
        userName.setAlignment(Pos.CENTER);

        try {
            sınavTarihiSınıfSecimi.getItems().addAll(dersineGirilenSınıflar());
            ogrenciBilgisiSınıfSecim.getItems().addAll(dersineGirilenSınıflar());
            sınavTarihiDers.setText("   " + YonetimGiris.ders);
            sınavTarihiTürSecim.getItems().addAll("Sınav" , "Proje");
            notGirisDers.setText(YonetimGiris.ders);

        } catch (SQLException e) {
            System.out.println("sql hatası");
        }catch (NullPointerException e){
            System.out.println("pointer hatası");
        }

        notGirisiTableView.setItems(dersNotları);
        notGirisiOgrenciNo.setCellValueFactory(new PropertyValueFactory<Sınavnotları , String>("ogrenciNo"));
        norGirisiAdSoayd.setCellValueFactory(new PropertyValueFactory<Sınavnotları , String>("adSoyad"));
        sınav1column.setCellValueFactory(new PropertyValueFactory<Sınavnotları , TextField>("sınav1TF"));
        sınav2column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("sınav2TF"));
        sınav3column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("sınav3TF"));
        proje1column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("proje1TF"));
        proje2column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("proje2TF"));
        proje3column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("proje3TF"));
        puanLabelCell.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("puanL"));

        sınavTarihiTableView.setItems(sınavTarihleri);
        sınavTarihiDersAdı.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("ders"));
        sınavTarihiTur.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("tur"));
        sınavTarihi.setCellValueFactory(new PropertyValueFactory<Sınavnotları,String>("tarih"));

        ogrenciBilgi.setItems(studentList);
        tc.setCellValueFactory(new PropertyValueFactory<Student,String>("tcNo"));
        ogrenciNo.setCellValueFactory(new PropertyValueFactory<Student,String>("ogrenciNo"));
        isim.setCellValueFactory(new PropertyValueFactory<Student,String>("isim"));
        soyİsim.setCellValueFactory(new PropertyValueFactory<Student,String>("soyisim"));
    }



    /*  Bu metod öğretmenin dersine girdiği sınıfları veri tabanından çekip ArrayList e ekler */
    private ArrayList<String> dersineGirilenSınıflar() throws SQLException {
        ArrayList<String> dersineGirilenSınıflar = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mariadb://mydbinstance.csgofyuhhtui.eu-central-1.rds.amazonaws.com:3306/okul_yonetim?user=admin&password=123456789");
        PreparedStatement statement = connection.prepareStatement("Select * from sınıfadersiolanogretmenler");
        ResultSet result = statement.executeQuery();
        while (result.next()){
            if (YonetimGiris.tc.equals(result.getString("KimlikNo"))){
                System.out.println(result.getString("Sınıf"));
                dersineGirilenSınıflar.add(result.getString("Sınıf"));
            }
        }
        return dersineGirilenSınıflar;
    }



    /* Bu metod seçilen Sınıf ve Sube de okuyan ögrencileri veri tabanından çekip tabloya ekler .
    Not girisleri için TextField alanları olusturur .
    Not girisi yapılan öğrencilerin
     */
    public void dersNotuGiris() throws SQLException {
        dersNotları.clear();

        int j = 0;

        String sınıfSube = notGirisiSınıfSecimi.getValue().toString();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ogrencilistesi WHERE kayıtSınıfı = ?");
        preparedStatement.setString(1,sınıfSube);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String ogrenciNo = resultSet.getString("ogrenciNo");
            String adSoyad = resultSet.getString("isim") + " " + resultSet.getString("soyisim");

            sınav1Field.add(j,new TextField());
            sınav2Field.add(j,new TextField());
            sınav3Field.add(j,new TextField());
            proje1Field.add(j,new TextField());
            proje2Field.add(j,new TextField());
            proje3Field.add(j,new TextField());
            puanLabel.add(j,new Label());

            sınav1Field.get(j).setId(ogrenciNo);
            sınav1Field.get(j).setOnAction(this::notKaydetSınav1);
            sınav1Field.get(j).setMaxWidth(35);
            sınav1Field.get(j).setText(sınav1FieldKontrol(ogrenciNo , notGirisDers.getText().toString() ));

            sınav2Field.get(j).setId(ogrenciNo);
            sınav2Field.get(j).setOnAction(this::notKaydetSınav2);
            sınav2Field.get(j).setMaxWidth(35);
            sınav2Field.get(j).setText(sınav2FieldKontrol(ogrenciNo , notGirisDers.getText().toString()   ));

            sınav3Field.get(j).setId(ogrenciNo);
            sınav3Field.get(j).setOnAction(this::notKaydetSınav3);
            sınav3Field.get(j).setMaxWidth(35);
            sınav3Field.get(j).setText(sınav3FieldKontrol(ogrenciNo , notGirisDers.getText().toString() ));

            proje1Field.get(j).setId(ogrenciNo);
            proje1Field.get(j).setOnAction(this::notKaydetProje1);
            proje1Field.get(j).setMaxWidth(35);
            proje1Field.get(j).setText(proje1FieldKontrol(ogrenciNo , notGirisDers.getText().toString() ));

            proje2Field.get(j).setId(ogrenciNo);
            proje2Field.get(j).setOnAction(this::notKaydetProje2);
            proje2Field.get(j).setMaxWidth(35);
            proje2Field.get(j).setText(proje2FieldKontrol(ogrenciNo , notGirisDers.getText().toString()  ));

            proje3Field.get(j).setId(ogrenciNo);
            proje3Field.get(j).setOnAction(this::notKaydetProje3);
            proje3Field.get(j).setMaxWidth(35);
            proje3Field.get(j).setText(proje3FieldKontrol(ogrenciNo , notGirisDers.getText().toString()));

            puanLabel.get(j).setText(puanHesapla( sınav1FieldKontrol(ogrenciNo , notGirisDers.getText().toString() ), sınav2FieldKontrol(ogrenciNo , notGirisDers.getText().toString()) , sınav3FieldKontrol(ogrenciNo , notGirisDers.getText().toString()) ,proje1FieldKontrol(ogrenciNo , notGirisDers.getText().toString())  ,  proje2FieldKontrol(ogrenciNo , notGirisDers.getText().toString()) ,  proje3FieldKontrol(ogrenciNo , notGirisDers.getText().toString())  ));


            Sınavnotları sınavnotları = new Sınavnotları(ogrenciNo,adSoyad,sınav1Field.get(j),sınav2Field.get(j),sınav3Field.get(j) ,proje1Field.get(j),proje2Field.get(j),proje3Field.get(j),puanLabel.get(j));
            dersNotları.add(sınavnotları);
            j = j +1;
        }
    }

    ArrayList<Sınavnotları> sınav1 = new ArrayList<>();
    ArrayList<Sınavnotları> sınav2 = new ArrayList<>();
    ArrayList<Sınavnotları> sınav3 = new ArrayList<>();


    ArrayList<Sınavnotları> proje1 = new ArrayList<>();
    ArrayList<Sınavnotları> proje2 = new ArrayList<>();
    ArrayList<Sınavnotları> proje3 = new ArrayList<>();

    private void notKaydetSınav1(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları sınavnotları  = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "sınav1");
        sınav1.add(sınavnotları);
    }

    private void notKaydetSınav2(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları sınavnotları = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "sınav2");
        sınav2.add(sınavnotları);
    }

    private void notKaydetProje1(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları projenotları = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "proje1");
        proje1.add(projenotları);
    }


    private void notKaydetSınav3(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları sınavnotları  = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "sınav3");
        sınav3.add(sınavnotları);
    }

    private void notKaydetProje3(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları projenotları = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "proje3");
        proje3.add(projenotları);
    }


    private void notKaydetProje2(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları projenotları = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "proje2");
        proje2.add(projenotları);
    }

    private String puanHesapla(String sınav1FieldKontrol, String sınav2FieldKontrol, String sınav3FieldKontrol, String proje1FieldKontrol, String proje2FieldKontrol, String proje3FieldKontrol) {
        int toplam = 0 , i = 0;
        if (!sınav1FieldKontrol.equals("")){
            toplam +=  Integer.parseInt(sınav1FieldKontrol);
            i = i+1;
        }
        if (!sınav2FieldKontrol.equals("")){
            toplam +=  Integer.parseInt(sınav2FieldKontrol);
            i = i+1;
        }
        if (!sınav3FieldKontrol.equals("")){
            toplam +=  Integer.parseInt(sınav3FieldKontrol);
            i = i+1;
        }
        if (!proje1FieldKontrol.equals("")){
            toplam +=  Integer.parseInt(proje1FieldKontrol);
            i = i+1;
        }
        if (!proje2FieldKontrol.equals("")){
            toplam +=  Integer.parseInt(proje2FieldKontrol);
            i = i+1;
        }
        if (!proje3FieldKontrol.equals("")){
            toplam +=  Integer.parseInt(proje3FieldKontrol);
            i = i+1;
        }

        if (toplam > 0){
            return String.valueOf(toplam/i);
        }

        return "";
    }

    private String proje2FieldKontrol(String ogrenciNo, String toString) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * from sınavnotları WHERE ogrenciNo = (?) and Ders = (?)");
        statement.setString(1,ogrenciNo);
        statement.setString(2,toString);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String puan =  String.valueOf(resultSet.getInt("Proje2"));
            if (puan.equals("0")){
                return "";
            }else {
                return puan;
            }
        }


        return "";
    }

    private String proje1FieldKontrol(String ogrenciNo, String toString) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * from sınavnotları WHERE ogrenciNo = (?) and Ders = (?)");
        statement.setString(1,ogrenciNo);
        statement.setString(2,toString);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String puan =  String.valueOf(resultSet.getInt("Proje1"));
            if (puan.equals("0")){
                return "";
            }else {
                return puan;
            }
        }
        return "";
    }

    private String sınav2FieldKontrol(String ogrenciNo, String toString) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * from sınavnotları WHERE ogrenciNo = (?) and Ders = (?)");
        statement.setString(1,ogrenciNo);
        statement.setString(2,toString);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String puan =  String.valueOf(resultSet.getInt("Sınav2"));
            if (puan.equals("0")){
                return "";
            }else {
                return puan;
            }
        }

        return "";
    }

    private String  sınav1FieldKontrol(String ogrenciNo, String s) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM sınavnotları WHERE OgrenciNo = (?) AND Ders = (?)");
        statement.setString(1,ogrenciNo);
        statement.setString(2,s);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String puan =  String.valueOf(resultSet.getInt("Sınav1"));
            if (puan.equals("0")){
                return "";

            }else {
                return puan;
            }
        }

        return "";
    }

    private String proje3FieldKontrol(String ogrenciNo, String toString) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * from sınavnotları WHERE ogrenciNo = (?) and Ders = (?)");
        statement.setString(1,ogrenciNo);
        statement.setString(2,toString);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String puan =  String.valueOf(resultSet.getInt("Proje3"));
            if (puan.equals("0")){
                return "";
            }else {
                return puan;
            }
        }

        return "";
    }
    private String sınav3FieldKontrol(String ogrenciNo, String toString) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * from sınavnotları WHERE ogrenciNo = (?) and Ders = (?)");
        statement.setString(1,ogrenciNo);
        statement.setString(2,toString);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            String puan =  String.valueOf(resultSet.getInt("Sınav3"));
            if (puan.equals("0")){
                return "";
            }else {
                return puan;
            }
        }

        return "";
    }


    public void notlarıKaydet() throws SQLException {
        for (int i = 0 ; i<sınav1.size();i++){
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,sınav1.get(i).ogrenciNo);
            statement.setString(2,notGirisDers.getText().toString());
            System.out.println(sınav1.get(i).ogrenciNo);
            System.out.println(notGirisDers.getText().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,?,0,0,0,0,0,0) ");
                statement1.setInt(4,sınav1.get(i).sınav1);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,sınav1.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Sınav1 = (?) WHERE OgrenciNo = (?) and Ders (?)");
                statement1.setInt(1,sınav1.get(i).sınav1);
                statement1.setString(2,sınav1.get(i).ogrenciNo);
                statement1.setString(3,notGirisiSınıfSecimi.getValue().toString());
                statement1.executeUpdate();
            }
        }

        for (int i = 0 ; i<sınav2.size();i++){
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,sınav2.get(i).ogrenciNo);
            statement.setString(2,notGirisDers.getText().toString());
            System.out.println(sınav2.get(i).ogrenciNo);
            System.out.println(notGirisDers.getText().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,?,0,0,0,0,0) ");
                System.out.println(sınav2.get(i).sınav2);
                statement1.setInt(4,sınav2.get(i).sınav2);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,sınav2.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Sınav2 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,sınav2.get(i).sınav2);
                statement1.setString(2,sınav2.get(i).ogrenciNo);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.executeUpdate();
            }

        }

        for (int i = 0 ; i<sınav3.size();i++){
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,sınav3.get(i).ogrenciNo);
            statement.setString(2,notGirisDers.getText().toString());
            System.out.println(sınav3.get(i).ogrenciNo);
            System.out.println(notGirisDers.getText().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,?,0,0,0,0) ");
                System.out.println(sınav3.get(i).sınav3);
                statement1.setInt(4,sınav3.get(i).sınav3);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,sınav3.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Sınav3 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,sınav3.get(i).sınav3);
                statement1.setString(2,sınav3.get(i).ogrenciNo);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.executeUpdate();
            }

        }

        for (int i = 0 ; i<proje1.size();i++){
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,proje1.get(i).ogrenciNo);
            statement.setString(2,notGirisDers.getText().toString());
            System.out.println(proje1.get(i).ogrenciNo);
            System.out.println(notGirisDers.getText().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,0,?,0,0,0) ");
                System.out.println(proje1.get(i).proje1);
                statement1.setInt(4,proje1.get(i).proje1);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,proje1.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Proje1 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,proje1.get(i).proje1);
                statement1.setString(2,proje1.get(i).ogrenciNo);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.executeUpdate();
            }

        }

        for (int i = 0 ; i<proje2.size();i++){PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,proje2.get(i).ogrenciNo);
            statement.setString(2,notGirisDers.getText().toString());
            System.out.println(proje2.get(i).ogrenciNo);
            System.out.println(notGirisDers.getText().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,0,0,?,0,0) ");
                System.out.println(proje2.get(i).proje2);
                statement1.setInt(4,proje2.get(i).proje2);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,proje2.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Proje2 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,proje2.get(i).sınav2);
                statement1.setString(2,proje2.get(i).ogrenciNo);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.executeUpdate();
            }
        }

        for (int i = 0 ; i<proje3.size();i++){PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,proje3.get(i).ogrenciNo);
            statement.setString(2,notGirisDers.getText().toString());
            System.out.println(proje3.get(i).ogrenciNo);
            System.out.println(notGirisDers.getText().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,0,0,0,?,0) ");
                System.out.println(proje3.get(i).proje3);
                statement1.setInt(4,proje3.get(i).proje3);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,proje3.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Proje3 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,proje3.get(i).proje3);
                statement1.setString(2,proje3.get(i).ogrenciNo);
                statement1.setString(3,notGirisDers.getText().toString());
                statement1.executeUpdate();
            }
        }



    }


    public void sınavTarihiKaydet() throws SQLException {
        String sınıfSube = sınavTarihiSınıfSecimi.getValue().toString();
        String ders = YonetimGiris.ders;
        String tur = sınavTarihiTürSecim.getValue().toString();
        String tarih = sınavTarihiSecimi.getValue().toString();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sınavtarihleri VALUES (?,?,?,?)");
        preparedStatement.setString(1,sınıfSube);
        preparedStatement.setString(2,ders);
        preparedStatement.setString(3,tur);
        preparedStatement.setString(4,tarih);
        preparedStatement.executeUpdate();

        sınavTarihiListele();



    }


    public void sınavTarihiListele() throws SQLException {
        sınavTarihleri.clear();
        sınavTarihiKaydetAnchorPane.setVisible(true);
        sınavTarihiListesi.setVisible(true);
        sınıfSubeSecimiAnchorPane2.setVisible(false);
        ogrenciBilgiAnchorPane.setVisible(false);
        sınavTarihListesiLabel.setText( "   " + sınavTarihiSınıfSecimi.getValue().toString() + " Şubesi Sınav ve Proje Tarihleri Listesi ");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sınavtarihleri WHERE (?)");
        preparedStatement.setString(1,sınavTarihiSınıfSecimi.getValue().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String ders = resultSet.getString("ders");
            String tur = resultSet.getString("tur");
            String tarih = resultSet.getString("tarih");

            Sınavnotları sınavnotları = new Sınavnotları(ders,tur,tarih);
            sınavTarihleri.add(sınavnotları);
        }
    }
    public void sınavTarihiButon(){
        sınavTarihiScroll.setVisible(true);
        notGirisScroll.setVisible(false);
        sınıfSubeSecimiAnchorPane2.setVisible(false);
        ogrenciBilgiAnchorPane.setVisible(false);

    }
    public void notİslemleriButon() throws SQLException {
        notGirisiSınıfSecimi.getItems().clear();
        notGirisScroll.setVisible(true);
        sınavTarihiScroll.setVisible(false);
        sınıfSubeSecimiAnchorPane2.setVisible(false);
        ogrenciBilgiAnchorPane.setVisible(false);
        notGirisiSınıfSecimi.getItems().addAll(dersineGirilenSınıflar());

    }
    public void ogrenciBilgiButon(){
        sınıfSubeSecimiAnchorPane2.setVisible(true);
        sınavTarihiKaydetAnchorPane.setVisible(false);
        sınavTarihiListesi.setVisible(false);
        notGirisScroll.setVisible(false);
        sınavTarihiScroll.setVisible(false);
        sınavTarihiScroll.setVisible(false);
        notGirisScroll.setVisible(false);
    }
    public void ogrenciBilgiListele() throws SQLException {
        ogrenciBilgiAnchorPane.setVisible(true);
        studentList.clear();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ogrencilistesi WHERE kayıtSınıfı = (?)");
        preparedStatement.setString(1,ogrenciBilgisiSınıfSecim.getValue().toString());
        ResultSet result = preparedStatement.executeQuery();

            while (result.next()){
                String tcNo = result.getString("kimlikNo");
                String isim = result.getString("isim");
                String babaAdı = result.getString("babaAdı");
                String telefon = result.getString("telefon");
                String dogumTarihi = result.getString("dogumTarihi");
                String soyisim = result.getString("soyisim");
                String dogumYeri = result.getString("dogumYeri");
                String adres = result.getString("adres");
                String kayıtSınıfı = result.getString("kayıtSınıfı");
                String ogrenciNo = result.getString("ogrenciNo");
                Student student = new Student(tcNo,isim,babaAdı,telefon,dogumTarihi,soyisim,dogumYeri,adres,kayıtSınıfı,ogrenciNo);
                studentList.add(student);
            }


    }

}
