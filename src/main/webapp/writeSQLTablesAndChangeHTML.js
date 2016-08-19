function sendRequestAndObtainResponse(){
	sendRequest.sendRequestToServlet();
}
var showTable = false;

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
		var message = "Please enter a correct Order Number and Market Code.";
		$(".ErrorMessage").html(message);
	},

	alertIfInvalidMarketCodeAndValidOrderNumber : function() {
		var message = "Please enter the correct market code.";
		$(".ErrorMessage").html(message);
	},
	alertIfInvalidOrderNumberAndValidMarketCode : function() {
		var message = "Please enter an order number.";
		$(".ErrorMessage").html(message);
	},
	
	sendRequestWithOrderNumberAndMarketCode : function(orderNumber, marketCode) {
		$.ajax({
			"url" : "http://omspipelinestatus.gapinc.dev//MainServ",
			data : {
				"orderNumber" : orderNumber,
				"marketCode" : marketCode
			},
			"error" : function(){
				var message = "The order was not found in the Monarch database. Please enter a correct order number.";
				$(".ErrorMessage").html(message);
				buttonState.changeButtonState(1);
				},
			"success" : function(responseData) {
				$(".ErrorMessage").html("");
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
			tableHTML += '<a class = "monarchHeader" >Order Status</a>';
			tableHTML += '</b>';
			tableHTML += '</h1>';
			tableHTML += '<p>'
			tableHTML += '<div class = monarchContents>';
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
			tableHTML += '</div>'
			$('#orderStatusContents').html(tableHTML);
			$('.monarchHeader').click(function(){
				handleResponse.toggleShowTable('.monarchContents');
			});
		},
		toggleShowTable: function(tableClassName){
			$(tableClassName).slideToggle('fast');
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
			var tableID = 'orderCreate';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName, tableID);
			$('#orderCreateContents').html(tableHTML);
			handleResponse.togglePipelineTables(tableID);
			var contentsName = '.' + tableID + 'Contents';
			if (!showTable){
				handleResponse.toggleShowTable(contentsName);
			}
			
		},
		writeOrderValidateToHTML: function(responseData){
			var tableName = 'Order Validate';
			var tableID = 'orderValidate';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName, tableID);
			$('#orderValidateContents').html(tableHTML);
			handleResponse.togglePipelineTables(tableID);
			var contentsName = '.' + tableID + 'Contents';
			if (!showTable){
				handleResponse.toggleShowTable(contentsName);
			}
		},
		writeOrderVerifyToHTML: function(responseData){
			var tableName = 'Order Verify';
			var tableID = 'orderVerify';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName, tableID);
			$('#orderVerifyContents').html(tableHTML);
			handleResponse.togglePipelineTables(tableID);
			var contentsName = '.' + tableID + 'Contents';
			if (!showTable){
				handleResponse.toggleShowTable(contentsName);
			}
		},
		writeOrderSourceToHTML: function(responseData){
			var tableName = 'Order Source';
			var tableID = 'orderSource';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName, tableID);
			$('#orderSourceContents').html(tableHTML);
			var contentsName = '.' + tableID + 'Contents';
			handleResponse.togglePipelineTables(tableID);
			if (!showTable){
				handleResponse.toggleShowTable(contentsName);
			}
		},
		writeOrderCancelToHTML: function(responseData){
			var tableName = 'Order Cancel';
			var tableID = 'orderCancel';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName, tableID);
			$('#orderCancelContents').html(tableHTML);
			handleResponse.togglePipelineTables(tableID);
			var contentsName = '.' + tableID + 'Contents';
			if (!showTable){
				handleResponse.toggleShowTable(contentsName);
			}
		},
		writeOrderReleaseToHTML: function(responseData){
			var tableName = 'Order Release';
			var tableID = 'orderRelease';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName, tableID);
			$('#orderReleaseContents').html(tableHTML);
			handleResponse.togglePipelineTables(tableID);
			var contentsName = '.' + tableID + 'Contents';
			if (!showTable){
				handleResponse.toggleShowTable(contentsName);
			}
		},
		writeOrderCompleteToHTML: function(responseData){
			var tableName = 'Order Complete';
			var tableID = 'orderComplete';
			var tableHTML = handleResponse.createEntityPipelineTableHTML(responseData,
					tableName, tableID);
			$('#orderCompleteContents').html(tableHTML);
			handleResponse.togglePipelineTables(tableID);
			var contentsName = '.' + tableID + 'Contents';
			if (!showTable){
				handleResponse.toggleShowTable(contentsName);
			}
		},
		togglePipelineTables: function(tableID){
			var headerName = '.' + tableID + 'Header';
			var contentsName = '.' + tableID + 'Contents';
			$(headerName).click(function(){
				handleResponse.toggleShowTable(contentsName);
			});
		},
		createEntityPipelineTableHTML: function(responseData, tableName, tableID){
			showTable = false;
			var tableHTML = '<h1>';
			tableHTML += '<b>';
			tableHTML += '<a class = "' + tableID + 'Header">';
			tableHTML += tableName;
			tableHTML += "</a>";
			tableHTML += '</b>';
			tableHTML += '</h1>';
			var entitystatus = responseData[responseData.length-1].pipelinestatus;
			tableHTML += handleResponse.createEntityPipelineStatusHTML(entitystatus);
			tableHTML += '<p>'
			tableHTML += '<div class = ' + tableID + 'Contents>';
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
				if (responseData[a].SRV_STATUS === "FAILED" || responseData[a].SRV_STATUS === "POSTPONED"){
					showTable = true;
				}
				var serverStatus = handleResponse.checkForFailedStatus(responseData[a].SRV_STATUS);
				tableHTML += handleResponse.checkForFailedStatus(serverStatus);
				tableHTML += '</td>';
				tableHTML += '<td>';
				tableHTML += responseData[a].LST_UPDT_APPL_SRVR_NM;
				tableHTML += '</td>';
				tableHTML += '</tr>';
			}
			tableHTML += '</table>';
			tableHTML += '</div>'
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
			else if (entitystatus === "IN_PROGRESS"){
                		html = "EntityStatus: ";
                html += "<span style = \"color: blue; font-weight: bold;\">";
                html += entitystatus;
                html += "</span>";
            }	
else if (entitystatus === "FAILED"){
                html = "EntityStatus: ";
                html += "<span style = \"color: red; font-weight: bold;\">";
                html += entitystatus;
                html += "</span>";
            }
            else if (entitystatus === "CONFIRMED"){
                html = "EntityStatus: ";
                html += "<span style = \"color: green; font-weight: bold;\">";
                html += entitystatus;
                html += "</span>";
            }
	    else if (entitystatus === "DONE"){
		html = "EntityStatus: ";
		html += "<span style = \"color: #006990; font-weight: bold;\">";
		html += entitystatus;
		html += "</span>";
		}
            else{
                    html = "EntityStatus: ";
                    html += "<span style = \"color: black; font-weight: normal;\">";
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
