<%-- 成績登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績登録</h2>

			<%-- エラーメッセージ表示 --%>
			<c:if test="${not empty errors}">
				<div class="mx-3 mb-3">
					<c:forEach var="error" items="${errors}">
						<div class="text-warning">${error.value}</div>
					</c:forEach>
				</div>
			</c:if>

			<%-- STEP1: 学生番号検索フォーム --%>
			<div class="border rounded mx-3 mb-4 p-3 bg-light bg-opacity-50">
				<h3 class="h6 mb-2 text-secondary">STEP 1｜学生を選択</h3>
				<form action="TestRegist.action" method="get">
					<div class="row align-items-end g-2">
						<div class="col-8">
							<label for="no" class="form-label">学生番号</label>
							<input class="form-control" type="text" id="no" name="no"
								value="${no}" maxlength="10" placeholder="学生番号を入力してください" required />
						</div>
						<div class="col-4">
							<button class="btn btn-secondary w-100" type="submit">検索</button>
						</div>
					</div>
				</form>
			</div>

			<%-- STEP2: 学生情報確認 + 得点入力フォーム（学生が見つかった場合のみ表示） --%>
			<c:choose>
				<c:when test="${not empty student}">
					<div class="border rounded mx-3 mb-4 p-3 bg-light bg-opacity-50">
						<h3 class="h6 mb-3 text-secondary">STEP 2｜学生情報の確認</h3>
						<table class="table table-sm table-borderless mb-0">
							<tr>
								<th class="col-3 text-secondary">学生番号</th>
								<td>${student.no}</td>
							</tr>
							<tr>
								<th class="text-secondary">氏名</th>
								<td>${student.name}</td>
							</tr>
							<tr>
								<th class="text-secondary">クラス</th>
								<td>${student.classNum}</td>
							</tr>
							<tr>
								<th class="text-secondary">入学年度</th>
								<td>${student.entYear}</td>
							</tr>
						</table>
					</div>

					<div class="border rounded mx-3 mb-3 p-3">
						<h3 class="h6 mb-3 text-secondary">STEP 3｜得点を入力</h3>
						<form action="TestRegistExecute.action" method="post">
							<%-- 学生番号・学校CD・クラスを隠しフィールドで引き継ぐ --%>
							<input type="hidden" name="student_no" value="${student.no}" />
							<input type="hidden" name="school_cd" value="${student.school.cd}" />
							<input type="hidden" name="class_num" value="${student.classNum}" />

							<div class="mb-3">
								<label for="subject_cd" class="form-label">科目CD</label>
								<input class="form-control" type="text" id="subject_cd" name="subject_cd"
									value="${subject_cd}" maxlength="3" placeholder="例：001" required />
								<div class="text-warning mt-1">${errors.get("subject_cd")}</div>
							</div>

							<div class="mb-3">
								<label for="point" class="form-label">得点</label>
								<div class="input-group" style="max-width: 200px;">
									<input class="form-control" type="number" id="point" name="point"
										value="${point}" min="0" max="100" placeholder="0〜100" required />
									<span class="input-group-text">点</span>
								</div>
								<div class="text-warning mt-1">${errors.get("point")}</div>
							</div>

							<div class="mt-3">
								<button class="btn btn-primary" type="submit">登録</button>
							</div>
						</form>
					</div>
				</c:when>
				<c:when test="${not empty no and empty student}">
					<div class="mx-3 text-warning">
						学生番号「${no}」に該当する学生が見つかりませんでした。
					</div>
				</c:when>
			</c:choose>

			<div class="mt-4 mx-3">
				<a href="Menu.action">メニューへ戻る</a>
			</div>
		</section>
	</c:param>
</c:import>
