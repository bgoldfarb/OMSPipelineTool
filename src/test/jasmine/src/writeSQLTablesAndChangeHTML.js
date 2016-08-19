function sendRequestAndObtainResponse(){
	sendRequest.sendRequestToServlet();
}

var sendRequest = {
	sendRequestToServlet : function() {
		var orderNumber = sendRequest.obtainOrderNumberFromHTML();
		var marketCode = sendRequest.obtainMarketCodeFromHTML();
		sendRequest.checkIfMarketNumberAndOrderNumberAreValid(orderNumber, marketCode);
	},
		
	obtainOrderNumberFromHTML : function() {
		var orderNumber = $("#orderNum").val();
		return orderNumber;
	},

	obtainMarketCodeFromHTML : function() {
		var marketCode = $("#selectedMarketCode").val();
		return marketCode;
	},

	checkForValidMarketCode : function(marketCode) {
		return !(marketCode == 0);
	},

	checkForValidOrderNumber : function(orderNumber) {
		return (orderNumber.length > 0);
	},

	checkIfMarketNumberAndOrderNumberAreValid : function(orderNumber,
			marketCode) {
		var validOrderNumber = sendRequest
				.checkForValidOrderNumber(orderNumber);
		var validMarketCode = sendRequest.checkForValidMarketCode(marketCode);
		if (!validOrderNumber && !validMarketCode) {
			sendRequest.alertIfInvalidMarketCodeAndOrderNumber();
		} else if (!validMarketCode) {
			sendRequest.alertIfInvalidMarketCodeAndValidOrderNumber();
		} else if (!validOrderNumber) {
			sendRequest.alertIfInvalidOrderNumberAndValidMarketCode();
		} else {
			buttonState.changeButtonState(2);
			sendRequest.sendRequestWithOrderNumberAndMarketCode(orderNumber, marketCode);
		}
	},

	alertIfInvalidMarketCodeAndOrderNumber : function() {
		alert("Please enter a correct Order Number and Market Code.");
	},

	alertIfInvalidMarketCodeAndValidOrderNumber : function() {
		alert("Please enter the correct market code.");
	},
	alertIfInvalidOrderNumberAndValidMarketCode : function() {
		alert("Please enter an order number.");
	},
	
	sendRequestWithOrderNumberAndMarketCode : function(orderNumber, marketCode) {
		$.ajax({
			"url" : "http://localhost:8080/MainServ",
			data : {
				"orderNumber" : orderNumber,
				"marketCode" : marketCode
			},
			"error" : function(){
				alert("The order was not found in the Monarch database. Please enter a correct order number.");
				buttonState.changeButtonState(1);
				},
			"success" : function(responseData) {
				buttonState.changeButtonState(1);
				handleResponse.writeResponseTables(responseData);
			}
		});
	}
}

var buttonState = {
		changeButtonState : function(buttonState) {
			if (buttonState == 1){
				$('#find').val('Search');
			}
			else if (buttonState == 2){
				$('#find').val('Searching...');
			}
		}
}

