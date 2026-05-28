package com.example.java_quanlyclbbida.model;

/**
 * Lớp đại diện cho một bàn bida
 * Chứa thông tin về tên, trạng thái và thời gian chơi
 */
public class BanBida {
    
    private int id; // ID duy nhất cho mỗi bàn
    private String tenBan;
    private boolean dangChoi; // true = Đang chơi, false = Trống
    private int thoiGianChoi; // Thời gian chơi tính bằng giây
    private Thread countingThread; // Thread đếm thời gian
    
    public BanBida(int id, String tenBan) {
        this.id = id;
        this.tenBan = tenBan;
        this.dangChoi = false;
        this.thoiGianChoi = 0;
    }
    
    // Getters và Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTenBan() {
        return tenBan;
    }
    
    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }
    
    public boolean isDangChoi() {
        return dangChoi;
    }
    
    public void setDangChoi(boolean dangChoi) {
        this.dangChoi = dangChoi;
    }
    
    public int getThoiGianChoi() {
        return thoiGianChoi;
    }
    
    public void setThoiGianChoi(int thoiGianChoi) {
        this.thoiGianChoi = thoiGianChoi;
    }
    
    public Thread getCountingThread() {
        return countingThread;
    }
    
    public void setCountingThread(Thread countingThread) {
        this.countingThread = countingThread;
    }
    
    /**
     * Tăng thời gian chơi thêm 1 giây
     */
    public void tangThoiGian() {
        this.thoiGianChoi++;
    }
    
    /**
     * Reset bàn về trạng thái ban đầu
     */
    public void reset() {
        this.dangChoi = false;
        this.thoiGianChoi = 0;
    }
    
    /**
     * Chuyển đổi thời gian từ giây sang định dạng HH:MM:SS
     * @return Chuỗi thời gian định dạng
     */
    public String getThoiGianFormatted() {
        int gio = thoiGianChoi / 3600;
        int phut = (thoiGianChoi % 3600) / 60;
        int giay = thoiGianChoi % 60;
        return String.format("%02d:%02d:%02d", gio, phut, giay);
    }
}
