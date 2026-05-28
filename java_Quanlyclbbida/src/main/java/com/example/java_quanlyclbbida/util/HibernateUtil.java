package com.example.java_quanlyclbbida.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Lớp tiện ích để quản lý Hibernate SessionFactory
 * Sử dụng Singleton pattern để đảm bảo chỉ có 1 SessionFactory duy nhất
 */
public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    // Khởi tạo SessionFactory khi class được load
    static {
        try {
            // Đọc cấu hình từ hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            // Tạo SessionFactory
            sessionFactory = configuration.buildSessionFactory();
            
            System.out.println("Hibernate SessionFactory đã được khởi tạo thành công!");
            
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo SessionFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    
    /**
     * Lấy SessionFactory
     * @return SessionFactory instance
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * Đóng SessionFactory khi ứng dụng kết thúc
     */
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Hibernate SessionFactory đã được đóng!");
        }
    }
}
