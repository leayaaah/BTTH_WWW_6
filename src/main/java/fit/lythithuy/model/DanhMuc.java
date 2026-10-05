/*
 * @ (#) DanhMuc        1.0     10/6/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package fit.lythithuy.model;

/*
 * @description:
 * @author: Thuy, Ly Thi
 * @version: 1.0
 * @created: 10/6/2026  5:24 AM
 */
public class DanhMuc {
    private int maDM;
    private String tenDanhMuc;

    public DanhMuc() {

    }

    public DanhMuc(int maDM, String tenDanhMuc) {
        this.maDM = maDM;
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getMaDM() {
        return maDM;
    }


    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }


    public String getTenDanhMuc() {
        return tenDanhMuc;
    }


    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}

