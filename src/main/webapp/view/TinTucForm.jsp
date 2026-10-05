<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 10/6/2026
  Time: 5:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Thêm Mới Tin Tức</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8 bg-white p-4 rounded shadow-sm">
      <h3 class="text-success mb-4 border-bottom pb-2">Thêm Mới Tin Tức</h3>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <form action="tin-tuc-form" method="post" onsubmit="return validateForm()">
        <!-- Mã TT (Bắt buộc nhập theo đề bài, dù DB tự tăng) -->
        <div class="mb-3">
          <label class="form-label fw-bold">Mã TT:</label>
          <input type="text" class="form-control" name="maTT" id="maTT" placeholder="Nhập mã tin tức (bắt buộc)">
        </div>

        <div class="mb-3">
          <label class="form-label fw-bold">Tiêu Đề:</label>
          <input type="text" class="form-control" name="tieuDe" id="tieuDe" placeholder="Nhập tiêu đề tin tức">
        </div>

        <div class="mb-3">
          <label class="form-label fw-bold">Liên Kết:</label>
          <input type="text" class="form-control" name="lienKet" id="lienKet" placeholder="Ví dụ: http://news.vn/tin-moi">
          <small class="text-danger" id="errLienKet"></small>
        </div>

        <div class="mb-3">
          <label class="form-label fw-bold">Thông tin danh mục:</label>
          <select name="maDM" id="maDM" class="form-select">
            <option value="">-- Chọn danh mục --</option>
            <c:forEach var="dm" items="${dsDanhMuc}">
              <option value="${dm.maDM}">${dm.tenDanhMuc}</option>
            </c:forEach>
          </select>
        </div>

        <div class="mb-3">
          <label class="form-label fw-bold">Nội Dung (Tối đa 255 ký tự):</label>
          <textarea class="form-control" name="noiDungTT" id="noiDungTT" rows="4"></textarea>
          <small class="text-danger" id="errNoiDung"></small>
        </div>

        <div class="text-end">
          <a href="danh-sach-tin-tuc" class="btn btn-secondary px-4 me-2">Quay Lại</a>
          <button type="submit" class="btn btn-success px-4">Thêm Tin Tức</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Script kiểm tra dữ liệu phía Client bằng Regular Expression -->
<script>
  function validateForm() {
    // Reset thông báo lỗi
    document.getElementById("errLienKet").innerText = "";
    document.getElementById("errNoiDung").innerText = "";

    // Lấy giá trị các ô nhập liệu
    var maTT = document.getElementById("maTT").value.trim();
    var tieuDe = document.getElementById("tieuDe").value.trim();
    var lienKet = document.getElementById("lienKet").value.trim();
    var maDM = document.getElementById("maDM").value.trim();
    var noiDung = document.getElementById("noiDungTT").value.trim();

    // 1. Kiểm tra bắt buộc nhập (Required)
    if (maTT === "" || tieuDe === "" || lienKet === "" || maDM === "" || noiDung === "") {
      alert("Vui lòng nhập đầy đủ: Mã TT, Tiêu đề, Liên kết, Nội dung và Danh mục!");
      return false;
    }

    // 2. Regular Expression: Liên kết bắt đầu bởi http://
    var regexLienKet = /^http:\/\/.*/;
    if (!regexLienKet.test(lienKet)) {
      document.getElementById("errLienKet").innerText = "Liên kết không hợp lệ! Phải bắt đầu bằng http://";
      document.getElementById("lienKet").focus();
      return false;
    }

    // 3. Regular Expression: Nội dung không quá 255 ký tự
    var regexNoiDung = /^[\s\S]{1,255}$/;
    if (!regexNoiDung.test(noiDung)) {
      document.getElementById("errNoiDung").innerText = "Nội dung không hợp lệ hoặc đã vượt quá giới hạn 255 ký tự!";
      document.getElementById("noiDungTT").focus();
      return false;
    }

    return true; // Dữ liệu hợp lệ -> Cho phép Submit Form
  }
</script>

</body>
</html>