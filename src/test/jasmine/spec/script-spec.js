var test = require('../src/writeSQLTablesAndChangeHTML.js');

beforeEach(function() {
	 spyOn(mockJqueryReturnObject, 'val');
});

$ = function() {
	return mockJqueryReturnObject;
}

var mockResponseData = [[],[],[],[],[],[],[]];

var mockJqueryReturnObject = {
		'val': function() {},
		'html': function() {},
		'ajax': function() {}
	};


describe ("This will test if the order number and market code values from the HTML input are properly obtained.", function(){
	

	
	it("This tests if the order number value is not empty", function(){		
		var orderNumber = test.sendRequest.obtainOrderNumberFromHTML();
		expect(mockJqueryReturnObject.val).toHaveBeenCalled();
		expect(orderNumber).not.toBe(null);
	});
	
	it("This tests if the market code is not empty", function(){		
		var marketCode = test.sendRequest.obtainMarketCodeFromHTML();
		expect(mockJqueryReturnObject.val).toHaveBeenCalled();
		expect(marketCode).not.toBe(null);
	});
	
	it("This tests if the market code is valid", function(){
		var checkIfValidMarketCodeOne = test.sendRequest.checkForValidMarketCode("0");
		var checkIfValidMarketCodeTwo = test.sendRequest.checkForValidMarketCode("4");
		expect(checkIfValidMarketCodeOne).toEqual(false);
		expect(checkIfValidMarketCodeTwo).toEqual(true);
	});
	
	it("This tests if the order number is valid", function(){
		var checkIfValidOrderNumberOne = test.sendRequest.checkForValidOrderNumber("");
		var checkIfValidOrderNumberTwo = test.sendRequest.checkForValidOrderNumber("foo");
		expect(checkIfValidOrderNumberOne).toEqual(false);
		expect(checkIfValidOrderNumberTwo).toEqual(true);
	});
	
});

