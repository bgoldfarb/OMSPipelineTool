package com.omspipeline.backend;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import com.omspipeline.backend.ResultSetToJSONConverter;

public class ResultSetToJSONConverterTest extends ResultSetToJSONConverter {

	@Test
	public void placeJSONObjectInArrayTest() throws JSONException{
		JSONObject obj = new JSONObject();
		JSONArray expectedArr = new JSONArray();
		JSONArray arr = new JSONArray();
		obj.put("one", true);
		expectedArr.put(obj);
		
		placeJSONObjectInArray(arr, obj);
		
		assertEquals(expectedArr.get(0), arr.get(0));
		assertEquals(expectedArr.length(), arr.length());
	}
	
	@Test
	public void createJSONObjectForEachRowBooleanTest() throws SQLException, JSONException{
		ResultSet rs = mock(ResultSet.class);
		ResultSetMetaData rsmd = mock(ResultSetMetaData.class);
		JSONObject obj = new JSONObject();
		
		when(rsmd.getColumnName(anyInt())).thenReturn("expected");
		when(rsmd.getColumnType(anyInt())).thenReturn(java.sql.Types.BOOLEAN);
		
		createJSONObjectForEachRow(rs, rsmd, 1, obj);
		
		verify(rs, atLeastOnce()).getBoolean(anyString());
	}
	
	@Test
	public void createJSONObjectForEachRowStringTest() throws SQLException, JSONException{
		ResultSet rs = mock(ResultSet.class);
		ResultSetMetaData rsmd = mock(ResultSetMetaData.class);
		JSONObject obj = new JSONObject();
		
		when(rsmd.getColumnName(anyInt())).thenReturn("expected");
		when(rsmd.getColumnType(anyInt())).thenReturn(java.sql.Types.VARCHAR);
		
		createJSONObjectForEachRow(rs, rsmd, 1, obj);
		
		verify(rs, atLeastOnce()).getString(anyString());
	}
	
	@Test
	public void createJSONObjectForEachRowIntegerTest() throws SQLException, JSONException{
		ResultSet rs = mock(ResultSet.class);
		ResultSetMetaData rsmd = mock(ResultSetMetaData.class);
		JSONObject obj = new JSONObject();
		
		when(rsmd.getColumnName(anyInt())).thenReturn("expected");
		when(rsmd.getColumnType(anyInt())).thenReturn(java.sql.Types.INTEGER);
		
		createJSONObjectForEachRow(rs, rsmd, 1, obj);
		
		verify(rs, atLeastOnce()).getInt(anyString());
	}
	
	@Test
	public void createJSONObjectForEachRowObjectTest() throws SQLException, JSONException{
		ResultSet rs = mock(ResultSet.class);
		ResultSetMetaData rsmd = mock(ResultSetMetaData.class);
		JSONObject obj = new JSONObject();
		
		when(rsmd.getColumnName(anyInt())).thenReturn("expected");
		
		createJSONObjectForEachRow(rs, rsmd, 1, obj);
		
		verify(rs, atLeastOnce()).getObject(anyString());
	}
}
