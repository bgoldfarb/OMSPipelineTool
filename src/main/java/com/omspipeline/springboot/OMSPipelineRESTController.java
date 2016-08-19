package com.omspipeline.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import com.omspipeline.backend.JSONResponse;
import com.omspipeline.backend.ObtainOrderNumberAndMarketCode;
import com.omspipeline.backend.SQLConnect;

import java.io.PrintWriter;

import java.sql.SQLException;
import java.io.IOException;
import org.json.JSONException;

@RestController
public class OMSPipelineRESTController {
	public static PrintWriter out;
	private SQLConnect sc;
	private JSONResponse jsonResponse;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping(value = "/MainServ", method = RequestMethod.GET)
	public void mainServ(@RequestParam(value = "orderNumber", defaultValue = "") String orderNum,
			@RequestParam(value = "marketCode", defaultValue = "0") String marCode, HttpServletResponse response)
			throws SQLException, IOException {

		response.setContentType("application/json; charset=UTF-8");
		out = response.getWriter();

		try {
			ObtainOrderNumberAndMarketCode.setOrderNumber(orderNum);
			ObtainOrderNumberAndMarketCode.setMarketCode(Integer.parseInt(marCode));
			sc = new SQLConnect();
			sc.setUpSQLConnections();
			jsonResponse = new JSONResponse();
			jsonResponse.obtainJSONResponse();
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
