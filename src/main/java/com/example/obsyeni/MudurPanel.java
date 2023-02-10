package com.example.obsyeni;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class MudurPanel implements Initializable {

    @FXML
    Button devamsızlıkKaydet;
    @FXML
    Text userName;
    @FXML
    AnchorPane ogretmenBilgiPane;

    @FXML
    Text role;

    @FXML
    AnchorPane screen;

    @FXML
    DatePicker tarihSecimi;

    @FXML
    RadioButton radioButton;

    @FXML
    Button button;

    @FXML
    VBox ogrenciKayit , ogretmenBilgi , devamsızlık;

    @FXML
    Button kaydet , okButton , obButton , yoklamaButton , ogrenciListeFiltrele ;

    @FXML
    TableView<Student> tableView;
    @FXML
    TableView<Teacher> teacherTableView;
    @FXML
    TableView<Student> yoklamaView;
    @FXML
    Button ogrenciBilgi;

    @FXML
    TableColumn<Student , String> ttcNo ,
            tisim ,
            togrno,
            tsoyisim,
            tsınıf;

    ArrayList<String> gelmeyenler = new ArrayList<>();

    @FXML
    TableColumn<Student , String> ytcNo ,
            yisim ,
            yogrno,
            ysoyisim,
            ysınıf;

    @FXML
    TableColumn<Student,RadioButton> ydevamsız;

    @FXML
    TableColumn<Teacher , String> ctcNo , cisim ,  csoyisim , cbrans , csifre , cemail;
    @FXML
    TableColumn<Student,Button> silButton;
    @FXML
    TextField tcNo , isim , babaAdı , telefon , dogumTarihi , soyisim , dogumYeri ,adres , kayıtSınıfı , ogrenciNo;
    @FXML
    TextField otcNo , oisim ,  osoyisim , obrans , osifre , oemail;
    @FXML
    private Button ogretmenButton , ogretmenKaydet;
    @FXML
    ChoiceBox sınıfSube;

    /* DERS İSLEMLERİ */
    @FXML
    ChoiceBox dersİslemleriSınıfSube , dersChoiceBox , ogretmenChoiceBox , ogrenciBilgiSınıfSube;
    @FXML
    Button dersİslemeriButon , ogretmenAtaButon , dersİslemleriListeleButon;
    @FXML
    AnchorPane sınıfSubeSecimiAnchorPane , dersOgretmeniniAtaAnchorPane;
    @FXML
    TableView<SınıfDers> sınıfaDersiOlanOgretmenler ;
    @FXML
    TableColumn<SınıfDers, String> sınıfDerstc , sınıfDersAdSoyad , sınıfDersDers;
    @FXML
    AnchorPane sınıfDersOgretmen;

    @FXML
    ScrollPane notGirisScroll , ogrenciBilgiScrollPane;

    Connection connection = DriverManager.getConnection("jdbc:mariadb://mydbinstance.csgobl1hhtui.eu-central-1.rds.amazonaws.com/okul_yonetim?user=admin&password=123456789");


    String[] ders = {"Matematik" , "Türkçe" , "Fen Bilgisi" , "Sosyal Bilgiler" , "Resim" , "Müzik", "Yabancı Dil","Beden Eğitimi ve Spor"};
    ObservableList<String> dersler = FXCollections.observableArrayList();

    /* ------------------- */

    /*  NOT İSLEMLERİ  */

    @FXML
    Button notİslemleriButton, notGirisiListeleButton , notKaydet;
    @FXML
    ChoiceBox<String> notGirisiSınıfSecimi , notGirisiDersSecimi;
    @FXML
    AnchorPane sınıfSubeSecimiAnchorPane1 , notGirisiAnchorPane;
    @FXML
    TableView<Sınavnotları>  notGirisiTableView;
    @FXML
    TableColumn<Sınavnotları,String> notGirisiOgrenciNo , norGirisiAdSoayd ;
    @FXML
    TableColumn<Sınavnotları,TextField> sınav1column , sınav2column , sınav3column , proje1column , proje2column , proje3column;
    @FXML
    TableColumn<Sınavnotları,Label> puanLabelCell;





    /* -------------------- */


    Set<String> sınıf = new HashSet<>();

    public MudurPanel() throws SQLException {
    }

    public void sınıfSube() throws SQLException {
        gelmeyenler.clear();
        sınıfSube.getItems().clear();
        dersİslemleriSınıfSube.getItems().clear();
        notGirisiSınıfSecimi.getItems().clear();
        ogrenciBilgiSınıfSube.getItems().clear();
        PreparedStatement statement = connection.prepareStatement("Select * from ogrencilistesi");
        ResultSet result = statement.executeQuery();
        while (result.next()){
            sınıf.add(result.getString("kayıtSınıfı"));
        }
        sınıfSube.getItems().addAll(sınıf);
        dersİslemleriSınıfSube.getItems().addAll(sınıf);
        ogrenciBilgiSınıfSube.getItems().addAll(sınıf);
        notGirisiSınıfSecimi.getItems().addAll(sınıf);
    }


    public void sınıfOgretretmenleriListele() throws SQLException {
        String sınıf = dersİslemleriSınıfSube.getValue().toString();
        String ders = dersChoiceBox.getValue().toString();
        String dersOgretmen = ogretmenChoiceBox.getValue().toString();
        String tc = "";

        String sql = "SELECT * FROM ogretmenlistesi WHERE CONCAT_WS(' ', `İsim`, `Soyisim`) = (?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,dersOgretmen);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            tc = result.getString("TC Kimlik NO");
        }
        System.out.println(tc);

        String sql2 = "SELECT * FROM sınıfadersiolanogretmenler WHERE KimlikNo =  (?) and Sınıf = (?)";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.setString(1,tc);
        preparedStatement2.setString(2,sınıf);
        ResultSet resultSet = preparedStatement2.executeQuery();
        if (!resultSet.next()){
            String sql1 = "INSERT INTO sınıfadersiolanogretmenler   VALUES ( ?,?,?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1,sınıf);
            preparedStatement1.setString(2,tc);
            preparedStatement1.setString(3,dersOgretmen);
            preparedStatement1.setString(4,ders);
            preparedStatement1.executeUpdate();
        }else {
            System.out.println(tc);
        }


        sınıfOgretmenleri();
    }



    Set<String> ogretmenler = new HashSet<>();

    public void ogretmenChoiceBox() throws SQLException {
        if (ogretmenler.isEmpty() == false){
            ogretmenler.clear();
            ogretmenChoiceBox.getItems().clear();
        }
        String sql = "SELECT * FROM ogretmenlistesi WHERE Branş = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,dersChoiceBox.getValue().toString());
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            ogretmenler.add(result.getString("İsim") + " " + result.getString("Soyisim"));
        }
        ogretmenChoiceBox.getItems().addAll(ogretmenler);

    }


    public void setNotGirisiListeleButton(){
    }



    ArrayList<Button> buton = new ArrayList<>();
    ArrayList<TextField> sınav1Field = new ArrayList<>();
    ArrayList<TextField> sınav2Field = new ArrayList<>();
    ArrayList<TextField> sınav3Field = new ArrayList<>();
    ArrayList<TextField> proje1Field = new ArrayList<>();
    ArrayList<TextField> proje2Field = new ArrayList<>();
    ArrayList<TextField> proje3Field = new ArrayList<>();
    ArrayList<Label> puanLabel = new ArrayList<>();



    ArrayList<RadioButton> radioButtons = new ArrayList<>();
    int i =0;
    ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
    ObservableList<Student> sınıfList = FXCollections.observableArrayList();
    ObservableList<Student> studentList = FXCollections.observableArrayList();

    ObservableList<SınıfDers> sınıfOgretmenleri = FXCollections.observableArrayList();
    ObservableList<Sınavnotları> sınavnotlarıObservableList = FXCollections.observableArrayList();

    public void notGirisiListeleButton() throws SQLException {

        sınavnotlarıObservableList.clear();
        tableView.getItems().clear();
        int j = 0;
        notGirisiAnchorPane.setVisible(true);
        PreparedStatement statement = connection.prepareStatement("Select * from ogrencilistesi WHERE kayıtSınıfı = (?) ");
        statement.setString(1,notGirisiSınıfSecimi.getValue().toString());
        ResultSet result = statement.executeQuery();


        while (result.next()){
            String ogrenciNo = result.getString("ogrenciNo");
            String adSoyad = result.getString("isim");


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
            sınav1Field.get(j).setText(sınav1FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString() ));

            sınav2Field.get(j).setId(ogrenciNo);
            sınav2Field.get(j).setOnAction(this::notKaydetSınav2);
            sınav2Field.get(j).setMaxWidth(35);
            sınav2Field.get(j).setText(sınav2FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()));

            sınav3Field.get(j).setId(ogrenciNo);
            sınav3Field.get(j).setOnAction(this::notKaydetSınav3);
            sınav3Field.get(j).setMaxWidth(35);
            sınav3Field.get(j).setText(sınav3FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()));

            proje1Field.get(j).setId(ogrenciNo);
            proje1Field.get(j).setOnAction(this::notKaydetProje1);
            proje1Field.get(j).setMaxWidth(35);
            proje1Field.get(j).setText(proje1FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()));

            proje2Field.get(j).setId(ogrenciNo);
            proje2Field.get(j).setOnAction(this::notKaydetProje2);
            proje2Field.get(j).setMaxWidth(35);
            proje2Field.get(j).setText(proje2FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()));

            proje3Field.get(j).setId(ogrenciNo);
            proje3Field.get(j).setOnAction(this::notKaydetProje3);
            proje3Field.get(j).setMaxWidth(35);
            proje3Field.get(j).setText(proje3FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()));



            puanLabel.get(j).setText(puanHesapla( sınav1FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString() ), sınav2FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()) , sınav3FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()) ,proje1FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString())  ,  proje2FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()) ,  proje3FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString())));


            if (puanLabel.get(j).getText() != ""){
                int puan = Integer.parseInt(puanHesapla( sınav1FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString() ), sınav2FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()) , sınav3FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()) ,proje1FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString())  ,  proje2FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString()) ,  proje3FieldKontrol(ogrenciNo , notGirisiDersSecimi.getValue().toString())));
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Puan = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                  statement1.setDouble(1,puan);
                statement1.setString(2,ogrenciNo);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.executeUpdate();
            }



            Sınavnotları sınavnotları = new Sınavnotları(ogrenciNo,adSoyad,sınav1Field.get(j),sınav2Field.get(j),sınav3Field.get(j) ,proje1Field.get(j),proje2Field.get(j),proje3Field.get(j),puanLabel.get(j));
            sınavnotlarıObservableList.add(sınavnotları);
            j = j +1;

        }


    }

    private String puanHesapla(String sınav1FieldKontrol, String sınav2FieldKontrol, String sınav3FieldKontrol, String proje1FieldKontrol, String proje2FieldKontrol, String proje3FieldKontrol) throws SQLException {
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
            return String.valueOf(toplam/i)  ;
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

    private String sınav1FieldKontrol(String ogrenciNo, String s) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Select * from sınavnotları WHERE ogrenciNo = (?) and Ders = (?)");
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

    private void notKaydetSınav3(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları sınavnotları  = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "sınav3");
        sınav3.add(sınavnotları);
    }
    private void notKaydetProje1(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları projenotları = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "proje1");
        proje1.add(projenotları);
    }
    private void notKaydetProje2(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları projenotları = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "proje2");
        proje2.add(projenotları);
    }
    private void notKaydetProje3(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        Sınavnotları projenotları = new Sınavnotları(textField.getId() , Integer.parseInt(textField.getText().toString()) , "proje3");
        proje3.add(projenotları);
    }
    public void notlarıKaydet() throws SQLException {
        for (int i = 0 ; i<sınav1.size();i++){
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,sınav1.get(i).ogrenciNo);
            statement.setString(2,notGirisiDersSecimi.getValue().toString());
            System.out.println(sınav1.get(i).ogrenciNo);
            System.out.println(notGirisiDersSecimi.getValue().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,?,0,0,0,0,0,0) ");
                statement1.setInt(4,sınav1.get(i).sınav1);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
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
            statement.setString(2,notGirisiDersSecimi.getValue().toString());
            System.out.println(sınav2.get(i).ogrenciNo);
            System.out.println(notGirisiDersSecimi.getValue().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,?,0,0,0,0,0) ");
                System.out.println(sınav2.get(i).sınav2);
                statement1.setInt(4,sınav2.get(i).sınav2);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,sınav2.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Sınav2 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,sınav2.get(i).sınav2);
                statement1.setString(2,sınav2.get(i).ogrenciNo);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.executeUpdate();
            }

        }

        for (int i = 0 ; i<sınav3.size();i++){
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,sınav3.get(i).ogrenciNo);
            statement.setString(2,notGirisiDersSecimi.getValue().toString());
            System.out.println(sınav3.get(i).ogrenciNo);
            System.out.println(notGirisiDersSecimi.getValue().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                        PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,?,0,0,0,0) ");
                System.out.println(sınav3.get(i).sınav3);
                statement1.setInt(4,sınav3.get(i).sınav3);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,sınav3.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Sınav3 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,sınav3.get(i).sınav3);
                statement1.setString(2,sınav3.get(i).ogrenciNo);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.executeUpdate();
            }

        }

        for (int i = 0 ; i<proje1.size();i++){
            PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,proje1.get(i).ogrenciNo);
            statement.setString(2,notGirisiDersSecimi.getValue().toString());
            System.out.println(proje1.get(i).ogrenciNo);
            System.out.println(notGirisiDersSecimi.getValue().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,0,?,0,0,0) ");
                System.out.println(proje1.get(i).proje1);
                statement1.setInt(4,proje1.get(i).proje1);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,proje1.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Proje1 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,proje1.get(i).proje1);
                statement1.setString(2,proje1.get(i).ogrenciNo);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.executeUpdate();
            }

        }

        for (int i = 0 ; i<proje2.size();i++){PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,proje2.get(i).ogrenciNo);
            statement.setString(2,notGirisiDersSecimi.getValue().toString());
            System.out.println(proje2.get(i).ogrenciNo);
            System.out.println(notGirisiDersSecimi.getValue().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,0,0,?,0,0) ");
                System.out.println(proje2.get(i).proje2);
                statement1.setInt(4,proje2.get(i).proje2);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,proje2.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Proje2 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,proje2.get(i).sınav2);
                statement1.setString(2,proje2.get(i).ogrenciNo);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.executeUpdate();
            }
        }

        for (int i = 0 ; i<proje3.size();i++){PreparedStatement statement = connection.prepareStatement("SELECT  * FROM sınavnotları WHERE OgrenciNo = (?) and Ders = (?)");
            statement.setString(1,proje3.get(i).ogrenciNo);
            statement.setString(2,notGirisiDersSecimi.getValue().toString());
            System.out.println(proje3.get(i).ogrenciNo);
            System.out.println(notGirisiDersSecimi.getValue().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO sınavnotları VALUES (?,?,?,0,0,0,0,0,?,0) ");
                System.out.println(proje3.get(i).proje3);
                statement1.setInt(4,proje3.get(i).proje3);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.setString(1,notGirisiSınıfSecimi.getValue().toString());
                statement1.setString(2,proje3.get(i).ogrenciNo);
                statement1.executeUpdate();
            }else {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE sınavnotları SET Proje3 = (?) WHERE OgrenciNo = (?) and Ders = (?)");
                statement1.setInt(1,proje3.get(i).proje3);
                statement1.setString(2,proje3.get(i).ogrenciNo);
                statement1.setString(3,notGirisiDersSecimi.getValue().toString());
                statement1.executeUpdate();
            }
        }
    }

    String ogrenciListesiSql = "Select * from ogrencilistesi ";
    public  void ogrenciListesi(){
        studentList.removeAll();
        tableView.getItems().clear();
        try {
            PreparedStatement statement = connection.prepareStatement(ogrenciListesiSql);
            ResultSet result = statement.executeQuery();
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

                buton.add(i ,new Button());
                buton.get(i).setOnAction(this::silButonAction);
                buton.get(i).setId(tcNo);

                Student student = new Student(tcNo,isim,babaAdı,telefon,dogumTarihi,soyisim,dogumYeri,adres,kayıtSınıfı,ogrenciNo , buton.get(i));
                i = i + 1;
                studentList.add(student);
            }
            statement.close();
            PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM  ogretmenlistesi");
            ResultSet result1 = statement1.executeQuery();
            while (result1.next()){
                String tcNo = result1.getString("TC Kimlik No");
                String isim = result1.getString("İsim");
                String soyisim = result1.getString("Soyisim");
                String brans = result1.getString("Branş");
                String email = result1.getString("EMail");
                String sifre = result1.getString("Sifre");

                Teacher teacher = new Teacher(tcNo,isim,soyisim,brans,email,sifre);
                teacherList.add(teacher);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void sınıfOgretmenleri() throws SQLException {
        sınıfOgretmenleri.clear();
        PreparedStatement statement = connection.prepareStatement("Select * from sınıfadersiolanogretmenler where Sınıf = (?)");
        try {
            statement.setString(1,dersİslemleriSınıfSube.getValue().toString());
        }catch (Exception e){

        }
        ResultSet result = statement.executeQuery();
        while (result.next()){
            String tcNo = result.getString("KimlikNo");
            String adSoyad = result.getString("AdSoyad");
            String girdigiDers = result.getString("GirdigiDers");

            SınıfDers sınıfDers = new SınıfDers(tcNo,adSoyad,girdigiDers);
            sınıfOgretmenleri.add(sınıfDers);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        notGirisiSınıfSecimi.getItems().addAll(sınıf);


        notGirisiDersSecimi.getItems().clear();
        for (String s : ders){
            dersler.add(s);
        }

        notGirisiDersSecimi.getItems().addAll(dersler);


        ogrenciListesi();
        tableView.setItems(studentList);
        ttcNo.setCellValueFactory(new PropertyValueFactory<Student,String>("tcNo"));
        togrno.setCellValueFactory(new PropertyValueFactory<Student,String>("ogrenciNo"));
        togrno.setStyle("-fx-alignment: CENTER;");
        tisim.setCellValueFactory(new PropertyValueFactory<Student,String>("isim"));
        tisim.setStyle("-fx-alignment: CENTER;");
        tsoyisim.setCellValueFactory(new PropertyValueFactory<Student,String>("soyisim"));
        tsoyisim.setStyle("-fx-alignment: CENTER;");
        tsınıf.setCellValueFactory(new PropertyValueFactory<Student,String>("kayıtSınıfı"));
        tsınıf.setStyle("-fx-alignment: CENTER;");
        silButton.setCellValueFactory(new PropertyValueFactory<Student,Button>("button"));


        teacherTableView.setItems(teacherList);
        ctcNo.setCellValueFactory(new PropertyValueFactory<Teacher,String>("tcNo"));
        cisim.setCellValueFactory(new PropertyValueFactory<Teacher,String>("isim"));
        csoyisim.setCellValueFactory(new PropertyValueFactory<Teacher,String>("soyisim"));
        cbrans.setCellValueFactory(new PropertyValueFactory<Teacher,String>("brans"));
        cemail.setCellValueFactory(new PropertyValueFactory<Teacher,String>("email"));

        sınıfSube.setOnAction(this::sınıfOgrenciListesi);

        yoklamaView.setItems(sınıfList);
        ysınıf.setCellValueFactory(new PropertyValueFactory<Student , String>("kayıtSınıfı"));
        ytcNo.setCellValueFactory(new PropertyValueFactory<Student,String>("tcNo"));
        yogrno.setCellValueFactory(new PropertyValueFactory<Student,String>("ogrenciNo"));
        yisim.setCellValueFactory(new PropertyValueFactory<Student,String>("isim"));
        ysoyisim.setCellValueFactory(new PropertyValueFactory<Student,String>("soyisim"));
        ydevamsız.setCellValueFactory(new PropertyValueFactory<Student , RadioButton>("radioButton"));
        sınıfaDersiOlanOgretmenler.setItems(sınıfOgretmenleri);
        sınıfDerstc.setCellValueFactory(new  PropertyValueFactory<SınıfDers,String >("sınıfDerstc"));
        sınıfDersAdSoyad.setCellValueFactory(new PropertyValueFactory<SınıfDers,String>("sınıfDersAdSoyad"));
        sınıfDersDers.setCellValueFactory(new PropertyValueFactory<SınıfDers , String>("sınıfDersDers"));


        notGirisiTableView.setItems(sınavnotlarıObservableList);
        notGirisiOgrenciNo.setCellValueFactory(new PropertyValueFactory<Sınavnotları , String>("ogrenciNo"));
        norGirisiAdSoayd.setCellValueFactory(new PropertyValueFactory<Sınavnotları , String>("adSoyad"));
        sınav1column.setCellValueFactory(new PropertyValueFactory<Sınavnotları , TextField>("sınav1TF"));
        sınav2column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("sınav2TF"));
        sınav3column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("sınav3TF"));
        proje1column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("proje1TF"));
        proje2column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("proje2TF"));
        proje3column.setCellValueFactory(new PropertyValueFactory<Sınavnotları,TextField>("proje3TF"));
        puanLabelCell.setCellValueFactory(new PropertyValueFactory<Sınavnotları,Label>("puanL"));
    }

    private void sınıfOgrenciListesi(Event event) {
        sınıfList.clear();
        i = 0;
        try {
            String sql = "SELECT * FROM ogrencilistesi WHERE kayıtSınıfı = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (sınıfSube.getValue() != null){
                preparedStatement.setString(1,sınıfSube.getValue().toString());
            }
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()){
                String tcNo = result.getString("kimlikNo");
                String isim = result.getString("isim");
                String soyisim = result.getString("soyisim");
                String kayıtSınıfı = result.getString("kayıtSınıfı");
                String ogrenciNo = result.getString("ogrenciNo");
                radioButtons.add(i ,new RadioButton());
                radioButtons.get(i).setId(tcNo);
                radioButtons.get(i).setOnAction(this::devamsızlıklar);

                Student student = new Student(kayıtSınıfı,tcNo,ogrenciNo,isim,soyisim,radioButtons.get(i));
                i = i + 1;

                sınıfList.add(student);


            }
        }catch (SQLException e){
        }
    }

    public void sınıfListe(){
        try {
            PreparedStatement statement = connection.prepareStatement("Select * from ogrencilistesi WHERE   kayıtSınıfı = ?");
            ResultSet result = statement.executeQuery();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void kaydett() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Devamsızlık Kayıt Onayı");
        alert.setHeaderText("Kayıt Onaylama");
        alert.setContentText("Devamsızlıklar kaydedilecektir onaylıyormusunuz ?");
        Optional<ButtonType> r = alert.showAndWait();
        if (r.get() == ButtonType.OK){
            for (String s : gelmeyenler){
                int devamsızlık = 0;
                PreparedStatement statement = connection.prepareStatement("Select * from ogrencilistesi where kimlikNo = (?)");
                statement.setString(1,s);
                ResultSet result = statement.executeQuery();
                while (result.next()){
                    devamsızlık = result.getInt("devamsızlık");
                }
                devamsızlık += 1;
                String sql = "UPDATE ogrencilistesi set devamsızlık = (?) where kimlikNo = (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1,devamsızlık);
                preparedStatement.setString(2,s);
                preparedStatement.executeUpdate();

                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO devamsızlık VALUES (?,?,?,?)");
                preparedStatement1.setString(1,s);
                preparedStatement1.setString(2,String.valueOf(tarihSecimi.getValue().getDayOfMonth()));
                preparedStatement1.setString(3,tarihSecimi.getValue().getMonth().toString());
                preparedStatement1.setString(4,String.valueOf(tarihSecimi.getValue().getYear()));

                preparedStatement1.executeUpdate();

            }
        }
    }
    public void silButonAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Kayıt Silme Onayı");
        alert.setContentText("Öğrenci kaydı silinecektir . Onaylıyormusunuz ? ");
        Optional<ButtonType> r = alert.showAndWait();
        if (r.get() == ButtonType.OK){
            try {
                Button buton = (Button) e.getSource();
                String sql = "DELETE FROM ogrencilistesi WHERE kimlikNo = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,buton.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                ogrenciListesi();
            }catch (Exception a){
                System.out.println("dd");
            }
        }
    }

    public void devamsızlıklar(ActionEvent e){
        RadioButton radioButton1 = (RadioButton) e.getSource();
        if (radioButton1.isSelected()){
            gelmeyenler.add(radioButton1.getId());
        }
        if(radioButton1.isSelected() == false){
            gelmeyenler.remove(radioButton1.getId());
        }
    }

    public void setUserName(){
        userName.setText(YonetimGiris.name + " " + YonetimGiris.surname);
        role.setText(YonetimGiris.rol);
    }

    public void ogrenciKayıtButtton(ActionEvent e){
        ogretmenBilgiPane.setVisible(false);
        ogrenciBilgiScrollPane.setVisible(false);
        dersOgretmeniniAtaAnchorPane.setVisible(false);
        sınıfSubeSecimiAnchorPane.setVisible(false);
        devamsızlıkKaydet.setVisible(false);
        tableView.setVisible(false);
        ogrenciKayit.setVisible(true);
        ogretmenBilgi.setVisible(false);
        teacherTableView.setVisible(false);
        devamsızlık.setVisible(false);
        yoklamaView.setVisible(false);
        sınıfDersOgretmen.setVisible(false);
        sınıfSubeSecimiAnchorPane1.setVisible(false);
        notGirisiAnchorPane.setVisible(false);
        notGirisScroll.setVisible(false);
    }

    public void ogrenciBilgiButton() throws SQLException {
        ogretmenBilgiPane.setVisible(false);
        ogrenciBilgiScrollPane.setVisible(true);
        dersOgretmeniniAtaAnchorPane.setVisible(false);
        sınıfSubeSecimiAnchorPane.setVisible(false);
        devamsızlıkKaydet.setVisible(false);
        ogrenciKayit.setVisible(false);
        tableView.setVisible(true);
        ogretmenBilgi.setVisible(false);
        teacherTableView.setVisible(false);
        devamsızlık.setVisible(false);
        yoklamaView.setVisible(false);
        sınıfDersOgretmen.setVisible(false);
        sınıfSubeSecimiAnchorPane1.setVisible(false);
        notGirisiAnchorPane.setVisible(false);
        notGirisScroll.setVisible(false);

        sınıfSube();
    }

    public void ogrenciListeFiltrele(){
        String sınıfSube =  ogrenciBilgiSınıfSube.getValue().toString();
        ogrenciListesiSql  = ogrenciListesiSql +   " WHERE kayıtSınıfı = '" + sınıfSube + "'";
        ogrenciListesi();
        ogrenciListesiSql = "Select * from ogrencilistesi ";
    }

    public void ogretmenBilgiButton(){
        ogretmenBilgiPane.setVisible(true);
        ogrenciBilgiScrollPane.setVisible(false);
        dersOgretmeniniAtaAnchorPane.setVisible(false);
        sınıfSubeSecimiAnchorPane.setVisible(false);
        devamsızlıkKaydet.setVisible(false);
        ogretmenBilgi.setVisible(true);
        teacherTableView.setVisible(true);
        ogrenciKayit.setVisible(false);
        tableView.setVisible(false);
        devamsızlık.setVisible(false);
        yoklamaView.setVisible(false);
        sınıfDersOgretmen.setVisible(false);
        sınıfSubeSecimiAnchorPane1.setVisible(false);
        notGirisiAnchorPane.setVisible(false);
        notGirisScroll.setVisible(false);
    }
    public void kaydet()  {
        try{
            String sql  = "INSERT INTO ogrencilistesi   VALUES ( ?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,tcNo.getText());
            preparedStatement.setString(2,isim.getText());
            preparedStatement.setString(3,babaAdı.getText());
            preparedStatement.setString(4,telefon.getText());
            preparedStatement.setString(5,dogumTarihi.getText());
            preparedStatement.setString(6,soyisim.getText());
            preparedStatement.setString(7,dogumYeri.getText());
            preparedStatement.setString(8,adres.getText());
            preparedStatement.setString(9,kayıtSınıfı.getText());
            preparedStatement.setString(10,ogrenciNo.getText());
            preparedStatement.setInt(11, 0);
            buton.add(i ,new Button());
            buton.get(i).setOnAction(this::silButonAction);

            Student student = new Student(tcNo.getText(),isim.getText(),babaAdı.getText(),telefon.getText(),dogumTarihi.getText(),soyisim.getText(),dogumYeri.getText(),adres.getText(),kayıtSınıfı.getText(),ogrenciNo.getText(),buton.get(i));
            i = i+1;
            studentList.add(student);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void okaydet() throws SQLException {
        String sql  = "INSERT INTO ogretmenlistesi VALUES ( ?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,otcNo.getText());
        preparedStatement.setString(2,oisim.getText());
        preparedStatement.setString(3,osoyisim.getText());
        preparedStatement.setString(4,obrans.getText());
        preparedStatement.setString(5,osifre.getText());
        preparedStatement.setString(6,oemail.getText());

        Teacher teacher = new Teacher(otcNo.getText(),oisim.getText(),osoyisim.getText(),obrans.getText(),oemail.getText(),osifre.getText());
        teacherList.add(teacher);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public void yoklamaButton() throws SQLException {
        ogretmenBilgiPane.setVisible(false);
        ogrenciBilgiScrollPane.setVisible(false);
        dersOgretmeniniAtaAnchorPane.setVisible(false);
        sınıfSubeSecimiAnchorPane.setVisible(false);
        devamsızlıkKaydet.setVisible(true);
        yoklamaView.setVisible(false);
        devamsızlık.setVisible(true);
        ogretmenBilgi.setVisible(false);
        teacherTableView.setVisible(false);
        ogrenciKayit.setVisible(false);
        tableView.setVisible(false);
        yoklamaView.setVisible(true);
        sınıfDersOgretmen.setVisible(false);
        sınıfSubeSecimiAnchorPane1.setVisible(false);
        notGirisiAnchorPane.setVisible(false);
        notGirisScroll.setVisible(false);
    }
    public void dersİslemeriButon(){
        ogretmenBilgiPane.setVisible(false);
        ogrenciBilgiScrollPane.setVisible(false);
        sınıfSubeSecimiAnchorPane.setVisible(true);
        devamsızlıkKaydet.setVisible(false);
        yoklamaView.setVisible(false);
        devamsızlık.setVisible(false);
        ogretmenBilgi.setVisible(false);
        teacherTableView.setVisible(false);
        ogrenciKayit.setVisible(false);
        tableView.setVisible(false);
        yoklamaView.setVisible(false);
        sınıfDersOgretmen.setVisible(false);
        sınıfSubeSecimiAnchorPane1.setVisible(false);
        notGirisiAnchorPane.setVisible(false);
        notGirisScroll.setVisible(false);
    }
    public void notİslemleriButton(){
        ogretmenBilgiPane.setVisible(false);
        ogrenciBilgiScrollPane.setVisible(false);
        dersOgretmeniniAtaAnchorPane.setVisible(false);
        sınıfSubeSecimiAnchorPane.setVisible(false);
        devamsızlıkKaydet.setVisible(false);
        yoklamaView.setVisible(false);
        devamsızlık.setVisible(false);
        ogretmenBilgi.setVisible(false);
        teacherTableView.setVisible(false);
        ogrenciKayit.setVisible(false);
        tableView.setVisible(false);
        yoklamaView.setVisible(false);
        sınıfDersOgretmen.setVisible(false);
        sınıfSubeSecimiAnchorPane1.setVisible(true);
        notGirisScroll.setVisible(true);
    }
    public void dersİslemleriListele() throws SQLException {
        dersChoiceBox.getItems().clear();
        dersler.clear();
        dersOgretmeniniAtaAnchorPane.setVisible(true);
        sınıfDersOgretmen.setVisible(true);
        for (String s : ders){
            dersler.add(s);
        }
        dersChoiceBox.getItems().addAll(dersler);
        sınıfOgretmenleri();
    }
}

