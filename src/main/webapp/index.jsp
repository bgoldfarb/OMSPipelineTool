<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OMS Pipeline</title>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
   <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel = "stylesheet" type="text/css" href="stylesheet.css" media="screen" />
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:700,400' rel='stylesheet' type='text/css'>
	<script src="writeSQLTablesAndChangeHTML.js" type="text/javascript" ></script>

</head>


<body>
	<div id="Input_Details">
		<div class="OrderNumsAndMarketCode">
			<h1>Pipeline Status by Order # and Product Code</h1>
			<div>
				Order #:<br> <input id=orderNum type="text" name="orderNumber">

				<br></br> Market Code:
				<div class="MarketCodeTextField">
					<select id="selectedMarketCode" name="marketCode">
						<option selected="selected" value = '0'>Select Market Code</option>
						<option value="1">EU</option>
						<option value="2">JP</option>
						<option value="3">CA</option>
						<option value="4">US</option>
					</select>
				</div>
				<br></br> <input type="button" id='find' value='Search'
					onclick="sendRequestAndObtainResponse()">
			</div>
			<br></br>
			<div class ="ErrorMessage"></div>
		</div>
	</div>
	<br></br>
	<div id="contents">
	<div id="orderStatusContents"></div>
	<div id="orderCreateContents"></div>
	<div id="orderValidateContents"></div>
	<div id="orderVerifyContents"></div>
	<div id="orderSourceContents"></div>
	<div id="orderCancelContents"></div>
	<div id="orderReleaseContents"></div>
	<div id="orderCompleteContents"></div>
</div>


	

</body>
</html>