describe ("These will see if the proper alerts are sent if the incorrect order number or market code is provided", function(){
	beforeEach(function(){
		spyOn(test.sendRequest, 'alertIfInvalidMarketCodeAndOrderNumber');
		spyOn(test.sendRequest, 'alertIfInvalidMarketCodeAndValidOrderNumber');
		spyOn(test.sendRequest, 'alertIfInvalidOrderNumberAndValidMarketCode');
		spyOn(test.sendRequest, 'sendRequestWithOrderNumberAndMarketCode');
	});
	
	it("This will test if an Alert is shown if there is an invalid market code and order number", function(){
		var orderNumber = "";
		var marketCode = 0;
		test.sendRequest.checkIfMarketNumberAndOrderNumberAreValid(orderNumber, marketCode);
		expect(test.sendRequest.alertIfInvalidMarketCodeAndOrderNumber).toHaveBeenCalled();
	});
	
	it("This will test if an Alert is shown if there is an invalid market code and valid order number", function(){
		var orderNumber = "TYYVW4";
		var marketCode = 0;
		test.sendRequest.checkIfMarketNumberAndOrderNumberAreValid(orderNumber, marketCode);
		expect(test.sendRequest.alertIfInvalidMarketCodeAndValidOrderNumber).toHaveBeenCalled();
	});
	
	it("This will test if an Alert is shown if there is an valid market code and invalid order number", function(){
		var orderNumber = "";
		var marketCode = 4;
		test.sendRequest.checkIfMarketNumberAndOrderNumberAreValid(orderNumber, marketCode);
		expect(test.sendRequest.alertIfInvalidOrderNumberAndValidMarketCode).toHaveBeenCalled();
	});
	
	it("This will test if a request is sent if both the order number and market code are valid", function(){
		var orderNumber = "TYYVW4";
		var marketCode = 4;
		test.sendRequest.checkIfMarketNumberAndOrderNumberAreValid(orderNumber, marketCode);
		expect(test.sendRequest.sendRequestWithOrderNumberAndMarketCode).toHaveBeenCalled();
	});
	
	
/*describe("These will test to see if the ajax call was properly called", function(){

	beforeEach(function(){
		//jasmineAJAX.jasmine.Ajax.install();
	});
	
	afterEach(function(){
		//jasmineAJAX.jasmine.Ajax.uninstall();
	});
	
	it("This will test to see if an ajax call was made", function(){
		 spyOn(mockJqueryReturnObject, "ajax");
		 
		
		 var orderNumber = "TYYVW4";
		 var marketCode = 4;
		 
		 //spyOn(mockJqueryReturnObject, 'ajax');
		 test.sendRequest.sendRequestWithOrderNumberAndMarketCode(orderNumber, marketCode);
		 //expect(mockJqueryReturnObject.ajax).toHaveBeenCalled();
		 expect(mockJqueryReturnObject.ajax).toHaveBeenCalled();
		 //expect(mockJqueryReturnObject.ajax.mostRecentCall.args[0]["url"]).toEqual('http://localhost:8080/OMS_Pipeline_Web/MainServ');
		 //expect(jasmine.Ajax.requests.mostRecent().url).toBe('http://localhost:8080/OMS_Pipeline_Web/MainServ');
		
	});
});*/
	
describe("These will test functionality of the Search button", function(){

	it("This tests if search is displayed after page is loaded", function(){
		test.buttonState.changeButtonState(1);
		expect(mockJqueryReturnObject.val).toHaveBeenCalledWith('Search');
	});
	
	it("This tests if searching... is displayed when page is loading", function(){
		test.buttonState.changeButtonState(2);
		expect(mockJqueryReturnObject.val).toHaveBeenCalledWith('Searching...');
	});
});

describe("These are the tests for the table to see if the correct case number is selected", function(){
	beforeEach(function() {
	 });
	it("This will test if writeResponseTables calls createEntityPipelineTables 'responseData' times", function(){
		 spyOn(test.handleResponse, 'createEntityPipelineTables');
		 test.handleResponse.writeResponseTables(mockResponseData);
		expect(test.handleResponse.createEntityPipelineTables.calls.count()).toEqual(6);		
	});
	describe("These are the tests to make sure that createEntityPipelineTables is calling the right function",function(){
		
		it("This will test if the right writeOrderCreateToHTML is called with a given input", function(){
			spyOn(test.handleResponse, 'writeOrderCreateToHTML')
			test.handleResponse.createEntityPipelineTables(mockResponseData,1);
			expect(test.handleResponse.writeOrderCreateToHTML.calls.count()).toEqual(1);
		});
		
		it("This will test if the right writeOrderValidateToHTML is called with a given input", function(){
			spyOn(test.handleResponse, 'writeOrderValidateToHTML')
			test.handleResponse.createEntityPipelineTables(mockResponseData,2);
			expect(test.handleResponse.writeOrderValidateToHTML.calls.count()).toEqual(1);
		});
		
		it("This will test if the right writeOrderVerifyToHTML is called with a given input", function(){
			spyOn(test.handleResponse, 'writeOrderVerifyToHTML')
			test.handleResponse.createEntityPipelineTables(mockResponseData,3);
			expect(test.handleResponse.writeOrderVerifyToHTML.calls.count()).toEqual(1);
		});
		
		it("This will test if the right writeOrderSourceToHTML is called with a given input", function(){
			spyOn(test.handleResponse, 'writeOrderSourceToHTML')
			test.handleResponse.createEntityPipelineTables(mockResponseData,4);
			expect(test.handleResponse.writeOrderSourceToHTML.calls.count()).toEqual(1);
		});
		
		it("This will test if the right writeOrderCancelToHTML is called with a given input", function(){
			spyOn(test.handleResponse, 'writeOrderCancelToHTML')
			test.handleResponse.createEntityPipelineTables(mockResponseData,5);
			expect(test.handleResponse.writeOrderCancelToHTML.calls.count()).toEqual(1);
		});
		
		it("This will test if the right writeOrderReleaseToHTML is called with a given input", function(){
			spyOn(test.handleResponse, 'writeOrderReleaseToHTML')
			test.handleResponse.createEntityPipelineTables(mockResponseData,6);
			expect(test.handleResponse.writeOrderReleaseToHTML.calls.count()).toEqual(1);
		});
		
		it("This will test if the right writeOrderCompleteToHTML is called with a given input", function(){
			spyOn(test.handleResponse, 'writeOrderCompleteToHTML')
			test.handleResponse.createEntityPipelineTables(mockResponseData,7);
			expect(test.handleResponse.writeOrderCompleteToHTML.calls.count()).toEqual(1);
		});
	});
	
	
});	
	
describe("These will test each writeToHTML function with different table names",function(){
	
	beforeEach(function() {
		spyOn(mockJqueryReturnObject, 'html');
		spyOn(test.handleResponse,'createEntityPipelineTableHTML').and.returnValue("expected")
	  });

	it("This will test the writeOrderCreateToHTML function is called with the expected data",function(){
	
		test.handleResponse.writeOrderCreateToHTML(mockResponseData)
		expect(mockJqueryReturnObject.html).toHaveBeenCalledWith("expected")
	});
	
	it("This will test the writeOrderValidateToHTML function is called with the expected data",function(){
		test.handleResponse.writeOrderValidateToHTML(mockResponseData)
		expect(mockJqueryReturnObject.html).toHaveBeenCalledWith("expected")
		});	
	
	it("This will test the writeOrderVerifyToHTML function is called with the expected data",function(){

		test.handleResponse.writeOrderVerifyToHTML(mockResponseData)
		expect(mockJqueryReturnObject.html).toHaveBeenCalledWith("expected")
		});	
	
	it("This will test the writeOrderSourceToHTML function is called with the expected data",function(){
		test.handleResponse.writeOrderSourceToHTML(mockResponseData)
		expect(mockJqueryReturnObject.html).toHaveBeenCalledWith("expected")
		});	
	
	it("This will test the writeOrderCancelToHTML function is called with the expected data",function(){
		test.handleResponse.writeOrderCancelToHTML(mockResponseData)
		expect(mockJqueryReturnObject.html).toHaveBeenCalledWith("expected")
		});	
	
	it("This will test the writeOrderReleaseToHTML function is called with the expected data",function(){
		test.handleResponse.writeOrderReleaseToHTML(mockResponseData)
		expect(mockJqueryReturnObject.html).toHaveBeenCalledWith("expected")
		});	
	
	it("This will test the writeOrderCompleteToHTML function is called with the expected data",function(){

		test.handleResponse.writeOrderCompleteToHTML(mockResponseData)
		expect(mockJqueryReturnObject.html).toHaveBeenCalledWith("expected")
		});	
			
});	

describe("This will test if the checkForFailedStatus highlights the failed and done html properly",function(){
	it("This will test if DONE is properly highlighted as green",function(){
		var expectedHTML = "<span style = \"color: green; font-weight: bold;\">DONE</span>"
		var returnedHTML = test.handleResponse.checkForFailedStatus("DONE")
		expect(returnedHTML).toBe(expectedHTML);
		});	
	
	it("This will test if FAILED is properly highlighted as red",function(){
		var expectedHTML = "<span style = \"color: red; font-weight: bold;\">FAILED</span>"
		var returnedHTML = test.handleResponse.checkForFailedStatus("FAILED")
		expect(returnedHTML).toBe(expectedHTML);
		});	
	
	it("This will test if POSTPONED is properly highlighted as orange",function(){
		var expectedHTML = "<span style = \"color: orange; font-weight: bold;\">POSTPONED</span>"
		var returnedHTML = test.handleResponse.checkForFailedStatus("POSTPONED")
		expect(returnedHTML).toBe(expectedHTML);
		});	
});
	
});