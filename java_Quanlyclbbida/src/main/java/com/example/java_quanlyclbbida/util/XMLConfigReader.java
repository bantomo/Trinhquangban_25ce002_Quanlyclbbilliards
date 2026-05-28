package com.example.java_quanlyclbbida.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * Lớp tiện ích để đọc file config.xml sử dụng DOM Parser
 * Đọc giá tiền từ file XML
 */
public class XMLConfigReader {
    
    private static double giaMotGio = 50000; // Giá mặc định
    
    /**
     * Đọc giá tiền từ file config.xml
     * @return Giá tiền 1 giờ
     */
    public static double docGiaTien() {
        try {
            // Tạo DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Đọc file XML từ resources
            InputStream inputStream = XMLConfigReader.class.getResourceAsStream("/config.xml");
            if (inputStream == null) {
                System.err.println("Không tìm thấy file config.xml, sử dụng giá mặc định!");
                return giaMotGio;
            }
            
            // Parse XML
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();
            
            // Lấy giá trị từ tag <giaMotGio>
            NodeList nodeList = document.getElementsByTagName("giaMotGio");
            if (nodeList.getLength() > 0) {
                Element element = (Element) nodeList.item(0);
                String giaText = element.getTextContent();
                giaMotGio = Double.parseDouble(giaText);
                System.out.println("Đọc giá tiền từ config.xml: " + giaMotGio + " đồng/giờ");
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file config.xml: " + e.getMessage());
            e.printStackTrace();
        }
        
        return giaMotGio;
    }
}
