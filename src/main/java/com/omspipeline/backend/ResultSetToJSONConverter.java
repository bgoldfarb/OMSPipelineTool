package com.omspipeline.backend;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ResultSetToJSONConverter {
	public JSONArray convert(ResultSet rs) throws SQLException, JSONException {
		return convertJSONArrayFromResultSet(rs);
	}

	public static JSONArray convertJSONArrayFromResultSet(ResultSet rs) throws SQLException, JSONException {
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		createJSONArray(rs, json, rsmd);
		return json;
	}

	protected static void createJSONArray(ResultSet rs, JSONArray json, ResultSetMetaData rsmd)
			throws SQLException, JSONException {
		while (rs.next()) {
			int numColumns = rsmd.getColumnCount();
			JSONObject obj = new JSONObject();
			createJSONObjectForEachRow(rs, rsmd, numColumns, obj);
			placeJSONObjectInArray(json, obj);
		}
	}

	protected static void createJSONObjectForEachRow(ResultSet rs, ResultSetMetaData rsmd, int numColumns,
			JSONObject obj) throws SQLException, JSONException {
		for (int i = 1; i < numColumns + 1; i++) {
			String column_name = rsmd.getColumnName(i);

			if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
				obj.put(column_name, rs.getArray(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
				obj.put(column_name, rs.getLong(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
				obj.put(column_name, rs.getBoolean(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
				obj.put(column_name, rs.getBlob(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
				obj.put(column_name, rs.getDouble(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
				obj.put(column_name, rs.getFloat(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
				obj.put(column_name, rs.getInt(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
				obj.put(column_name, rs.getNString(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
				obj.put(column_name, rs.getString(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
				obj.put(column_name, rs.getInt(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
				obj.put(column_name, rs.getInt(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
				obj.put(column_name, rs.getDate(column_name));
			} else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
				obj.put(column_name, rs.getTimestamp(column_name));
			} else {
				obj.put(column_name, rs.getObject(column_name));
			}
		}
	}

	protected static void placeJSONObjectInArray(JSONArray json, JSONObject obj) {
		json.put(obj);
	}

	/**
	 * 
	 * new code
	 */
	
	
	protected JSONObject convertEntityStatus(ResultSet entityStatus, ResultSet entityStatusJobIDIsZero)
			throws SQLException, JSONException {
		JSONObject obj = new JSONObject();
		boolean entityStatusIsEmptyWhenJobIDIsZero = !entityStatusJobIDIsZero.next();
		boolean entityStatusIsEmptyWhenJobIDIsNotZero = !entityStatus.next();
		placeEntityStatusObjectInArray(entityStatus, entityStatusJobIDIsZero, obj, entityStatusIsEmptyWhenJobIDIsZero,
				entityStatusIsEmptyWhenJobIDIsNotZero);
		return obj;
	}

	protected void placeEntityStatusObjectInArray(ResultSet entityStatus, ResultSet entityStatusJobIDIsZero,
			JSONObject obj, boolean entityStatusIsEmptyWhenJobIDIsZero, boolean entityStatusIsEmptyWhenJobIDIsNotZero) {
		try {
			if (entityStatusIsEmptyWhenJobIDIsNotZero && entityStatusIsEmptyWhenJobIDIsZero) {
				placeNoneEntityStatusForEmptyResults(obj);
			} else if (!entityStatusIsEmptyWhenJobIDIsZero) {
				checkEntityStatusAndPlaceWhenJobIDZeroForConfirmed(entityStatus, entityStatusJobIDIsZero, obj,
						entityStatusIsEmptyWhenJobIDIsNotZero);
			} else {
				placeEntityStatusIntoJSONObj(entityStatus, obj);
			}
		} catch (SQLException e) {
			placeNoneEntityStatusForEmptyResults(obj);
			e.printStackTrace();
		}
	}

	protected void placeEntityStatusIntoJSONObj(ResultSet rs, JSONObject obj) throws SQLException {
		obj.put("pipelinestatus", rs.getString("ENTY_STAT"));
	}

	protected void placeNoneEntityStatusForEmptyResults(JSONObject obj) {
		obj.put("pipelinestatus", "NONE");
	}

	protected void checkEntityStatusAndPlaceWhenJobIDZeroForConfirmed(ResultSet entityStatus, ResultSet entityStatusJobIDIsZero,
			JSONObject obj, boolean entityStatusIsEmptyWhenJobIDIsNotZero) throws SQLException {
		if (entityStatusJobIDIsZero.getString(1).toLowerCase().contains("confirmed")) {
			placeEntityStatusIntoJSONObj(entityStatusJobIDIsZero, obj);
		} else {
			if (entityStatusIsEmptyWhenJobIDIsNotZero) {
				placeNoneEntityStatusForEmptyResults(obj);
			} else {
				placeEntityStatusIntoJSONObj(entityStatus, obj);
			}
		}
	}

}
