package com.example.obsyeni;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class VBSGiris {
    @FXML
    TextField tc;
    @FXML
    TextField okulNo;
    @FXML
    Button giris;


    static String kimlikNo;
    static  String öğrenciNo;
    static  String adSoyad;


    public void giris() throws SQLException, IOException {
        kimlikNo = tc.getText().toString();
        öğrenciNo = okulNo.getText().toString();

        Connection connection = DriverManager.getConnection("jdbc:mariadb://mydbinstance.csgord1hhtui.eu-central-1.rds.amazonaws.com:3306/okul_yonetim?user=admin&password=123456789");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ogrencilistesi WHERE kimlikNo = (?) and ogrenciNo = (?)");
        preparedStatement.setString(1,kimlikNo);
        preparedStatement.setString(2,öğrenciNo);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString("isim") + " "+ resultSet.getString("soyisim"));
            adSoyad = resultSet.getString("isim") + " "+ resultSet.getString("soyisim");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Veli Bilgilendirme.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

}
