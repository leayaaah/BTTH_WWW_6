<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 10/6/2026
  Time: 5:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản Lý / Hủy Tin Tức</title>
    <!-- CSS Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5 bg-white p-4 rounded shadow-sm">
    <h2 class="text-danger mb-4 border-bottom pb-2">Quản Lý - Hủy Tin Tức</h2>

    <!-- Thanh công cụ chọn danh mục -->
    <form action="quan-ly-tin-tuc" method="get" class="row mb-4 align-items-center">
        <div class="col-md-5">
            <label class="form-label fw-bold">Lọc tin tức cần xóa theo danh mục:</label>
            <select name="maDM" class="form-select border-danger" onchange="this.form.submit()">
                <c:forEach var="dm" items="${dsDanhMuc}">
                    <option value="${dm.maDM}" ${dm.maDM == maDMDangChon ? 'selected' : ''}>
                            ${dm.tenDanhMuc}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-7 text-end mt-4">
            <a href="danh-sach-tin-tuc" class="btn btn-outline-primary px-4 me-2">Về Trang Chủ Tin Tức</a>
            <a href="tin-tuc-form" class="btn btn-success px-4">Thêm Tin Mới</a>
        </div>
    </form>

    <!-- Bảng danh sách quản lý -->
    <table class="table table-bordered table-striped table-hover align-middle">
        <thead class="table-dark text-center">
        <tr>
            <th width="8%">Mã TT</th>
            <th width="30%">Tiêu Đề</th>
            <th width="42%">Nội Dung TT</th>
            <th width="10%">Mã DM</th>
            <th width="10%">Thao Tác</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty dsTinTuc}">
                <c:forEach var="tt" items="${dsTinTuc}">
                    <tr>
                        <td class="text-center fw-bold">${tt.maTT}</td>
                        <td class="fw-semibold">${tt.tieuDe}</td>
                        <td class="text-muted text-truncate" style="max-width: 250px;">
                                ${tt.noiDungTT}
                        </td>
                        <td class="text-center">${tt.maDM}</td>
                        <td class="text-center">
                            <!-- Nút Xóa kèm xác nhận bằng Javascript -->
                            <a href="quan-ly-tin-tuc?action=delete&maTT=${tt.maTT}&maDM=${maDMDangChon}"
                               class="btn btn-sm btn-danger px-3"
                               onclick="return confirm('Bạn có chắc chắn muốn hủy bỏ tin tức này khỏi hệ thống không? Hành động này không thể hoàn tác!');">
                                Hủy
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" class="text-center text-success py-3 fw-bold">
                        Không có tin tức nào trong danh mục này để quản lý.
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

</body>
</html>
