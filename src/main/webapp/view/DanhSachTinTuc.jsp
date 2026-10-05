<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 10/6/2026
  Time: 5:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Danh Sách Tin Tức</title>
  <!-- Sử dụng Bootstrap cho Layout chung -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5 bg-white p-4 rounded shadow-sm">
  <h2 class="text-primary mb-4 border-bottom pb-2">Hệ Thống Quản Lý Tin Tức Trực Tuyến</h2>

  <!-- Thanh công cụ: Chọn danh mục & Các nút chức năng -->
  <form action="danh-sach-tin-tuc" method="get" class="row mb-4 align-items-center">
    <div class="col-md-4">
      <label class="form-label fw-bold">Xem theo danh mục:</label>
      <select name="maDM" class="form-select border-primary" onchange="this.form.submit()">
        <c:forEach var="dm" items="${dsDanhMuc}">
          <!-- So sánh maDM để giữ trạng thái Selected cho danh mục đang xem -->
          <option value="${dm.maDM}" ${dm.maDM == maDMDangChon ? 'selected' : ''}>
              ${dm.tenDanhMuc}
          </option>
        </c:forEach>
      </select>
    </div>
    <div class="col-md-8 text-end mt-4">
      <a href="tin-tuc-form" class="btn btn-success px-4 me-2">Thêm Mới Tin Tức</a>
      <a href="quan-ly-tin-tuc" class="btn btn-danger px-4">Quản Lý / Hủy Tin</a>
    </div>
  </form>

  <!-- Bảng hiển thị danh sách tin tức -->
  <table class="table table-bordered table-striped table-hover align-middle">
    <thead class="table-dark text-center">
    <tr>
      <th width="8%">Mã TT</th>
      <th width="25%">Tiêu Đề</th>
      <th width="45%">Nội Dung</th>
      <th width="22%">Liên Kết</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
      <c:when test="${not empty dsTinTuc}">
        <c:forEach var="tt" items="${dsTinTuc}">
          <tr>
            <td class="text-center fw-bold">${tt.maTT}</td>
            <td class="fw-semibold text-primary">${tt.tieuDe}</td>
            <td class="text-muted">${tt.noiDungTT}</td>
            <td>
              <a href="${tt.lienKet}" target="_blank" class="text-decoration-none">
                  ${tt.lienKet}
              </a>
            </td>
          </tr>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <tr>
          <td colspan="4" class="text-center text-danger py-3">
            Không có bài báo/tin tức nào trong danh mục này.
          </td>
        </tr>
      </c:otherwise>
    </c:choose>
    </tbody>
  </table>
</div>

</body>
</html>