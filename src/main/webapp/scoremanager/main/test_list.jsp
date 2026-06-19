<%-- 成績参照JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<%-- 絞り込みフォーム --%>
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-3">
						<label class="form-label" for="test-f1-select">入学年度</label>
						<select class="form-select" id="test-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="test-f2-select">クラス</label>
						<select class="form-select" id="test-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="test-f3-select">科目</label>
						<select class="form-select" id="test-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_set}">
								<%-- 現在のsubject[0]と選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject[0]}" <c:if test="${subject[0]==f3}">selected</c:if>>${subject[1]}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3 text-center" style="padding-top: 1.8rem;">
						<button class="btn btn-secondary" id="filter-button">絞込み</button>
					</div>
				</div>
			</form>

			<%-- 検索結果 --%>
			<c:choose>
				<%-- 入学年度・クラス・科目のいずれかが未選択の場合 --%>
				<c:when test="${f1==0 || f2=='0' || f3=='0'}">
					<div class="px-3 text-danger">入学年度とクラスと科目を選択してください</div>
				</c:when>
				<c:when test="${tests != null && tests.size() > 0}">
					<div class="px-3 mb-2">検索結果：${tests.size()}件</div>
					<table class="table table-hover table-bordered mx-3" style="width: calc(100% - 2rem);">
						<thead class="table-light">
							<tr>
								<th>入学年度</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>クラス</th>
								<th>科目</th>
								<th>回</th>
								<th class="text-end">得点</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${tests}">
								<tr>
									<td>${test.student.entYear}</td>
									<td>${test.studentNo}</td>
									<td>${test.student.name}</td>
									<td>${test.classNum}</td>
									<td>${test.subjectName}</td>
									<td>${test.no}</td>
									<td class="text-end">
										<%-- 得点に応じて色を変える（80点以上：緑、60点未満：赤） --%>
										<c:choose>
											<c:when test="${test.point >= 80}">
												<span class="text-success fw-bold">${test.point}</span>
											</c:when>
											<c:when test="${test.point < 60}">
												<span class="text-danger">${test.point}</span>
											</c:when>
											<c:otherwise>
												${test.point}
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:when test="${tests != null && tests.size() == 0}">
					<div class="px-3 text-muted">成績データが存在しませんでした。</div>
				</c:when>
			</c:choose>

		</section>
	</c:param>
</c:import>