package com.boco.msgl.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.boco.msgl.modules.system.dto.User;
import com.boco.msgl.util.ConstantUtil;
import com.boco.msgl.util.JsonUtil;
import com.boco.msgl.util.ObjectUtil;

import net.sf.json.JSONObject;

public class BaseController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	Logger logger = Logger.getLogger(BaseController.class);

	/*定义了datatables插件约定的参数名称*/
	private final String PAGE_SIZE_PARAM = "length";
	private final String PAGE_INDEX_PARAM = "start";
	private final String ORDER_BY_NAME_PARAM = "order[0][column]";
	private final String ORDER_NAME_PARAM = "order[0][dir]";
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	protected void printMessage(HttpServletResponse response, Object message){
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(message);
			out.close();
			logger.debug("BaseController::printMessage -- " + message);
		} catch (IOException e) {
			logger.error(e);
		}
		
	}
	
	protected User getCurrentUser(){
		return (User)session.getAttribute(ConstantUtil.USER_IN_SESSION_ALIAS);
	}
	
	protected void printMessage(Object message) {
		printMessage(response, message);
	}

	protected void setContentTypeToImage(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
	}
	
	protected void setContentTypeToDownload(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.setContentType("application/octet-stream");  
		response.setHeader("Content-Disposition", "attachment;fileName=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) );   
	}
	
	/*以下是得到从前端post请求常用的param*/
	protected Integer getPagesize(HttpServletRequest request){
		String result = request.getParameter(PAGE_SIZE_PARAM);
		return StringUtils.isEmpty(result) ? 20 : Integer.valueOf(result);
	}
	
	protected Integer getPage(HttpServletRequest request){
		String result = request.getParameter(PAGE_INDEX_PARAM);
		Integer startIndex = StringUtils.isEmpty(result) ? 0 : Integer.valueOf(result);
		Integer pageSize = getPagesize(request);
		return startIndex / pageSize + 1;
	}
	
	/**
	 * 得到排序列名，已经转成数据库的格式了，可以直接使用
	 * @param request
	 * @return
	 */
	protected String getOrderBy(HttpServletRequest request){
		String colIndex = request.getParameter(ORDER_BY_NAME_PARAM);
		String orderBy = request.getParameter("columns[" + colIndex + "][data]");
		return ObjectUtil.CamelCaseToUnderscore(orderBy);
	}
	
	/**
	 * asc || desc
	 * @param request
	 * @return
	 */
	protected String getOrder(HttpServletRequest request){
		String result = request.getParameter(ORDER_NAME_PARAM);
		return StringUtils.isEmpty(result) ? null : result;
	}
	
	/**
	 * 将最后传递给前端的数据封装成datatables格式的形式
	 * http://blog.csdn.net/u010403387/article/details/46355533
	 * @return
	 */
	protected String dataTablesPattern(HttpServletRequest request, List<BaseObject> resultList, Integer totalCount){
		String draw = request.getParameter("draw");
		JSONObject obj = JsonUtil.formatResultToJSON(resultList);
		obj.element("draw", StringUtils.isEmpty(draw)?0:Integer.valueOf(draw));
		obj.element("recordsTotal", totalCount);
		obj.element("recordsFiltered", totalCount);
		return obj.toString();
	}
	/**
	 * 向前端输出数据
	 * @param request
	 * @param response
	 * @param bean 封装好条件的bean
	 * @param service 由子类传递进来
	 */
	protected void printMessage(HttpServletRequest request, HttpServletResponse response, BaseObject bean, BaseService service){
		Integer page = getPage(request);
		Integer pagesize = getPagesize(request);
		String order = getOrder(request);
		String orderBy = getOrderBy(request);
		
		List<BaseObject> list = service.getBeans(bean, page, pagesize, orderBy, order);
		Integer count = service.getCountByParam(bean);
		
		printMessage(response, dataTablesPattern(request, list, count));
	}

}
