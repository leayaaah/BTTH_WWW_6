/*
 * @ (#) DanhSachTinTucServlet        1.0     10/6/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package fit.lythithuy.servlet;

/*
 * @description:
 * @author: Thuy, Ly Thi
 * @version: 1.0
 * @created: 10/6/2026  5:25 AM
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

@WebServlet("/danh-sach-tin-tuc")
public class DanhSachTinTucServlet extends HttpServlet {
    @Resource(name = "jdbc/quanlydanhmuc")
    private DataSource dataSource;

    private DanhSachTinTucQuanLy dao;

    @Override
    public void init() throws ServletException {
        dao = new DanhSachTinTucQuanLy(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy danh sách danh mục để đưa lên menu thả xuống
        List<DanhMuc> dsDanhMuc = dao.layTatCaDanhMuc();
        req.setAttribute("dsDanhMuc", dsDanhMuc);

        // 2. Lấy tham số maDM từ request (khi người dùng đổi danh mục)
        String maDMParam = req.getParameter("maDM");
        int maDM = 1; // Mặc định hiển thị danh mục 1 (Thời sự) khi mới vào trang

        if (maDMParam != null && !maDMParam.isEmpty()) {
            maDM = Integer.parseInt(maDMParam);
        }

        // 3. Lấy danh sách tin tức thuộc danh mục đang chọn
        List<TinTuc> dsTinTuc = dao.layTinTucTheoDanhMuc(maDM);
        req.setAttribute("dsTinTuc", dsTinTuc);
        req.setAttribute("maDMDangChon", maDM);

        // 4. Chuyển tiếp dữ liệu sang View (JSP)
        req.getRequestDispatcher("view/DanhSachTinTuc.jsp").forward(req, resp);
    }
}
