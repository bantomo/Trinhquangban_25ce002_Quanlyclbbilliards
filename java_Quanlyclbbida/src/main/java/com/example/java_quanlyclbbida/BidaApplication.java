package com.example.java_quanlyclbbida;

import com.example.java_quanlyclbbida.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lớp chính của ứng dụng JavaFX
 * Khởi tạo và hiển thị giao diện
 */
public class BidaApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        // Load file FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
            BidaApplication.class.getResource("bida-view.fxml")
        );
        
        // Tạo Scene với kích thước phù hợp
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        
        // Cấu hình Stage
        stage.setTitle("Quản lý Câu lạc bộ Bida");
        stage.setScene(scene);
        stage.setResizable(false); // Không cho phép thay đổi kích thước
        stage.show();
        
        System.out.println("Ứng dụng đã khởi động thành công!");
    }
    
    @Override
    public void stop() {
        // Đóng Hibernate SessionFactory khi ứng dụng kết thúc
        HibernateUtil.shutdown();
        System.out.println("Ứng dụng đã đóng!");
    }
    
    public static void main(String[] args) {
        launch();
    }
}
