package com.example.java_quanlyclbbida.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity HoaDon - Đại diện cho một hóa đơn thanh toán bàn bida
 * Sử dụng Hibernate để lưu vào database
 */
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ten_ban", nullable = false)
    private String tenBan;
    
    @Column(name = "thoi_gian_choi", nullable = false)
    private int thoiGianChoi; // Thời gian chơi tính bằng giây
    
    @Column(name = "tong_tien", nullable = false)
    private double tongTien;
    
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
    
    // Constructor mặc định (bắt buộc cho Hibernate)
    public HoaDon() {
        this.ngayTao = LocalDateTime.now();
    }
    
    // Constructor đầy đủ
    public HoaDon(String tenBan, int thoiGianChoi, double tongTien) {
        this.tenBan = tenBan;
        this.thoiGianChoi = thoiGianChoi;
        this.tongTien = tongTien;
        this.ngayTao = LocalDateTime.now();
    }
    
    // Getters và Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTenBan() {
        return tenBan;
    }
    
    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }
    
    public int getThoiGianChoi() {
        return thoiGianChoi;
    }
    
    public void setThoiGianChoi(int thoiGianChoi) {
        this.thoiGianChoi = thoiGianChoi;
    }
    
    public double getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    public LocalDateTime getNgayTao() {
        return ngayTao;
    }
    
    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    @Override
    public String toString() {
        return "HoaDon{" +
                "id=" + id +
                ", tenBan='" + tenBan + '\'' +
                ", thoiGianChoi=" + thoiGianChoi +
                ", tongTien=" + tongTien +
                ", ngayTao=" + ngayTao +
                '}';
    }
}
