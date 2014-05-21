package com.lj.common.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageList<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4392871752483853270L;

	// 集合数据
	private List<E> all = new ArrayList<E>();

	// 页码
	private int pageNo;

	// 页大小
	private int pageSize;

	// 总条数
	private long totalCount;

	// 总页数
	private int totalPages;

	// 是否是首页
	private boolean isFirstPage;

	// 是否是最后一页
	private boolean isLastPage;

	// 是否有上一页
	private boolean hasPrevPage;

	// 是否有下一页
	private boolean hasNextPage;

	public List<E> getAll() {
		return all;
	}

	public void addAll(Collection<? extends E> c) {
		all.addAll(c);
	}

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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public boolean isHasPrevPage() {
		return hasPrevPage;
	}

	public void setHasPrevPage(boolean hasPrevPage) {
		this.hasPrevPage = hasPrevPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

}
