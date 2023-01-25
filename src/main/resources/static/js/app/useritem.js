$(document).ready(function() {
	$("#userItemListBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/useritems",
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

	$("#userItemReadBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/useritems/" + $("#userItemNo").val(),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function(data) {
				console.log(data);
				
				alert(JSON.stringify(data));
				
				$("#userItemName").val(data.itemName);
				$("#userItemDescription").val(data.description);
				
				$("#userItemPreview").empty();
				
				var str = "<img src='items/display?itemId=" + data.itemId + "&timestamp=" + new Date().getTime() + "' width='210' height='240'>";
				
				$("#userItemPreview").append(str);
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});
	
	$("#userItemResetBtn").on("click", function() {
		$("#userItemNo").val("");
		$("#userItemName").val("");
		$("#userItemDescription").val("");
	});
	
	$("#userItemDownloadBtn").on("click", function() {
		var req = new XMLHttpRequest();
		
		req.open("GET", "/useritems/download/" + $("#userItemNo").val(), true);
		req.responseType = "blob";
		  
		req.setRequestHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		  
		req.onload = function (event) {
			var responseHeader = req.getResponseHeader("Content-Disposition");
			var type = req.getResponseHeader("Content-Type");
				
			console.log("responseHeader: " + responseHeader);
			console.log("type: " + type);
			
			if(responseHeader) {
				var downloadFilename = responseHeader.substring(22, responseHeader.length - 1);
				
				var decodedDownloadFilename = decodeURIComponent(escape(downloadFilename));
				
				alert(decodedDownloadFilename); 
				  
			    var blob = req.response;
			    
			    console.log(blob.size);
			    
			    var link=document.createElement('a');
			    link.href=window.URL.createObjectURL(blob);
			    link.download = decodedDownloadFilename;
			    link.click();
			}
			else {
				var fr = new FileReader();
				fr.onload = function(e){
					console.log(e.target.result);
					
					var jsonObj = JSON.parse(e.target.result);
					
					alert(jsonObj.message);
				}
				
				fr.readAsText(req.response);
			}
			
		};

		req.send();
	});
});
