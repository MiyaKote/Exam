<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
			
			<div class="my-4 text-end">
				<%-- 登録画面へ飛ばすリンク --%>
				<a href="SubjectCreate.action" class="btn btn-success">新規登録</a>
			</div>

			<table class="table table-hover">
				<thead>
					<tr>
						<th>科目コード</th>
						<th>科目名</th>
						<th></th> <%-- 変更・削除ボタン用 --%>
					</tr>
				</thead>
				<tbody>
					<%-- ここでDaoのfilterメソッドから届いた「お盆（list）」を1つずつ取り出す --%>
					<c:forEach var="subject" items="${subjects}">
						<tr>
							<td>${subject.cd}</td>
							<td>${subject.name}</td>
							<td class="text-end">
								<%-- どの科目を変更・削除するか、コードを付けて飛ばす --%>
								<a href="SubjectUpdate.action?cd=${subject.cd}" class="btn btn-primary btn-sm">変更</a>
								<a href="SubjectDelete.action?cd=${subject.cd}" class="btn btn-danger btn-sm">削除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="mt-4">
				<a href="Menu.action">メニューに戻る</a>
			</div>
		</section>
	</c:param>
</c:import>