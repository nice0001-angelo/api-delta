$(document).ready(function() {
	function getOriginalName(fileName){
		var idx = fileName.indexOf("_") + 1;
		return fileName.substr(idx);
	}	
	
	$(".uploadedList").on("click", "span", function(event){
		$(this).parent("div").remove();
	});
	
	$("#pdsInputFile").on("change", function(event){
		console.log("change");
		
		var files = event.target.files;
		
		var file = files[0];

		console.log(file);
		
		var formData = new FormData();
		
		formData.append("file", file);
		
		$.ajax({
			url: "/pds/upload",
			data: formData,
			dataType:"text",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			processData: false,
			contentType: false,
			type: "POST",
			success: function(data){
				console.log(data);
			  
				var str = "<div><a href='/pds/download?fullName=" + data + "'>" + getOriginalName(data) + "</a><span>X</span></div>";
			  
				$(".uploadedList").append(str);
			}
		});
	});	
	
	$("#pdsItemListBtn").on("click", function() {
		$.get("/pds", function(data) {
			console.log(data);

			alert(JSON.stringify(data));
		});
	});

	$("#pdsItemReadBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/pds/" + $("#pdsItemId").val(),
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				console.log("data: " + data);
				
				alert(JSON.stringify(data));
				
				console.log("data: " + data.itemId);
				
				$("#pdsItemName").val(data.itemName);
				$("#pdsItemDescription").val(data.description);
				
				$(".uploadedList").empty();
				 
				$.getJSON("/pds/attach/"+data.itemId,function(list){
					
					$(list).each(function(){
						console.log("data : " + this);
						
						var data = this;
						
						var str = "<div><a href='/pds/download?fullName="+data+"'>" + getOriginalName(data) + "</a></div>";
						  
						$(".uploadedList").append(str);
					});
				});
				
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#pdsItemRegisterBtn").on("click", function() {
		var filenames =[];
		$(".uploadedList a").each(function(index){	
			 var value = $(this).attr("href");
			 value = value.substr(24);
			 
			 filenames[index] = value;
		});
		
		console.log("filenames = " + filenames);

		var itemObject = {
			itemId : $("#pdsItemId").val(),
			itemName : $("#pdsItemName").val(),
			description : $("#pdsItemDescription").val(),
			files : filenames
		};
		
		alert("JSON.stringify(itemObject) = " + JSON.stringify(itemObject));
		
		$.ajax({
			type : "POST",
			url : "/pds",
			data : JSON.stringify(itemObject),
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

	$("#pdsItemDeleteBtn").on("click", function() {
		$.ajax({
			type : "DELETE",
			url : "/pds/" + $("#pdsItemId").val(),
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

	$("#pdsItemModifyBtn").on("click", function() {
		console.log("modify");
		
		var itemIdVal = $("#pdsItemId").val();
		
		var filenames =[];
		$(".uploadedList a").each(function(index){	
			 var value = $(this).attr("href");
			 value = value.substr(24);
			 
			 filenames[index] = value;
		});
		
		console.log("filenames = " + filenames);

		var itemObject = {
			itemId : itemIdVal,
			itemName : $("#pdsItemName").val(),
			description : $("#pdsItemDescription").val(),
			files : filenames
		};
		
		$.ajax({
			type : "PUT",
			url : "/pds/" + itemIdVal,
			data : JSON.stringify(itemObject),
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
	
	$("#pdsItemResetBtn").on("click", function() {
		$("#pdsItemId").val("");
		$("#pdsItemName").val("");
		$("#pdsItemDescription").val("");
		
		$(".uploadedList").empty();
	});
	
});
