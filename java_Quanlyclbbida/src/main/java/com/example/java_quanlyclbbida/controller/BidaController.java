package com.example.java_quanlyclbbida.controller;

import com.example.java_quanlyclbbida.model.BanBida;
import com.example.java_quanlyclbbida.model.HoaDon;
import com.example.java_quanlyclbbida.service.HoaDonService;
import com.example.java_quanlyclbbida.util.XMLConfigReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller xử lý giao diện và logic cho ứng dụng quản lý bàn bida
 * Hỗ trợ N bàn động với chức năng thêm/xóa/sửa
 */
public class BidaController {
    
    @FXML private FlowPane flowPaneBan; // Container chứa các bàn
    @FXML private Button btnThemBan;
    
    // Danh sách các bàn
    private List<BanBida> danhSachBan;
    
    // Service
    private HoaDonService hoaDonService;
    
    // Giá tiền đọc từ XML
    private double giaMotGio;
    
    // ID counter cho bàn mới
    private int nextBanId = 1;
    
    /**
     * Phương thức khởi tạo - được gọi tự động sau khi FXML load
     */
    @FXML
    public void initialize() {
        // Khởi tạo danh sách bàn
        danhSachBan = new ArrayList<>();
        
        // Khởi tạo service
        hoaDonService = new HoaDonService();
        
        // Đọc giá tiền từ file XML
        giaMotGio = XMLConfigReader.docGiaTien();
        
        // Tạo 3 bàn mặc định
        for (int i = 1; i <= 3; i++) {
            themBanMoi("Bàn " + i);
        }
        
        System.out.println("Controller đã được khởi tạo với " + danhSachBan.size() + " bàn!");
    }
    
