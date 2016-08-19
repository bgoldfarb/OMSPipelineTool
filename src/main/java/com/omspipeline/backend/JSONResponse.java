package com.omspipeline.backend;

import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.omspipeline.springboot.OMSPipelineRESTController;

public class JSONResponse extends SQLConnect {

	private JSONArray responseArray;
	private ResultSetToJSONConverter converter;
	private SQLConnect sc;

	public JSONArray getResponseArray() {
		return responseArray;
	}

	protected void setResponseArray(JSONArray responseArray) {
		this.responseArray = responseArray;
	}

	protected ResultSetToJSONConverter getConverter() {
		return converter;
	}

	protected void setConverter(ResultSetToJSONConverter converter) {
		this.converter = converter;
	}

	protected SQLConnect getSc() {
		return sc;
	}

	protected void setSc(SQLConnect sc) {
		this.sc = sc;
	}

	public JSONResponse() {
		super();
	}

	public void obtainJSONResponse() throws JSONException, SQLException {
		setUpJSONResponseFields();
		if (!sc.getEmptyMonarchDB()) {
			createJSONResponseArray();
		}
		this.sendResponseToServlet(OMSPipelineRESTController.out);
	}

	protected void setUpJSONResponseFields() {
		responseArray = new JSONArray();
		converter = new ResultSetToJSONConverter();
		sc = new SQLConnect();
	}

	protected void createJSONResponseArray() {
		try {
			putMonarchJSONArrayIntoResponseArray();
			putCreateJSONArrayIntoResponseArray();
			putValidateJSONArrayIntoResponseArray();
			putVerifyJSONArrayIntoResponseArray();
			putSourceJSONArrayIntoResponseArray();
			putCancelJSONArrayIntoResponseArray();
			putReleaseJSONArrayIntoResponseArray();
			putCompleteJSONArrayIntoResponseArray();
			putMonarchOrderStatusCodesIntoResponseArray();
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void putMonarchJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray monarchSQLData = converter.convert(SQLConnect.getMonarchDBResults());
		responseArray.put(0, monarchSQLData);
	}

	protected void putCreateJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray createSQLData = converter.convert(SQLConnect.getCreateDBResults());
		createSQLData.put(converter.convertEntityStatus(SQLConnect.getCreateDBResultsStatus(), SQLConnect.getCreateDBResultsStatusJobIDZero()));
		responseArray.put(1, createSQLData);
	}

	protected void putValidateJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray validateSQLData = converter.convert(SQLConnect.getValidateDBResults());
		validateSQLData.put(converter.convertEntityStatus(SQLConnect.getValidateDBResultsStatus(), SQLConnect.getValidateDBResultsStatusJobIDZero()));
		responseArray.put(2, validateSQLData);
	}

	protected void putVerifyJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray verifySQLData = converter.convert(SQLConnect.getVerifyDBResults());
		verifySQLData.put(converter.convertEntityStatus(SQLConnect.getVerifyDBResultsStatus(), SQLConnect.getVerifyDBResultsStatusJobIDZero()));
		responseArray.put(3, verifySQLData);
	}

	protected void putSourceJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray sourceSQLData = converter.convert(SQLConnect.getSourceDBResults());
		sourceSQLData.put(converter.convertEntityStatus(SQLConnect.getSourceDBResultsStatus(), SQLConnect.getSourceDBResultsStatusJobIDZero()));
		responseArray.put(4, sourceSQLData);
	}

	protected void putCancelJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray cancelSQLData = converter.convert(SQLConnect.getCancelDBResults());
		cancelSQLData.put(converter.convertEntityStatus(SQLConnect.getCancelDBResultsStatus(), SQLConnect.getCancelDBResultsStatusJobIDZero()));
		responseArray.put(5, cancelSQLData);
	}

	protected void putReleaseJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray releaseSQLData = converter.convert(SQLConnect.getReleaseDBResults());
		releaseSQLData.put(converter.convertEntityStatus(SQLConnect.getReleaseDBResultsStatus(), SQLConnect.getReleaseDBResultsStatusJobIDZero()));
		responseArray.put(6, releaseSQLData);
	}

	protected void putCompleteJSONArrayIntoResponseArray() throws JSONException, SQLException {
		JSONArray completeSQLData = converter.convert(SQLConnect.getCompleteDBResults());
		completeSQLData.put(converter.convertEntityStatus(SQLConnect.getCompleteDBResultsStatus(), SQLConnect.getCompleteDBResultsStatusJobIDZero()));
		responseArray.put(7, completeSQLData);
	}
	
	protected void putMonarchOrderStatusCodesIntoResponseArray() throws JSONException, SQLException {
		JSONArray monarchOrderStatusCodes = converter.convert(SQLConnect.getOMSCommonDBResults());
		responseArray.put(8, monarchOrderStatusCodes);
	}

	protected void sendResponseToServlet(PrintWriter responseOutput) {
		responseOutput.println(responseArray);
	}

}
