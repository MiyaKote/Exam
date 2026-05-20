<%-- クラス登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<section>

			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				クラス登録
			</h2>

			<form action="ClassCreateExecute.action" method="get">

				<div>

					<label for="class_num">
						クラス番号
					</label>

					<br>

					<input
						class="form-control"
						type="text"
						id="class_num"
						name="class_num"
						value="${class_num }"
						required
						maxlength="10"
						placeholder="クラス番号を入力してください" />

				</div>

				<div class="mt-2 text-warning">
					${errors.get("1") }
				</div>

				<div class="mx-auto py-2">

					<button
						class="btn btn-secondary"
						id="create-button"
						name="end">

						登録して終了

					</button>

				</div>

			</form>

			<a href="ClassList.action">戻る</a>

		</section>

	</c:param>

</c:import>