    /**
     * Xử lý sự kiện khi nhấn nút "Thêm bàn"
     */
    @FXML
    private void handleThemBan() {
        // Hiển thị dialog nhập tên bàn
        TextInputDialog dialog = new TextInputDialog("Bàn " + nextBanId);
        dialog.setTitle("Thêm bàn mới");
        dialog.setHeaderText("Thêm bàn bida mới");
        dialog.setContentText("Nhập tên bàn:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(tenBan -> {
            if (tenBan.trim().isEmpty()) {
                hienThiThongBao("Lỗi", "Tên bàn không được để trống!", Alert.AlertType.WARNING);
                return;
            }
            themBanMoi(tenBan.trim());
            hienThiThongBao("Thành công", "Đã thêm bàn: " + tenBan, Alert.AlertType.INFORMATION);
        });
    }
    
    /**
     * Thêm một bàn mới vào danh sách và giao diện
     * @param tenBan Tên bàn
     */
    private void themBanMoi(String tenBan) {
        // Tạo model bàn mới
        BanBida ban = new BanBida(nextBanId++, tenBan);
        danhSachBan.add(ban);
        
        // Tạo giao diện cho bàn
        VBox banUI = taoBanUI(ban);
        flowPaneBan.getChildren().add(banUI);
    }
    
    /**
     * Tạo giao diện UI cho một bàn
     * @param ban Bàn cần tạo UI
     * @return VBox chứa UI của bàn
     */
    private VBox taoBanUI(BanBida ban) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: white; -fx-border-color: #3498db; " +
                     "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
        vbox.setPadding(new Insets(15));
        vbox.setPrefWidth(200);
        
        // Tên bàn
        Label lblTenBan = new Label(ban.getTenBan());
        lblTenBan.setFont(Font.font("System Bold", 18));
        lblTenBan.setStyle("-fx-text-fill: #2c3e50;");
        
        // Trạng thái
        Label lblTrangThaiText = new Label("Trạng thái:");
        lblTrangThaiText.setStyle("-fx-text-fill: #7f8c8d;");
        
        Label lblTrangThai = new Label("Trống");
        lblTrangThai.setFont(Font.font(14));
        lblTrangThai.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        
        // Thời gian
        Label lblThoiGianText = new Label("Thời gian chơi:");
        lblThoiGianText.setStyle("-fx-text-fill: #7f8c8d;");
        
        Label lblThoiGian = new Label("00:00:00");
        lblThoiGian.setFont(Font.font("System Bold", 16));
        lblThoiGian.setStyle("-fx-text-fill: #e74c3c;");
        
        // Nút Mở bàn
        Button btnMoBan = new Button("Mở bàn");
        btnMoBan.setPrefWidth(120);
        btnMoBan.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Nút Thanh toán
        Button btnThanhToan = new Button("Thanh toán");
        btnThanhToan.setPrefWidth(120);
        btnThanhToan.setDisable(true);
        btnThanhToan.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Set event handlers sau khi đã khai báo cả 2 buttons
        btnMoBan.setOnAction(e -> handleMoBan(ban, lblTrangThai, lblThoiGian, btnMoBan, btnThanhToan));
        btnThanhToan.setOnAction(e -> handleThanhToan(ban, lblTrangThai, lblThoiGian, btnMoBan, btnThanhToan));
        
        // Nút Sửa
        Button btnSua = new Button("Sửa tên");
        btnSua.setPrefWidth(120);
        btnSua.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSua.setOnAction(e -> handleSuaBan(ban, lblTenBan));
        
        // Nút Xóa
        Button btnXoa = new Button("Xóa bàn");
        btnXoa.setPrefWidth(120);
        btnXoa.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");
        btnXoa.setOnAction(e -> handleXoaBan(ban, vbox));
        
        // Thêm tất cả vào VBox
        vbox.getChildren().addAll(
            lblTenBan,
            lblTrangThaiText,
            lblTrangThai,
            lblThoiGianText,
            lblThoiGian,
            btnMoBan,
            btnThanhToan,
            btnSua,
            btnXoa
        );
        
        return vbox;
    }
    
    /**
     * Xử lý mở bàn
     */
    private void handleMoBan(BanBida ban, Label lblTrangThai, Label lblThoiGian, 
                            Button btnMoBan, Button btnThanhToan) {
        if (!ban.isDangChoi()) {
            ban.setDangChoi(true);
            ban.setThoiGianChoi(0);
            
            // Cập nhật UI
            lblTrangThai.setText("Đang chơi");
            lblTrangThai.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            btnMoBan.setDisable(true);
            btnThanhToan.setDisable(false);
            
            // Bắt đầu đếm thời gian
            batDauDemThoiGian(ban, lblThoiGian);
            
            hienThiThongBao("Thông báo", "Đã mở " + ban.getTenBan(), Alert.AlertType.INFORMATION);
        }
    }
    
    /**
     * Xử lý thanh toán
     */
    private void handleThanhToan(BanBida ban, Label lblTrangThai, Label lblThoiGian,
                                Button btnMoBan, Button btnThanhToan) {
        if (!ban.isDangChoi()) {
            hienThiThongBao("Lỗi", "Bàn này chưa được mở!", Alert.AlertType.WARNING);
            return;
        }
        
        // Dừng đếm thời gian
        ban.setDangChoi(false);
        
        // Dừng thread nếu đang chạy
        if (ban.getCountingThread() != null && ban.getCountingThread().isAlive()) {
            ban.getCountingThread().interrupt();
        }
        
        // Tính tiền
        double thoiGianGio = ban.getThoiGianChoi() / 3600.0;
        double tongTien = thoiGianGio * giaMotGio;
        
        // Tạo hóa đơn
        HoaDon hoaDon = new HoaDon(ban.getTenBan(), ban.getThoiGianChoi(), tongTien);
        
        // Lưu vào database
        boolean luuThanhCong = hoaDonService.luuHoaDon(hoaDon);
        
        // Xuất ra file
        boolean xuatThanhCong = hoaDonService.xuatHoaDonRaFile(hoaDon);
        
        // Hiển thị thông báo
        if (luuThanhCong && xuatThanhCong) {
            String thongBao = String.format(
                "Thanh toán thành công!\n\n" +
                "Bàn: %s\n" +
                "Thời gian: %s\n" +
                "Tổng tiền: %,.0f đồng\n\n" +
                "Hóa đơn đã được lưu vào database và file HoaDon.txt",
                ban.getTenBan(),
                ban.getThoiGianFormatted(),
                tongTien
            );
            hienThiThongBao("Thanh toán", thongBao, Alert.AlertType.INFORMATION);
        } else {
            hienThiThongBao("Lỗi", "Có lỗi xảy ra khi thanh toán!", Alert.AlertType.ERROR);
        }
        
        // Reset bàn
        ban.reset();
        
        // Cập nhật UI
        lblTrangThai.setText("Trống");
        lblTrangThai.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        lblThoiGian.setText("00:00:00");
        btnMoBan.setDisable(false);
        btnThanhToan.setDisable(true);
    }
    
    /**
     * Xử lý sửa tên bàn
     */
    private void handleSuaBan(BanBida ban, Label lblTenBan) {
        if (ban.isDangChoi()) {
            hienThiThongBao("Lỗi", "Không thể sửa tên khi bàn đang chơi!", Alert.AlertType.WARNING);
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog(ban.getTenBan());
        dialog.setTitle("Sửa tên bàn");
        dialog.setHeaderText("Sửa tên bàn");
        dialog.setContentText("Nhập tên mới:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(tenMoi -> {
            if (tenMoi.trim().isEmpty()) {
                hienThiThongBao("Lỗi", "Tên bàn không được để trống!", Alert.AlertType.WARNING);
                return;
            }
            ban.setTenBan(tenMoi.trim());
            lblTenBan.setText(tenMoi.trim());
            hienThiThongBao("Thành công", "Đã đổi tên thành: " + tenMoi, Alert.AlertType.INFORMATION);
        });
    }
    
    /**
     * Xử lý xóa bàn
     */
    private void handleXoaBan(BanBida ban, VBox banUI) {
        if (ban.isDangChoi()) {
            hienThiThongBao("Lỗi", "Không thể xóa bàn đang chơi!", Alert.AlertType.WARNING);
            return;
        }
        
        // Xác nhận xóa
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Xóa bàn " + ban.getTenBan());
        alert.setContentText("Bạn có chắc chắn muốn xóa bàn này?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Dừng thread nếu có
            if (ban.getCountingThread() != null && ban.getCountingThread().isAlive()) {
                ban.getCountingThread().interrupt();
            }
            
            // Xóa khỏi danh sách
            danhSachBan.remove(ban);
            
            // Xóa khỏi giao diện
            flowPaneBan.getChildren().remove(banUI);
            
            hienThiThongBao("Thành công", "Đã xóa " + ban.getTenBan(), Alert.AlertType.INFORMATION);
        }
    }
    
    /**
     * Bắt đầu đếm thời gian cho một bàn sử dụng Thread
     * @param ban Bàn cần đếm thời gian
     * @param lblThoiGian Label hiển thị thời gian
     */
    private void batDauDemThoiGian(BanBida ban, Label lblThoiGian) {
        // Tạo một luồng mới để đếm thời gian
        Thread thread = new Thread(() -> {
            while (ban.isDangChoi()) {
                try {
                    // Ngủ 1 giây
                    Thread.sleep(1000);
                    
                    // Tăng thời gian
                    ban.tangThoiGian();
                    
                    // Cập nhật giao diện (phải dùng Platform.runLater vì JavaFX không thread-safe)
                    Platform.runLater(() -> {
                        lblThoiGian.setText(ban.getThoiGianFormatted());
                    });
                    
                } catch (InterruptedException e) {
                    System.out.println("Luồng đếm thời gian bàn " + ban.getTenBan() + " bị dừng");
                    break;
                }
            }
        });
        
        // Đặt luồng là daemon để tự động dừng khi ứng dụng đóng
        thread.setDaemon(true);
        thread.start();
        
        // Lưu reference thread vào model
        ban.setCountingThread(thread);
    }
    
    /**
     * Hiển thị hộp thoại thông báo
     * @param tieuDe Tiêu đề
     * @param noiDung Nội dung
     * @param loai Loại thông báo
     */
    private void hienThiThongBao(String tieuDe, String noiDung, Alert.AlertType loai) {
        Alert alert = new Alert(loai);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }
}
