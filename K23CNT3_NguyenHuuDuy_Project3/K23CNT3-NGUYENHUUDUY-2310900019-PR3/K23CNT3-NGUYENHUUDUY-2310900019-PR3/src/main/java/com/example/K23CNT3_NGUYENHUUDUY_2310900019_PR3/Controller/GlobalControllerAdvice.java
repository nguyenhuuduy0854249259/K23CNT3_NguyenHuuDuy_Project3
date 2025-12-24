package com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.Controller;

import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.entity.DanhMuc;
import com.example.K23CNT3_NGUYENHUUDUY_2310900019_PR3.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

@ControllerAdvice // Annotation này giúp áp dụng cho toàn bộ Controller
public class GlobalControllerAdvice {

    @Autowired
    private DanhMucRepository danhMucRepository;

    @ModelAttribute("dsDanhMuc") // Tên biến này sẽ được dùng trong th:each của HTML
    public List<DanhMuc> globalDanhMuc() {
        return danhMucRepository.findByParentIsNull();
    }
}
