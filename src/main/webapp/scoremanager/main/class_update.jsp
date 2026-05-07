<%-- クラス情報変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp" >

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<section>

			<h2
				class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">

				クラス情報変更

			</h2>

			<form action="ClassUpdateExecute.action" method="get">

				<input
					type="hidden"
					name="old_class_num"
					value="${class_num }" />

				<div class="mx-auto py-2">

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
						maxlength="10" />

				</div>

				<div class="mx-auto py-2">

					<input
						class="btn btn-primary"
						type="submit"
						name="update"
						value="変更"/>

				</div>

			</form>

			<a href="ClassList.action">

				戻る

			</a>

		</section>

	</c:param>

</c:import>