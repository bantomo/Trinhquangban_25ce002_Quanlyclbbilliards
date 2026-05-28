# Dự án Quản lý Câu lạc bộ Bida

Dự án thực hành Java cho sinh viên năm nhất, minh họa các khái niệm:
- JavaFX & FXML (Giao diện động)
- Multithreading (Đa luồng)
- Hibernate (ORM)
- XML Processing (DOM Parser)
- File I/O
- Dynamic UI (Tạo giao diện động)
- Collection Management (Quản lý danh sách)

## Tính năng mới ⭐

- ✅ Hỗ trợ N bàn (không giới hạn số lượng)
- ✅ Thêm bàn mới động
- ✅ Xóa bàn (khi không đang chơi)
- ✅ Sửa tên bàn (khi không đang chơi)
- ✅ Giao diện tự động wrap (FlowPane)
- ✅ ScrollPane để xem nhiều bàn

## Cấu trúc dự án

```
java_quanlyclbbida/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/java_quanlyclbbida/
│   │   │       ├── BidaApplication.java          # Main JavaFX Application
│   │   │       ├── Launcher.java                 # Entry point
│   │   │       ├── controller/
│   │   │       │   └── BidaController.java       # Controller xử lý giao diện
│   │   │       ├── model/
│   │   │       │   ├── BanBida.java              # Model bàn bida
│   │   │       │   └── HoaDon.java               # Entity Hibernate
│   │   │       ├── service/
│   │   │       │   └── HoaDonService.java        # Service xử lý hóa đơn
│   │   │       └── util/
│   │   │           ├── HibernateUtil.java        # Hibernate SessionFactory
│   │   │           └── XMLConfigReader.java      # Đọc file XML
│   │   └── resources/
│   │       ├── com/example/java_quanlyclbbida/
│   │       │   └── bida-view.fxml                # Giao diện FXML
│   │       ├── config.xml                        # Cấu hình giá tiền
│   │       └── hibernate.cfg.xml                 # Cấu hình Hibernate
├── pom.xml                                       # Maven dependencies
└── README.md
```

## Công nghệ sử dụng

1. **JavaFX & FXML**: Giao diện người dùng
2. **Multithreading**: Đếm thời gian chơi cho từng bàn
3. **Hibernate**: Lưu hóa đơn vào database H2
4. **XML (DOM)**: Đọc giá tiền từ file config.xml
5. **File I/O**: Xuất hóa đơn ra file HoaDon.txt

## Chức năng

### Quản lý bàn động
- Thêm bàn mới với tên tùy chỉnh
- Xóa bàn (khi không đang chơi)
- Sửa tên bàn (khi không đang chọi)
- Hỗ trợ N bàn không giới hạn

### Mỗi bàn hiển thị
- Tên bàn (có thể sửa)
- Trạng thái (Trống/Đang chơi)
- Thời gian đã chơi (HH:MM:SS)
- Nút "Mở bàn"
- Nút "Thanh toán"
- Nút "Sửa tên"
- Nút "Xóa bàn"

### Tính năng khác
- Tự động đếm thời gian khi bàn đang chơi
- Tính tiền dựa trên thời gian và giá từ XML
- Lưu hóa đơn vào database
- Xuất hóa đơn ra file text
- Giao diện responsive với ScrollPane

## Cách chạy

### Yêu cầu
- Java 21 hoặc cao hơn
- Maven

### Chạy ứng dụng

```bash
# Compile và chạy
mvn clean javafx:run

# Hoặc chạy từ IDE (IntelliJ IDEA, Eclipse)
# Run class: Launcher.java
```

## Giải thích code cho sinh viên

### 1. Model (BanBida.java, HoaDon.java)
- **BanBida**: Lớp đại diện cho một bàn bida với các thuộc tính cơ bản
- **HoaDon**: Entity Hibernate với các annotation JPA (@Entity, @Id, @Column)

### 2. Controller (BidaController.java)
- Xử lý sự kiện từ giao diện (nút bấm)
- Sử dụng @FXML để liên kết với FXML
- Tạo Thread để đếm thời gian (minh họa Multithreading)
- Sử dụng Platform.runLater() để cập nhật UI từ thread khác

### 3. Service (HoaDonService.java)
- Xử lý logic nghiệp vụ
- Lưu hóa đơn vào database qua Hibernate
- Xuất hóa đơn ra file text

### 4. Util (HibernateUtil.java, XMLConfigReader.java)
- **HibernateUtil**: Quản lý SessionFactory (Singleton pattern)
- **XMLConfigReader**: Đọc file XML bằng DOM Parser

### 5. FXML (bida-view.fxml)
- Định nghĩa giao diện bằng XML
- Liên kết với Controller qua fx:controller
- Liên kết các thành phần với code qua fx:id

## Điểm học tập quan trọng

### Dynamic UI Creation
```java
private VBox taoBanUI(BanBida ban) {
    VBox vbox = new VBox(10);
    // Tạo các components động
    Label lblTenBan = new Label(ban.getTenBan());
    Button btnMoBan = new Button("Mở bàn");
    btnMoBan.setOnAction(e -> handleMoBan(ban, ...));
    vbox.getChildren().addAll(lblTenBan, btnMoBan, ...);
    return vbox;
}
```

### Collection Management
```java
private List<BanBida> danhSachBan = new ArrayList<>();

// Thêm
danhSachBan.add(ban);
flowPaneBan.getChildren().add(banUI);

// Xóa
danhSachBan.remove(ban);
flowPaneBan.getChildren().remove(banUI);
```

### Multithreading
```java
Thread thread = new Thread(() -> {
    while (ban.isDangChoi()) {
        Thread.sleep(1000);
        ban.tangThoiGian();
        Platform.runLater(() -> {
            lblThoiGian.setText(ban.getThoiGianFormatted());
        });
    }
});
thread.setDaemon(true);
thread.start();
```

### Hibernate Transaction
```java
try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    transaction = session.beginTransaction();
    session.persist(hoaDon);
    transaction.commit();
} catch (Exception e) {
    if (transaction != null) transaction.rollback();
}
```

### XML DOM Parser
```java
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
Document document = builder.parse(inputStream);
NodeList nodeList = document.getElementsByTagName("giaMotGio");
```

### File I/O
```java
try (FileWriter writer = new FileWriter("HoaDon.txt", true)) {
    writer.write("Thông tin hóa đơn...");
}
```

## Mở rộng

Sinh viên có thể mở rộng dự án bằng cách:
1. Thêm nhiều bàn hơn
2. Thêm chức năng đặt bàn trước
3. Thêm báo cáo doanh thu
4. Thêm quản lý khách hàng
5. Thêm giao diện đăng nhập