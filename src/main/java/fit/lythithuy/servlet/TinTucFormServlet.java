/*
 * @ (#) TinTucFormServlet        1.0     10/6/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package fit.lythithuy.servlet;

/*
 * @description:
 * @author: Thuy, Ly Thi
 * @version: 1.0
 * @created: 10/6/2026  5:31 AM
 */

import fit.lythithuy.dao.DanhSachTinTucQuanLy;
import fit.lythithuy.model.TinTuc;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/tin-tuc-form")
public class TinTucFormServlet extends HttpServlet {
    @Resource(name = "jdbc/quanlydanhmuc")
    private DataSource dataSource;

    private DanhSachTinTucQuanLy dao;

    @Override
    public void init() throws ServletException {
        dao = new DanhSachTinTucQuanLy(dataSource);
    }

    // Hiển thị giao diện Form thêm mới
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đẩy danh sách danh mục sang JSP để hiển thị lên thẻ <select>
        req.setAttribute("dsDanhMuc", dao.layTatCaDanhMuc());
        req.getRequestDispatcher("view/TinTucForm.jsp").forward(req, resp);
    }

    // Xử lý dữ liệu khi người dùng bấm nút "Thêm"
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Thiết lập encoding để lấy dữ liệu tiếng Việt không bị lỗi font
        req.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ Form (Bỏ qua maTT vì DB đã set AUTO_INCREMENT)
        String tieuDe = req.getParameter("tieuDe");
        String noiDungTT = req.getParameter("noiDungTT");
        String lienKet = req.getParameter("lienKet");
        int maDM = Integer.parseInt(req.getParameter("maDM"));

        // Khởi tạo đối tượng Model và gọi DAO để thêm vào DB
        TinTuc tt = new TinTuc(tieuDe, noiDungTT, lienKet, maDM);
        boolean success = dao.themTinTuc(tt);

        if (success) {
            // Thêm thành công -> Trả về danh sách tin tức đúng của danh mục vừa thêm
            resp.sendRedirect("danh-sach-tin-tuc?maDM=" + maDM);
        } else {
            // Nếu có lỗi ở DB, báo lỗi và load lại trang
            req.setAttribute("error", "Lưu vào cơ sở dữ liệu thất bại!");
            doGet(req, resp);
        }
    }
}

