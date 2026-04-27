<%-- 成績参照JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<%-- 絞り込みフォーム --%>
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-5">
						<label class="form-label" for="score-f1-select">入学年度</label>
						<select class="form-select" id="score-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-5">
						<label class="form-label" for="score-f2-select">クラス</label>
						<select class="form-select" id="score-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center" style="padding-top: 1.8rem;">
						<button class="btn btn-secondary" id="filter-button">絞込み</button>
					</div>
				</div>
			</form>

			<%-- 検索結果 --%>
			<c:choose>
				<c:when test="${scores != null && scores.size() > 0}">
					<div class="px-3 mb-2">検索結果：${scores.size()}件</div>
					<table class="table table-hover table-bordered mx-3" style="width: calc(100% - 2rem);">
						<thead class="table-light">
							<tr>
								<th>入学年度</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>クラス</th>
								<th>科目</th>
								<th class="text-end">得点</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="score" items="${scores}">
								<tr>
									<td>${score.student.entYear}</td>
									<td>${score.student.no}</td>
									<td>${score.student.name}</td>
									<td>${score.student.classNum}</td>
									<td>${score.subject}</td>
									<td class="text-end">
										<%-- 得点に応じて色を変える --%>
										<c:choose>
											<c:when test="${score.point >= 80}">
												<span class="text-success fw-bold">${score.point}</span>
											</c:when>
											<c:when test="${score.point < 60}">
												<span class="text-danger">${score.point}</span>
											</c:when>
											<c:otherwise>
												${score.point}
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:when test="${scores != null && scores.size() == 0}">
					<div class="px-3 text-muted">成績データが存在しませんでした。</div>
				</c:when>
				<c:otherwise>
					<div class="px-3 text-muted">入学年度・クラスを選択して絞り込んでください。</div>
				</c:otherwise>
			</c:choose>

		</section>
	</c:param>
</c:import>
