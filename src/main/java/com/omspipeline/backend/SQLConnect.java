package com.omspipeline.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class SQLConnect {

	// Private members
	private String connectionString;
	private String entityPipelineStatusQueryStringWhenJobIDIsNotZero;
	private String entityPipelineStatusQueryStringWhenJobIDIsZero;
	private String monarchOrderStatusQueryString;
	private String entityPipelineQueryString;
	private String omsCommonQueryString;
	private Connection cancelDBConnection;
	private Connection completeDBConnection;
	private Connection createDBConnection;
	private Connection monarchDBConnection;
	private Connection releaseDBConnection;
	private Connection sourceDBConnection;
	private Connection validateDBConnection;
	private Connection verifyDBConnection;
	private Connection omsCommonDBConnection;
	private static ResultSet cancelDBResults;
	private static ResultSet completeDBResults;
	private static ResultSet createDBResults;
	private static ResultSet monarchDBResults;
	private static ResultSet releaseDBResults;
	private static ResultSet sourceDBResults;
	private static ResultSet validateDBResults;
	private static ResultSet verifyDBResults;
	private static ResultSet cancelDBResultsStatus;
	private static ResultSet completeDBResultsStatus;
	private static ResultSet createDBResultsStatus;
	private static ResultSet releaseDBResultsStatus;
	private static ResultSet sourceDBResultsStatus;
	private static ResultSet validateDBResultsStatus;
	private static ResultSet verifyDBResultsStatus;
	private static ResultSet omsCommonDBResults;
	private static ResultSet cancelDBResultsStatusJobIDZero;
	private static ResultSet completeDBResultsStatusJobIDZero;
	private static ResultSet createDBResultsStatusJobIDZero;
	private static ResultSet releaseDBResultsStatusJobIDZero;
	private static ResultSet sourceDBResultsStatusJobIDZero;
	private static ResultSet validateDBResultsStatusJobIDZero;
	private static ResultSet verifyDBResultsStatusJobIDZero;
	private static ResultSet omsCommonDBResultsJobIDZero;

	private boolean emptyMonarchDB;
	private boolean isNotConfirmed;
	private final int EU = 1;
	private final int JP = 2;
	private final int CA = 3;
	private final int US = 4;
	private Properties prop = new Properties();;
	private InputStream input;

	public SQLConnect() {
		super();
	}

	public void setUpSQLConnections() {
		try {
			setUpPropertiesFile();
			selectCorrectServers();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	protected void setUpPropertiesFile() throws IOException {
		input = new FileInputStream("servers.properties");
		prop.load(input);
		input.close();
		connectionString = prop.getProperty("connectionString");
	}

	protected boolean getEmptyMonarchDB() {
		return emptyMonarchDB;
	}

	protected void setEmptyMonarchDB(boolean emptyMonarchDB) {
		this.emptyMonarchDB = emptyMonarchDB;
	}

	protected static ResultSet getCancelDBResults() {
		return cancelDBResults;
	}

	protected static ResultSet getCompleteDBResults() {
		return completeDBResults;
	}

	protected static ResultSet getCreateDBResults() {
		return createDBResults;
	}
	protected String getMonarchOrderStatusQueryString() {
		return monarchOrderStatusQueryString;
	}

	protected String getEntityPipelineStatusQueryStringWhenJobIDIsNotZero() {
		return entityPipelineStatusQueryStringWhenJobIDIsNotZero;
	}

	protected static ResultSet getMonarchDBResults() {
		return monarchDBResults;
	}

	protected static ResultSet getReleaseDBResults() {
		return releaseDBResults;
	}

	protected static ResultSet getSourceDBResults() {
		return sourceDBResults;
	}

	protected static ResultSet getValidateDBResults() {
		return validateDBResults;
	}

	protected static ResultSet getVerifyDBResults() {
		return verifyDBResults;
	}

	protected static ResultSet getCancelDBResultsStatus() {
		return cancelDBResultsStatus;
	}

	protected static ResultSet getCompleteDBResultsStatus() {
		return completeDBResultsStatus;
	}

	protected static ResultSet getCreateDBResultsStatus() {
		return createDBResultsStatus;
	}

	protected static ResultSet getReleaseDBResultsStatus() {
		return releaseDBResultsStatus;
	}

	protected static ResultSet getSourceDBResultsStatus() {
		return sourceDBResultsStatus;
	}

	protected static ResultSet getValidateDBResultsStatus() {
		return validateDBResultsStatus;
	}

	protected static ResultSet getVerifyDBResultsStatus() {
		return verifyDBResultsStatus;
	}

	protected static ResultSet getOMSCommonDBResults() {
		return omsCommonDBResults;
	}

	protected static ResultSet getCancelDBResultsStatusJobIDZero() {
		return cancelDBResultsStatusJobIDZero;
	}

	protected static ResultSet getCompleteDBResultsStatusJobIDZero() {
		return completeDBResultsStatusJobIDZero;
	}

	protected static ResultSet getCreateDBResultsStatusJobIDZero() {
		return createDBResultsStatusJobIDZero;
	}

	protected static ResultSet getReleaseDBResultsStatusJobIDZero() {
		return releaseDBResultsStatusJobIDZero;
	}

	protected static ResultSet getSourceDBResultsStatusJobIDZero() {
		return sourceDBResultsStatusJobIDZero;
	}

	protected static ResultSet getValidateDBResultsStatusJobIDZero() {
		return validateDBResultsStatusJobIDZero;
	}

	protected static ResultSet getVerifyDBResultsStatusJobIDZero() {
		return verifyDBResultsStatusJobIDZero;
	}

	protected static ResultSet getOmsCommonDBResultsJobIDZero() {
		return omsCommonDBResultsJobIDZero;
	}

	protected void selectCorrectServers() throws SQLException {
		assignQueryStrings();

		selectServerCaseByMarketCode(ObtainOrderNumberAndMarketCode.getMarketCode());
	}

	protected void assignQueryStrings() {
		assignEntityPipelineStatusQueryStrings();
		assignMonarchOrderStatusQueryString();
		assignEntityPipelineQueryString();
		assignOMSCommonQueryString();
	}

	protected void assignEntityPipelineStatusQueryStrings() {
		entityPipelineStatusQueryStringWhenJobIDIsNotZero = "select " + "enty_stat"
				+ " from ENTY_PRXY_T where ENTY_ID = '" + ObtainOrderNumberAndMarketCode.getOrderNumber()
				+ "' and Job_ID != 0";
		entityPipelineStatusQueryStringWhenJobIDIsZero = "select " + "enty_stat" + " from ENTY_PRXY_T where ENTY_ID = '"
				+ ObtainOrderNumberAndMarketCode.getOrderNumber() + "' and Job_ID = 0";
	}

	protected void assignMonarchOrderStatusQueryString() {
		monarchOrderStatusQueryString = "select"
				+ " marc_ord_sts_cd, sts_qty, lst_updt_dttm, crt_user_id, lst_updt_user_id, lst_updt_appl_srvr_nm "
				+ "from MARC_ORD_UN_STS_T " + "where MARC_ORD_LN_KEY " + "IN(select MARC_ORD_LN_KEY "
				+ "from MARC_ORD_LN_T " + "where MARC_ORD_KEY IN(select MARC_ORD_KEY from MARC_ORD_T where ORD_NO = '"
				+ ObtainOrderNumberAndMarketCode.getOrderNumber() + "')) order by lst_updt_dttm asc";
	}

	protected void assignEntityPipelineQueryString() {
		entityPipelineQueryString = "select srv_nm, lst_updt_dttm, enty_typ, srv_status, lst_updt_appl_srvr_nm "
				+ "from srv_state_dtl where enty_Key = '" + ObtainOrderNumberAndMarketCode.getOrderNumber()
				+ "' order by lst_updt_dttm";

	}

	protected void assignOMSCommonQueryString() {
		omsCommonQueryString = "select ORD_STS_CD, STS_NM from ORD_STS_T";
	}

	protected void selectServerCaseByMarketCode(int marketCode) throws SQLException {
		switch (marketCode) {

		case EU:
			checkEUMonarchDBAndAssignConnections();
			break;

		case JP:
			checkJPMonarchDBAndAssignConnections();
			break;

		case CA:
			checkCAMonarchDBAndAssignConnections();
			break;

		case US:
			checkUSMonarchDBAndAssignConnections();
			break;

		default:
			break;

		}
	}

	protected void checkEUMonarchDBAndAssignConnections() throws SQLException {
		checkMonarchDatabaseForOrderByMarketCode(EU);
		if (!emptyMonarchDB) {
			assignEUConnectionsAndObtainResultSets();
		}
	}

	protected void checkJPMonarchDBAndAssignConnections() throws SQLException {
		checkMonarchDatabaseForOrderByMarketCode(JP);
		if (!emptyMonarchDB) {
			assignJPConnectionsAndObtainResultSets();
		}
	}

	protected void checkCAMonarchDBAndAssignConnections() throws SQLException {
		checkMonarchDatabaseForOrderByMarketCode(CA);
		if (!emptyMonarchDB) {
			assignNAConnectionsAndObtainResultSets();
		}
	}

	protected void checkUSMonarchDBAndAssignConnections() throws SQLException {
		checkMonarchDatabaseForOrderByMarketCode(US);
		if (!emptyMonarchDB) {
			assignNAConnectionsAndObtainResultSets();
		}
	}

	protected void checkMonarchDatabaseForOrderByMarketCode(int marketCode) throws SQLException {
		createMonarchDBConnectionByMarketCode(marketCode);
		createMonarchDBResultSet();
		checkIfMonarchDBIsEmpty();
	}

	protected void createMonarchDBConnectionByMarketCode(int marketCode) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			switch (marketCode) {
			case 1:
				monarchDBConnection = DriverManager.getConnection(connectionString,
						prop.getProperty("monarchEUUsernamePassword"), prop.getProperty("monarchEUUsernamePassword"));
				break;
			case 2:
				monarchDBConnection = DriverManager.getConnection(connectionString,
						prop.getProperty("monarchJPUsernamePassword"), prop.getProperty("monarchJPUsernamePassword"));
				break;
			case 3:
				monarchDBConnection = DriverManager.getConnection(connectionString,
						prop.getProperty("monarchCAUsernamePassword"), prop.getProperty("monarchCAUsernamePassword"));
				break;
			case 4:
				monarchDBConnection = DriverManager.getConnection(connectionString,
						prop.getProperty("monarchUSUsernamePassword"), prop.getProperty("monarchUSUsernamePassword"));
				break;
			default:
				break;
			}
		} catch (NullPointerException e) {
			System.out.println("Monarch connection details are not found in the properties file.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("Please make sure you are connected to the VPN before establishing connections.");
			System.out.println("######################################");
			e.printStackTrace();
		}
	}

	protected void createMonarchDBResultSet() throws SQLException {
		monarchDBResults = monarchDBConnection.createStatement().executeQuery(monarchOrderStatusQueryString);
	}

	protected void checkIfMonarchDBIsEmpty() throws SQLException {
		if (!monarchDBResults.next()) {
			sendEmptyJSONresponseIfMonarchDBIsEmpty();
		} else {
			emptyMonarchDB = false;
		}
	}

	protected void sendEmptyJSONresponseIfMonarchDBIsEmpty() throws SQLException {
		JSONResponse jsonR = new JSONResponse();
		emptyMonarchDB = true;
		jsonR.obtainJSONResponse();
	}

	protected void assignNAConnectionsAndObtainResultSets() {
		try {
			assignNAConnections();
			makeOMSCommonDBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		obtainResultSetsFromQueries();
	}

	protected void assignJPConnectionsAndObtainResultSets() {
		assignJPConnections();
		makeOMSCommonDBConnection();
		obtainResultSetsFromQueries();
	}

	protected void assignEUConnectionsAndObtainResultSets() {
		assignEUConnections();
		makeOMSCommonDBConnection();
		obtainResultSetsFromQueries();
	}

	private void assignEUConnections() {
		try {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			cancelDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("cancelEUUsernamePassword"), prop.getProperty("cancelEUUsernamePassword"));
			completeDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("completeEUUsernamePassword"), prop.getProperty("completeEUUsernamePassword"));
			createDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("createEUUsernamePassword"), prop.getProperty("createEUUsernamePassword"));
			releaseDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("releaseEUUsernamePassword"), prop.getProperty("releaseEUUsernamePassword"));
			sourceDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("sourceEUUsernamePassword"), prop.getProperty("sourceEUUsernamePassword"));
			validateDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("validateEUUsernamePassword"), prop.getProperty("validateEUUsernamePassword"));
			verifyDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("verifyEUUsernamePassword"), prop.getProperty("verifyEUUsernamePassword"));

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void assignJPConnections() {
		try {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			cancelDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("cancelJPUsernamePassword"), prop.getProperty("cancelJPUsernamePassword"));
			completeDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("completeJPUsernamePassword"), prop.getProperty("completeJPUsernamePassword"));
			createDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("createJPUsernamePassword"), prop.getProperty("createJPUsernamePassword"));
			releaseDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("releaseJPUsernamePassword"), prop.getProperty("releaseJPUsernamePassword"));
			sourceDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("sourceJPUsernamePassword"), prop.getProperty("sourceJPUsernamePassword"));
			validateDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("validateJPUsernamePassword"), prop.getProperty("validateJPUsernamePassword"));
			verifyDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("verifyJPUsernamePassword"), prop.getProperty("verifyJPUsernamePassword"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private void assignNAConnections() throws SQLException {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		cancelDBConnection = DriverManager.getConnection(connectionString, prop.getProperty("cancelNAUsernamePassword"),
				prop.getProperty("cancelNAUsernamePassword"));
		completeDBConnection = DriverManager.getConnection(connectionString,
				prop.getProperty("completeNAUsernamePassword"), prop.getProperty("completeNAUsernamePassword"));
		createDBConnection = DriverManager.getConnection(connectionString, prop.getProperty("createNAUsernamePassword"),
				prop.getProperty("createNAUsernamePassword"));
		releaseDBConnection = DriverManager.getConnection(connectionString,
				prop.getProperty("releaseNAUsernamePassword"), prop.getProperty("releaseNAUsernamePassword"));
		sourceDBConnection = DriverManager.getConnection(connectionString, prop.getProperty("sourceNAUsernamePassword"),
				prop.getProperty("sourceNAUsernamePassword"));
		validateDBConnection = DriverManager.getConnection(connectionString,
				prop.getProperty("validateNAUsernamePassword"), prop.getProperty("validateNAUsernamePassword"));
		verifyDBConnection = DriverManager.getConnection(connectionString, prop.getProperty("verifyNAUsernamePassword"),
				prop.getProperty("verifyNAUsernamePassword"));
	}

	protected void makeOMSCommonDBConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			omsCommonDBConnection = DriverManager.getConnection(connectionString,
					prop.getProperty("omsCommUsernamePassword"), prop.getProperty("omsCommUsernamePassword"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void obtainResultSetsFromQueries() {
		try {
			obtainCreateResultSets();
			obtainValidateResultSets();
			obtainVerifyResultSets();
			obtainSourceResultSets();
			obtainCancelResultSets();
			obtainReleaseResultSets();
			obtainCompleteResultSets();
			obtainOMSCommonResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void obtainOMSCommonResultSet() throws SQLException {
		omsCommonDBResults = omsCommonDBConnection.createStatement().executeQuery(omsCommonQueryString);
	}

	private void obtainVerifyResultSets() throws SQLException {
		verifyDBResults = verifyDBConnection.createStatement().executeQuery(entityPipelineQueryString);

		verifyDBResultsStatusJobIDZero = verifyDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsZero);
		verifyDBResultsStatus = verifyDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsNotZero);
	}

	private void obtainValidateResultSets() throws SQLException {
		validateDBResults = validateDBConnection.createStatement().executeQuery(entityPipelineQueryString);
		validateDBResultsStatusJobIDZero = validateDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsZero);
		validateDBResultsStatus = validateDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsNotZero);
	}

	private void obtainSourceResultSets() throws SQLException {
		sourceDBResults = sourceDBConnection.createStatement().executeQuery(entityPipelineQueryString);
		sourceDBResultsStatusJobIDZero = sourceDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsZero);
		sourceDBResultsStatus = sourceDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsNotZero);
	}

	private void obtainReleaseResultSets() throws SQLException {
		releaseDBResults = releaseDBConnection.createStatement().executeQuery(entityPipelineQueryString);
		releaseDBResultsStatusJobIDZero = releaseDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsZero);
		releaseDBResultsStatus = releaseDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsNotZero);
	}

	private void obtainCreateResultSets() throws SQLException {
		createDBResults = createDBConnection.createStatement().executeQuery(entityPipelineQueryString);
		createDBResultsStatusJobIDZero = createDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsZero);
		createDBResultsStatus = createDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsNotZero);
	}

	private void obtainCompleteResultSets() throws SQLException {
		completeDBResults = completeDBConnection.createStatement().executeQuery(entityPipelineQueryString);
		completeDBResultsStatusJobIDZero = completeDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsZero);
		completeDBResultsStatus = completeDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsNotZero);
	}

	private void obtainCancelResultSets() throws SQLException {
		cancelDBResults = cancelDBConnection.createStatement().executeQuery(entityPipelineQueryString);
		cancelDBResultsStatusJobIDZero = cancelDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsZero);
		cancelDBResultsStatus = cancelDBConnection.createStatement()
				.executeQuery(entityPipelineStatusQueryStringWhenJobIDIsNotZero);
	}

}
