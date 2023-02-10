package com.example.obsyeni;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class YonetimGiris {
    public static String rol;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField pass;
    @FXML
    private Button button;


    static String name , surname;
    static String tc;
    static String ders;
    boolean giris = false;

    @FXML
    protected void giris() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://mydbinstance.csgovg1hhtui.eu-central-1.rds.amazonaws.com:3306/okul_yonetim?user=admin&password=123456789");
        PreparedStatement statement = connection.prepareStatement("Select * from yonetim");
        ResultSet result = statement.executeQuery();

        while (result.next()){
            if (result.getString("Ad").equals(userName.getText())&& result.getString("Sifre").equals(pass.getText()) && result.getString("Unvan" ).equals("MÜDÜR") ){
                name = result.getString("Ad");
                surname = result.getString("Soyad");
                rol = result.getString("Unvan");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("YonetimPanel.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                break;
            }else {
                PreparedStatement statement2 = connection.prepareStatement("Select * from ogretmenlistesi");
                ResultSet result2 = statement2.executeQuery();
                while (result2.next()) {
                    if (result2.getString("İsim").equals(userName.getText()) && result2.getString("Sifre").equals(pass.getText())) {
                        giris = true;
                        name = result2.getString("İsim");
                        surname = result2.getString("Soyisim");
                        rol = "Öğretmen";
                        tc = result2.getString("TC Kimlik No");
                        System.out.println(tc);
                        ders = result2.getString("Branş");
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ogretmen.fxml"));
                        Parent root = fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        break;
                    }
                }

                if (giris == false) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Dikkat");
                    alert.setHeaderText("Hatalı Kullanıcı Adı yda Sifre");
                    alert.setContentText("Kullanıcı adı yada sifreyi hatalı girdiniz .Lütfen tekrar deneyiniz . ");
                    alert.showAndWait();
                }
            }
            break;
        }
    }
}