package com.omspipeline.backend;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import static org.mockito.Mockito.*;

import com.omspipeline.backend.ObtainOrderNumberAndMarketCode;
import com.omspipeline.backend.SQLConnect;

public class SQLConnectTest extends SQLConnect {

	private final int EU = 1;
	private final int JP = 2;
	private final int CA = 3;
	private final int US = 4;
	private String orderNumber = "TTYWLPY";
	private String expectedEntityPipelineStatusQueryString = "select enty_stat"
			+ " from ENTY_PRXY_T where ENTY_ID = '" + orderNumber + "' and Job_ID != 0";
	private String expectedMonarchQueryString = "select"
			+ " marc_ord_sts_cd, sts_qty, lst_updt_dttm, crt_user_id, lst_updt_user_id, lst_updt_appl_srvr_nm "
			+ "from MARC_ORD_UN_STS_T " + "where MARC_ORD_LN_KEY " + "IN(select MARC_ORD_LN_KEY "
			+ "from MARC_ORD_LN_T " + "where MARC_ORD_KEY IN(select MARC_ORD_KEY from MARC_ORD_T where ORD_NO = '"
			+ orderNumber + "')) order by lst_updt_dttm asc";
	private SQLConnect sc;

	/**
	 * @Before
	 */
	public SQLConnectTest() {
		sc = new SQLConnect();
	}

	@Test
	public void testAssignQueryStrings() {
		ObtainOrderNumberAndMarketCode.setOrderNumber(orderNumber);
		sc.assignQueryStrings();

		assertEquals(expectedEntityPipelineStatusQueryString, sc.getEntityPipelineStatusQueryStringWhenJobIDIsNotZero());
		assertEquals(expectedMonarchQueryString, sc.getMonarchOrderStatusQueryString());
	}

	@Test
	public void selectServerCaseByMarketCodeTestForEU() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkEUMonarchDBAndAssignConnections();

		spySQLConnect.selectServerCaseByMarketCode(1);

		verify(spySQLConnect, atLeastOnce()).checkEUMonarchDBAndAssignConnections();
	}

	@Test
	public void selectServerCaseByMarketCodeTestForJP() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkJPMonarchDBAndAssignConnections();

		spySQLConnect.selectServerCaseByMarketCode(2);

		verify(spySQLConnect, atLeastOnce()).checkJPMonarchDBAndAssignConnections();
	}

	@Test
	public void selectServerCaseByMarketCodeTestForCA() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkCAMonarchDBAndAssignConnections();

		spySQLConnect.selectServerCaseByMarketCode(3);

		verify(spySQLConnect, atLeastOnce()).checkCAMonarchDBAndAssignConnections();
	}

	@Test
	public void selectServerCaseByMarketCodeTestForUS() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkUSMonarchDBAndAssignConnections();

		spySQLConnect.selectServerCaseByMarketCode(4);

		verify(spySQLConnect, atLeastOnce()).checkUSMonarchDBAndAssignConnections();
	}

	@Test
	public void checkUSMonarchDBAndAssignConnectionsNonEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignNAConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(false);
		spySQLConnect.checkUSMonarchDBAndAssignConnections();

		verify(spySQLConnect, atLeastOnce()).assignNAConnectionsAndObtainResultSets();
	}

	@Test
	public void checkUSMonarchDBAndAssignConnectionsEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignNAConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(true);
		spySQLConnect.checkUSMonarchDBAndAssignConnections();

		verify(spySQLConnect, times(0)).assignNAConnectionsAndObtainResultSets();
	}

	@Test
	public void checkCAMonarchDBAndAssignConnectionsNonEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignNAConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(false);
		spySQLConnect.checkCAMonarchDBAndAssignConnections();

		verify(spySQLConnect, atLeastOnce()).assignNAConnectionsAndObtainResultSets();
	}

	@Test
	public void checkCAMonarchDBAndAssignConnectionsEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignNAConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(true);
		spySQLConnect.checkCAMonarchDBAndAssignConnections();

		verify(spySQLConnect, times(0)).assignNAConnectionsAndObtainResultSets();
	}

	@Test
	public void checkJPMonarchDBAndAssignConnectionsNonEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignJPConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(false);
		spySQLConnect.checkJPMonarchDBAndAssignConnections();

		verify(spySQLConnect, atLeastOnce()).assignJPConnectionsAndObtainResultSets();
	}

	@Test
	public void checkJPMonarchDBAndAssignConnectionsEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignJPConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(true);
		spySQLConnect.checkJPMonarchDBAndAssignConnections();

		verify(spySQLConnect, times(0)).assignJPConnectionsAndObtainResultSets();
	}

	@Test
	public void checkEUMonarchDBAndAssignConnectionsNonEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignEUConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(false);
		spySQLConnect.checkEUMonarchDBAndAssignConnections();

		verify(spySQLConnect, atLeastOnce()).assignEUConnectionsAndObtainResultSets();
	}

	@Test
	public void checkEUMonarchDBAndAssignConnectionsEmptyDBTest() throws SQLException {
		SQLConnect spySQLConnect = spy(new SQLConnect());
		doNothing().when(spySQLConnect).checkMonarchDatabaseForOrderByMarketCode(anyInt());
		doNothing().when(spySQLConnect).assignEUConnectionsAndObtainResultSets();

		spySQLConnect.setEmptyMonarchDB(true);
		spySQLConnect.checkEUMonarchDBAndAssignConnections();

		verify(spySQLConnect, times(0)).assignEUConnectionsAndObtainResultSets();
	}

}
