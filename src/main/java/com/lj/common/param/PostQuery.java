package com.lj.common.param;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PostQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5958159730925982514L;

	// 页码
	private int pageNo;

	// 页大小
	private int pageSize;

	// 排序字符串
	private String orderBy;

	// 分组字符串
	private String groupBy;

	// 自字义查询条件字符串
	private String hqlWhere;

	// where条件参数
	private List<Condition> param = new LinkedList<Condition>();

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getHqlWhere() {
		return hqlWhere;
	}

	public void setHqlWhere(String hqlWhere) {
		this.hqlWhere = hqlWhere;
	}

	public void addParam(Condition param) {
		this.param.add(param);
	}

	public List<Condition> getParam() {
		return param;
	}

}
