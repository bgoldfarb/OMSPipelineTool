package com.omspipeline.backend;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.PrintWriter;

import org.junit.Test;

import com.omspipeline.backend.JSONResponse;
import com.omspipeline.backend.ResultSetToJSONConverter;
import com.omspipeline.backend.SQLConnect;

public class JSONResponseTest extends JSONResponse {
	@Test
	public void privateFieldsSetUpTest() {

		setUpJSONResponseFields();

		assertNotNull(getResponseArray());
		assertNotNull(getConverter());
		assertNotNull(getSc());
	}

	@Test
	public void putMonarchJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getMonarchDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putMonarchJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(0));
	}

	@Test
	public void putCreateJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getCreateDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putCreateJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(1));
	}

	@Test
	public void putValidateJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getValidateDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putValidateJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(2));
	}

	@Test
	public void putVerifyJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getVerifyDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putVerifyJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(3));
	}

	@Test
	public void putSourceJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getSourceDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putSourceJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(4));
	}

	@Test
	public void putCancelJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getCancelDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putCancelJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(5));
	}

	@Test
	public void putReleaseJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getReleaseDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putReleaseJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(6));
	}

	@Test
	public void putCompleteJSONArrayIntoResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getCompleteDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);
		putCompleteJSONArrayIntoResponseArray();

		assertEquals(mockedJSONArray, getResponseArray().get(7));
	}

	@Test
	public void createJSONResponseArrayTest() throws JSONException, SQLException {
		JSONArray responseArray = new JSONArray();
		ResultSetToJSONConverter converter = mock(ResultSetToJSONConverter.class);
		JSONArray mockedJSONArray = mock(JSONArray.class);
		SQLConnect sc = mock(SQLConnect.class);

		when(converter.convert(sc.getMonarchDBResults())).thenReturn(mockedJSONArray);
		when(converter.convert(sc.getCreateDBResults())).thenReturn(mockedJSONArray);
		when(converter.convert(sc.getValidateDBResults())).thenReturn(mockedJSONArray);
		when(converter.convert(sc.getVerifyDBResults())).thenReturn(mockedJSONArray);
		when(converter.convert(sc.getSourceDBResults())).thenReturn(mockedJSONArray);
		when(converter.convert(sc.getCancelDBResults())).thenReturn(mockedJSONArray);
		when(converter.convert(sc.getReleaseDBResults())).thenReturn(mockedJSONArray);
		when(converter.convert(sc.getCompleteDBResults())).thenReturn(mockedJSONArray);

		setConverter(converter);
		setResponseArray(responseArray);
		setSc(sc);

		createJSONResponseArray();

		assertTrue(getResponseArray().length() == 9);
	}

	@Test
	public void sendResponseToServletTest() {

		JSONArray mockedResponseArray = mock(JSONArray.class);
		PrintWriter mockedOut = mock(PrintWriter.class);
		setResponseArray(mockedResponseArray);

		sendResponseToServlet(mockedOut);

		verify(mockedOut, times(1)).println(mockedResponseArray);

	}

}
