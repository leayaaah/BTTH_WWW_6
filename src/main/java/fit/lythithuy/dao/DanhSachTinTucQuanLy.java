/*
 * @ (#) DanhSachTinTucQuanLy        1.0     10/6/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package fit.lythithuy.dao;

import fit.lythithuy.model.DanhMuc;
import fit.lythithuy.model.TinTuc;
import fit.lythithuy.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * @description:
 * @author: Thuy, Ly Thi
 * @version: 1.0
 * @created: 10/6/2026  5:15 AM
 */
public class DanhSachTinTucQuanLy {
    private DBUtil dbutil;
    // 1. Lấy danh sách tin tức theo danh mục
    public DanhSachTinTucQuanLy(DataSource dataSource) {
        // Khởi tạo DBUtil với DataSource
        dbutil = new DBUtil(dataSource);
    }
    public List<TinTuc> layTinTucTheoDanhMuc(int maDM) {
        List<TinTuc> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tintuc WHERE MADM = ?";

        try (Connection conn = dbutil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDM);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    danhSach.add(new TinTuc(
                            rs.getInt("MATT"),
                            rs.getString("TIEUDE"),
                            rs.getString("NOIDUNGTT"),
                            rs.getString("LIENKET"),
                            rs.getInt("MADM")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    // 2. Thêm mới tin tức
    public boolean themTinTuc(TinTuc tt) {
        String sql = "INSERT INTO tintuc (TIEUDE, NOIDUNGTT, LIENKET, MADM) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbutil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tt.getTieuDe());
            ps.setString(2, tt.getNoiDungTT());
            ps.setString(3, tt.getLienKet());
            ps.setInt(4, tt.getMaDM());

            // Trả về true nếu số dòng bị ảnh hưởng > 0 (thêm thành công)
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Xóa tin tức
    public boolean xoaTinTuc(int maTT) {
        String sql = "DELETE FROM tintuc WHERE MATT = ?";

        try (Connection conn = dbutil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTT);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<DanhMuc> layTatCaDanhMuc() {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT MADM, TENDANHMUC FROM danhmuc";

        try (Connection conn = dbutil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new DanhMuc(rs.getInt("MADM"), rs.getString("TENDANHMUC")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

