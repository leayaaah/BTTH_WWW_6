/*
 * @ (#) QuanLyFormServlet        1.0     10/6/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package fit.lythithuy.servlet;

/*
 * @description:
 * @author: Thuy, Ly Thi
 * @version: 1.0
 * @created: 10/6/2026  5:42 AM
 */
import fit.lythithuy.dao.DanhSachTinTucQuanLy;
import fit.lythithuy.model.DanhMuc;
import fit.lythithuy.model.TinTuc;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/quan-ly-tin-tuc")
public class QuanLyFormServlet extends HttpServlet {

    // Tiêm DataSource từ cấu hình server
    @Resource(name = "jdbc/quanlydanhmuc")
    private DataSource dataSource;

    private DanhSachTinTucQuanLy dao;

    @Override
    public void init() throws ServletException {
        // Khởi tạo DAO với DataSource
        dao = new DanhSachTinTucQuanLy(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        // Nếu yêu cầu là "delete" -> Thực hiện gọi DAO để xóa
        if ("delete".equals(action)) {
            String maTTParam = req.getParameter("maTT");
            String maDMParam = req.getParameter("maDM"); // Lấy maDM để xóa xong load lại đúng danh mục đó

            if (maTTParam != null && !maTTParam.trim().isEmpty()) {
                int maTT = Integer.parseInt(maTTParam);
                dao.xoaTinTuc(maTT); // Gọi hàm xóa trong DAO
            }

            // Xóa xong chuyển hướng lại về trang quản lý (giữ nguyên danh mục đang xem)
            String redirectUrl = "quan-ly-tin-tuc";
            if (maDMParam != null && !maDMParam.isEmpty()) {
                redirectUrl += "?maDM=" + maDMParam;
            }
            resp.sendRedirect(redirectUrl);
            return; // Kết thúc phương thức để không chạy tiếp đoạn code render View bên dưới
        }

        // ==========================================
        // Nếu không phải "delete" -> Hiển thị Form danh sách
        // ==========================================

        // 1. Lấy danh sách danh mục đổ lên ô chọn
        List<DanhMuc> dsDanhMuc = dao.layTatCaDanhMuc();
        req.setAttribute("dsDanhMuc", dsDanhMuc);

        // 2. Lấy mã danh mục người dùng đang xem
        String maDMParam = req.getParameter("maDM");
        int maDM = 1; // Mặc định là danh mục 1
        if (maDMParam != null && !maDMParam.isEmpty()) {
            maDM = Integer.parseInt(maDMParam);
        }

        // 3. Lấy tin tức theo danh mục
        List<TinTuc> dsTinTuc = dao.layTinTucTheoDanhMuc(maDM);
        req.setAttribute("dsTinTuc", dsTinTuc);
        req.setAttribute("maDMDangChon", maDM);

        // 4. Đẩy sang JSP
        req.getRequestDispatcher("view/QuanLyForm.jsp").forward(req, resp);
    }
}

