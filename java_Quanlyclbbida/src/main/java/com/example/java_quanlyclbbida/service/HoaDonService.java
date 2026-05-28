package com.example.java_quanlyclbbida.service;

import com.example.java_quanlyclbbida.model.HoaDon;
import com.example.java_quanlyclbbida.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý nghiệp vụ liên quan đến Hóa đơn
 * Bao gồm lưu vào database, xuất ra file và truy vấn
 */
public class HoaDonService {
    
    /**
     * Lưu hóa đơn vào database sử dụng Hibernate
     * @param hoaDon Hóa đơn cần lưu
     * @return true nếu lưu thành công, false nếu thất bại
     */
    public boolean luuHoaDon(HoaDon hoaDon) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Bắt đầu transaction
            transaction = session.beginTransaction();
            
            // Lưu hóa đơn
            session.persist(hoaDon);
            
            // Commit transaction
            transaction.commit();
            
            System.out.println("Đã lưu hóa đơn vào database: " + hoaDon);
            return true;
            
        } catch (Exception e) {
            // Rollback nếu có lỗi
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Lỗi khi lưu hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Lấy tất cả hóa đơn từ database
     * @return Danh sách hóa đơn
     */
    public List<HoaDon> layTatCaHoaDon() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL (Hibernate Query Language) - tương tự SQL nhưng dùng tên class
            return session.createQuery("FROM HoaDon ORDER BY ngayTao DESC", HoaDon.class)
                         .list();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Lấy hóa đơn theo tên bàn
     * @param tenBan Tên bàn cần tìm
     * @return Danh sách hóa đơn của bàn đó
     */
    public List<HoaDon> layHoaDonTheoBan(String tenBan) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM HoaDon WHERE tenBan = :tenBan ORDER BY ngayTao DESC", 
                HoaDon.class
            )
            .setParameter("tenBan", tenBan)
            .list();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy hóa đơn theo bàn: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Tính tổng doanh thu
     * @return Tổng doanh thu
     */
    public double tinhTongDoanhThu() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Double result = session.createQuery(
                "SELECT SUM(tongTien) FROM HoaDon", 
                Double.class
            ).uniqueResult();
            
            return result != null ? result : 0.0;
        } catch (Exception e) {
            System.err.println("Lỗi khi tính tổng doanh thu: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }
    
    /**
     * Đếm số hóa đơn
     * @return Số lượng hóa đơn
     */
    public long demSoHoaDon() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(*) FROM HoaDon", Long.class)
                         .uniqueResult();
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * Xuất hóa đơn ra file HoaDon.txt
     * @param hoaDon Hóa đơn cần xuất
     * @return true nếu xuất thành công, false nếu thất bại
     */
    public boolean xuatHoaDonRaFile(HoaDon hoaDon) {
        try (FileWriter writer = new FileWriter("HoaDon.txt", true)) {
            // Format thời gian
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String ngayGio = hoaDon.getNgayTao().format(formatter);
            
            // Chuyển đổi thời gian chơi sang giờ:phút:giây
            int thoiGianGiay = hoaDon.getThoiGianChoi();
            int gio = thoiGianGiay / 3600;
            int phut = (thoiGianGiay % 3600) / 60;
            int giay = thoiGianGiay % 60;
            String thoiGianFormatted = String.format("%02d:%02d:%02d", gio, phut, giay);
            
            // Viết thông tin hóa đơn
            writer.write("========================================\n");
            writer.write("         HÓA ĐƠN THANH TOÁN\n");
            writer.write("========================================\n");
            writer.write("Mã hóa đơn: " + hoaDon.getId() + "\n");
            writer.write("Tên bàn: " + hoaDon.getTenBan() + "\n");
            writer.write("Thời gian chơi: " + thoiGianFormatted + "\n");
            writer.write("Tổng tiền: " + String.format("%,.0f", hoaDon.getTongTien()) + " đồng\n");
            writer.write("Ngày giờ: " + ngayGio + "\n");
            writer.write("========================================\n\n");
            
            System.out.println("Đã xuất hóa đơn ra file HoaDon.txt");
            return true;
            
        } catch (IOException e) {
            System.err.println("Lỗi khi xuất hóa đơn ra file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