var handleResponse = {
		writeResponseTables : function(responseData){
			var responseDataArrayLength = responseData.length;
			for (var a = 0; a < responseDataArrayLength; a++) {
				if (a == 0){
					var orderStatusArray = responseData[responseDataArrayLength - 1];
					handleResponse.createMonarchOrderStatusTable(responseData[a], orderStatusArray);
				}
				else{
					handleResponse.createEntityPipelineTables(responseData[a], a);
				}
			}
		},
		createEntityPipelineTables: function(responseData, tableVal){
			switch (tableVal) {
		
			// Create
			case 1:
				handleResponse.writeOrderCreateToHTML(responseData);
				break;
		
			// Validate
			case 2:
				handleResponse.writeOrderValidateToHTML(responseData);
				break;
		
			// Verify
			case 3:
				handleResponse.writeOrderVerifyToHTML(responseData);
				break;
		
			// Source
			case 4:
				handleResponse.writeOrderSourceToHTML(responseData);
				break;
		
			// Cancel
			case 5:
				handleResponse.writeOrderCancelToHTML(responseData);
				break;
		
			// Release
			case 6:
				handleResponse.writeOrderReleaseToHTML(responseData);
				break;
		
			// Complete
			case 7:
				handleResponse.writeOrderCompleteToHTML(responseData);
				break;
		
			default:
				break;
			}
		
		},
		
		
		
		
		
		createMonarchOrderStatusTable: function(responseData, orderStatusArray){
			var tableHTML = '<h1>';
			tableHTML += '<b>';
			tableHTML += 'Order Status';
			tableHTML += '</b>';
			tableHTML += '</h1>';
			tableHTML += '<p>'
			tableHTML += '<table style="width: 300px">';
			tableHTML += '<tr>'
			tableHTML += '<th>MARC_ORD_STS_CD</th>'
			tableHTML += '<th>STS_NM</th>'
			tableHTML += '<th>STS_QTY</th>'
			tableHTML += '<th>LST_UPDT_DTTM</th>'
			tableHTML += '<th>CRT_USER_ID</th>'
			tableHTML += '<th>LST_UPDT_USER_ID</th>'
			tableHTML += '<th>LST_UPDT_APPL_SRVR_NM</th>'
			tableHTML += '</tr>'
			for (var a = 0; a <= responseData.length - 1; a++) {
				tableHTML += '<tr>';
				tableHTML += '<td>';
				var orderStatusCode = responseData[a].MARC_ORD_STS_CD;
				tableHTML += orderStatusCode;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += handleResponse.findOrderStatusName(orderStatusCode, orderStatusArray);
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].STS_QTY;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].LST_UPDT_DTTM;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].CRT_USER_ID;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].LST_UPDT_USER_ID;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].LST_UPDT_APPL_SRVR_NM;
				tableHTML += '</td>';
				tableHTML += '</tr>';
			}
			tableHTML += '</table>';
			$('#orderStatusContents').html(tableHTML);
		},
		findOrderStatusName: function(orderStatusCode, orderStatusArray){
			var html = "";
			for (var a = 0; a < orderStatusArray.length; a++){
				if (orderStatusArray[a].ORD_STS_CD === orderStatusCode){
					html += orderStatusArray[a].STS_NM; 
				}
			}
			return html;
		},
		writeOrderCreateToHTML: function(responseData){
			var tableName = 'Order Create';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName);
			$('#orderCreateContents').html(tableHTML);
		},
		writeOrderValidateToHTML: function(responseData){
			var tableName = 'Order Validate';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName);
			$('#orderValidateContents').html(tableHTML);
		},
		writeOrderVerifyToHTML: function(responseData){
			var tableName = 'Order Verify';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName);
			$('#orderVerifyContents').html(tableHTML);
		},
		writeOrderSourceToHTML: function(responseData){
			var tableName = 'Order Source';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName);
			$('#orderSourceContents').html(tableHTML);
		},
		writeOrderCancelToHTML: function(responseData){
			var tableName = 'Order Cancel';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName);
			$('#orderCancelContents').html(tableHTML);
		},
		writeOrderReleaseToHTML: function(responseData){
			var tableName = 'Order Release';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName);
			$('#orderReleaseContents').html(tableHTML);
		},
		writeOrderCompleteToHTML: function(responseData){
			var tableName = 'Order Complete';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName);
			$('#orderCompleteContents').html(tableHTML);
		},
		
		createEntityPipelineTableHTML: function(responseData, tableName){
			var tableHTML = '<h1>';
			tableHTML += '<b>';
			tableHTML += tableName;
			tableHTML += '</b>';
			tableHTML += '</h1>';
			var entitystatus = responseData[responseData.length-1].pipelinestatus;
			tableHTML += handleResponse.createEntityPipelineStatusHTML(entitystatus);
			tableHTML += '<p>'
			tableHTML += '<table style="width: 250px">';
			tableHTML += '<tr>'
			tableHTML += '<th>SRV_NM</th>'
			tableHTML += '<th>LST_UPDT_DTTM</th>'
			tableHTML += '<th>ENTY_TYP</th>'
			tableHTML += '<th>SRV_STATUS</th>'
			tableHTML += '<th>LST_UPDT_APPL_SRVR_NM</th>'
			tableHTML += '</tr>'
			for (var a = 0; a <= responseData.length - 2; a++) {
				tableHTML += '<tr>';
				tableHTML += '<td>';
				tableHTML += responseData[a].SRV_NM;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].LST_UPDT_DTTM;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].ENTY_TYP;
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += handleResponse.checkForFailedStatus(responseData[a].SRV_STATUS);
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].LST_UPDT_APPL_SRVR_NM;
				tableHTML += '</td>';
				tableHTML += '</tr>';
			}
			tableHTML += '</table>';
			return tableHTML;
		},
		createEntityPipelineStatusHTML: function(entitystatus) {
			var entityPipelineStatusHTML = '<div class = EntityOrderStatus: >';
			entityPipelineStatusHTML += '<h2>';
			entityPipelineStatusHTML += handleResponse.checkForEntityStatusPostponed(entitystatus);
			entityPipelineStatusHTML +=	'</h2>';
			entityPipelineStatusHTML += '</div>';
			return entityPipelineStatusHTML;
		},
		checkForFailedStatus: function(serverStatus){
			var html = "";
			if(serverStatus === "FAILED"){
				html = "<span style = \"color: red; font-weight: bold;\">";
				html += serverStatus;
				html += "</span>";
			}
			else if(serverStatus === "DONE"){
				html = "<span style = \"color: green; font-weight: bold;\">";
				html += serverStatus;
				html += "</span>";
			}
			else if(serverStatus === "POSTPONED"){
				html = "<span style = \"color: orange; font-weight: bold;\">";
				html += serverStatus;
				html += "</span>";
			}
			else {
				html += serverStatus;
			}
			return html;
		},
		checkForEntityStatusPostponed: function(entitystatus){
			var html = "";
			if(entitystatus === "NONE"){
				html = "EntityStatus: ";
				html += "<span style = \"color: black; font-weight: normal;\">";
				html += entitystatus;
				html += "</span>";
			}
			else if (entitystatus === "POSTPONED"){
				html = "EntityStatus: ";
				html += "<span style = \"color: orange; font-weight: bold;\">";
				html += entitystatus;
				html += "</span>";
			}
			return html;
		}
}


module.exports = {
		sendRequest : sendRequest,
		buttonState : buttonState,
		handleResponse : handleResponse
	};
