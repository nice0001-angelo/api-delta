$(document).ready(function() {
	$("#codeDetailListBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/codedetails",
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function(data) {
				console.log(data);
				
				alert(JSON.stringify(data));
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#codeDetailReadBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/codedetails/" + $("#codeGroupCode").val() + "/" + $("#codeValue").val(),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function(data) {
				console.log(data);
				
				alert(JSON.stringify(data));
				
				$("#codeGroupCode").val(data.groupCode);
				$("#codeValue").val(data.codeValue);
				$("#codeName").val(data.codeName);
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#codeDetailRegisterBtn").on("click", function() {
		var codeGroupObject = {
			groupCode : $("#codeGroupCode").val(),
			codeValue : $("#codeValue").val(),
			codeName : $("#codeName").val()
		};
		
		alert(JSON.stringify(codeGroupObject));

		$.ajax({
			type : "POST",
			url : "/codedetails",
			data : JSON.stringify(codeGroupObject),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function() {
				alert("Created");
			},
			error : function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#codeDetailDeleteBtn").on("click", function() {
		$.ajax({
			type : "DELETE",
			url : "/codedetails/" + $("#codeGroupCode").val() + "/" + $("#codeValue").val(),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function() {
				alert("Deleted");
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#codeDetailModifyBtn").on("click", function() {
		var groupCodeVal = $("#codeGroupCode").val();
		var codeValueVal = $("#codeValue").val();
		
		var codeGroupObject = {
			groupCode : groupCodeVal,
			codeValue : codeValueVal,
			codeName : $("#codeName").val()
		};

		$.ajax({
			type : "PUT",
			url : "/codedetails/" + groupCodeVal + "/" + codeValueVal,
			data : JSON.stringify(codeGroupObject),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function() {
				alert("Modified");
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});
	
	$("#codeDetailResetBtn").on("click", function() {
		$("#codeGroupCode").val("");
		$("#codeValue").val("");
		$("#codeName").val("");
	});
	
	$.getJSON("/codes/codeGroup",function(list){
		$(list).each(function(){
			var str = "<option value='" + this.value + "'>" + this.label + "</option>";
			$("#codeGroupCode").append(str);
		});
	});
});
