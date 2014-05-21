package com.lj.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lj.common.base.AbstractBaseModel;
import com.lj.common.param.Condition;
import com.lj.common.param.HandlerDAO;
import com.lj.common.param.PageList;
import com.lj.common.param.PostQuery;
import com.lj.common.utils.object.ObjUtils;
import com.lj.dao.IHibernateDAO;
import com.lj.log.Loggers;

@SuppressWarnings("unchecked")
public abstract class HibernateDAOImpl<T extends AbstractBaseModel> extends HibernateDaoSupport implements IHibernateDAO<T> {

	protected Loggers logger = new Loggers(HibernateDAOImpl.class.getName());

	public HibernateDAOImpl() {
		super();
		logger = new Loggers(this.getClass().getName());
	}

	@Override
	public T getById(String id) {
		Object obj = this.getHibernateTemplate().get(ObjUtils.getEntityClass(this.getClass()), id);
		return (obj != null) ? (T) obj : null;
	}

	@Override
	public T getById(Integer id) {
		Object obj = this.getHibernateTemplate().get(ObjUtils.getEntityClass(this.getClass()), id);
		return (obj != null) ? (T) obj : null;
	}

	@Override
	public int save(Object obj) {
		if (obj instanceof Collection) {// 如果是批量数据保存
			Collection<?> cols = (Collection<?>) obj;
			for (Object o : cols)
				this.save(o);
			return cols.size();
		}
		// 单一对象保存
		if (obj == null || !(obj instanceof AbstractBaseModel))
			return 0;
		this.getHibernateTemplate().save(obj);
		return 1;
	}

	@Override
	public int update(T obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

	@Override
	public int remove(T obj) {
		if (obj == null)
			return 0;
		this.getHibernateTemplate().delete(obj);
		return 1;
	}

	@Override
	public long getCounts() {
		return this.getCounts("select count(*) as nums from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName());
	}

	@Override
	public long getCounts(String hql) {
		return this.getCounts(hql, null);
	}

	@Override
	public long getCounts(String hql, PostQuery dto) {
		Session session = this.getSession();
		if (!hql.startsWith("select")) {
			hql = "select count(*) as nums " + hql;
		}
		try {
			Query query = session.createQuery(hql);
			this.setParamters(query, dto);
			long intRtn = (Long) query.uniqueResult();
			return intRtn;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	@Override
	public T getByWhere(String hqlWhere) {
		if (StringUtils.isBlank(hqlWhere))
			return null;
		List<T> lstTemp = this.getListByWhere(hqlWhere);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	@Override
	public <O> O getByWhere(String hqlWhere, Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		List<O> lstTemp = this.getListByWhere(hqlWhere, clazz);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	@Override
	public <O> O getByWhere(String hqlWhere, Object[] whereArgs, Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		if (whereArgs == null)
			return this.getByWhere(hqlWhere, clazz);
		List<O> lstTemp = this.getListByWhere(hqlWhere, whereArgs, clazz);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	public T getByWhere(String hqlWhere, Object... whereArgs) {
		if (StringUtils.isBlank(hqlWhere))
			return null;
		if (whereArgs == null)
			return this.getByWhere(hqlWhere);
		List<T> lstTemp = this.getListByWhere(hqlWhere, whereArgs);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	public List<T> getListByWhere(String hqlWhere) {
		if (StringUtils.isBlank(hqlWhere))
			return this.getList();
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " " : " where ") + hqlWhere;
		String hql = "from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName() + " b5m " + hqlWhere;
		return this.getHibernateTemplate().find(hql);
	}

	public List<T> getListByWhere(String hqlWhere, Object[] whereArgs) {
		if (StringUtils.isBlank(hqlWhere))
			return this.getList();
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " " : " where ") + hqlWhere;
		String hql = "from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName() + " b5m " + hqlWhere;
		return this.getHibernateTemplate().find(hql, whereArgs);
	}

	@Override
	public <O> List<O> getListByWhere(String hqlWhere, Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " " : " where ") + hqlWhere;
		String hql = "from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName() + " b5m " + hqlWhere;
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public <O> List<O> getListByWhere(String hqlWhere, Object[] whereArgs, Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " " : " where ") + hqlWhere;
		String hql = "from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName() + " b5m " + hqlWhere;
		return this.getHibernateTemplate().find(hql, whereArgs);
	}

	@Override
	public List<T> getList() {
		List<T> lst = this.getHibernateTemplate().find("from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName());
		return (lst != null) ? lst : null;
	}

	@Override
	public PageList<T> getPageList(PostQuery dto) {
		PageList<T> pageList = new PageList<T>();
		Session session = null;
		Query query = null;
		try {
			session = this.getSession();
			String hql = "from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName() + " b5m ";
			// 计算总记录数
			String hqlString = buildHql(hql, dto);
			int pageNo = dto.getPageNo();
			int pageSize = dto.getPageSize();
			long totalCount = this.getCounts(hqlString, dto);

			int mod = (int) totalCount % pageSize;
			long totalPages = totalCount / pageSize + (mod > 0 ? 1 : 0);

			if (pageNo < 1)
				pageNo = 1;
			if (pageNo > totalPages)
				pageNo = (int) totalPages;
			dto.setPageNo(pageNo);

			boolean isFirstPage = pageNo == 1;
			boolean isLastPage = pageNo == totalPages;
			boolean hasNextPage = pageNo < totalPages;
			boolean hasPrevPage = pageNo > 1;

			pageList.setFirstPage(isFirstPage);
			pageList.setLastPage(isLastPage);
			pageList.setHasNextPage(hasNextPage);
			pageList.setHasPrevPage(hasPrevPage);

			pageList.setPageNo(pageNo);
			pageList.setPageSize(pageSize);
			pageList.setTotalCount(totalCount);
			pageList.setTotalPages((int) totalPages);

			// 查询分页数据
			query = this.createQuery(session, hql, dto);
			List<T> lst = query.list();
			pageList.addAll(lst);
			return pageList;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	@Override
	public List<T> getListByQuery(PostQuery dto) {
		Session session = null;
		Query query = null;
		try {
			session = this.getSession();
			String hql = "from " + ObjUtils.getEntityClass(this.getClass()).getSimpleName() + " b5m ";
			query = this.createQuery(session, hql, dto);
			List<T> lst = query.list();
			return lst;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListByQuery(String hql, PostQuery dto) {
		Session session = null;
		try {
			session = this.getSession();
			Query query = this.createQuery(session, hql, dto);
			List<?> list = query.list();
			return list;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	private String buildHql(String hql, PostQuery dto) {
		if (hql.toLowerCase().lastIndexOf("where") <= 0)
			hql += " where 1=1 ";
		// 拼装自定义查询条件字符串
		if (StringUtils.isNotBlank(dto.getHqlWhere()))
			hql += (dto.getHqlWhere().trim().toLowerCase().startsWith("and") ? " " : " and ") + dto.getHqlWhere();
		if (null != dto.getParam() && !dto.getParam().isEmpty()) {
			List<Condition> condList = dto.getParam();
			for (Condition condition : condList) {
				hql += " and " + condition.getName() + " " + condition.getOperation() + " ? ";// " :"+condition.getName();
			}
		}
		return hql;
	}

	private Query setParamters(Query query, PostQuery dto) {
		if (null != dto.getParam() && !dto.getParam().isEmpty()) {
			List<Condition> condList = dto.getParam();
			int i = 0;
			for (Condition condition : condList) {
				// query.setParameter(condition.getName(),
				// condition.getValue());
				Object value = condition.getValue();
				if (value instanceof Date) {
					query.setDate(i, (Date) value);
				} else {
					query.setParameter(i, value);
				}
				i++;
			}// end.for
		}
		return query;
	}

	/**
	 * 组装hql字符串并生成Query
	 * 
	 * @param session
	 * @param hql
	 * @param qc
	 * @return
	 */
	private Query createQuery(Session session, String hql, PostQuery dto) {
		// 拼装查询条件字符串
		hql = buildHql(hql, dto);
		// 拼装分组字符串
		if (StringUtils.isNotBlank(dto.getGroupBy()))
			hql += (dto.getGroupBy().trim().toLowerCase().startsWith("group by") ? " " : " group by ") + dto.getGroupBy();

		// 拼装排序字符串
		if (StringUtils.isNotBlank(dto.getOrderBy()))
			hql += (dto.getOrderBy().trim().toLowerCase().startsWith("order by") ? " " : " order by ") + dto.getOrderBy();

		logger.logDebug(hql);
		Query query = session.createQuery(hql);
		// 为查询条件置值
		query = this.setParamters(query, dto);
		if (dto.getPageNo() != 0 && dto.getPageSize() != 0) {
			query.setFirstResult((dto.getPageNo() - 1) * dto.getPageSize());
			query.setMaxResults(dto.getPageSize());
		}

		return query;
	}

	@Override
	public int executeUpdate(String hql) {
		return this.executeUpdate(new String[] { hql });
	}

	@Override
	public int executeUpdate(String[] hqls) {
		Session session = null;
		int ret = 0;
		try {
			session = this.getSession();
			for (String hql : hqls) {
				if (StringUtils.isBlank(hql))
					continue;
				ret += session.createQuery(hql).executeUpdate();
			}
			session.flush();
			return ret;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}

	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return this.getJdbcTemplate();
	}

	/**
	 * 查询所有数据
	 * 
	 * @param sql
	 * @return
	 */
	public List<T> getSqlList(String sql, Class<T> clazz) {
		Session session = null;
		Query query = null;
		try {
			session = this.getSession();
			// 查询分页数据
			query = session.createSQLQuery(sql).addEntity(clazz);
			List<T> list = query.list();
			return list;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	/**
	 * 自定义sql查询分页数据
	 * 
	 * @param sql
	 *            语句
	 * @param objs
	 *            参数
	 * @param pageSize
	 *            页大小
	 * @param pageNo
	 *            页码
	 * @return
	 */
	public Map<String, Object> getSqlListByPage(String sql, Object[] objs, int pageSize, int pageNo) {
		Session session = null;
		Query query = null;
		try {
			session = this.getSession();

			long totalCount = this.getSqlCounts(sql, objs);

			if (pageSize == 0)
				pageSize = 10;
			int mod = (int) totalCount % pageSize;
			long totalPages = totalCount / pageSize + (mod > 0 ? 1 : 0);

			if (pageNo < 1)
				pageNo = 1;
			if (pageNo > totalPages)
				pageNo = (int) totalPages;

			// 查询分页数据
			query = session.createSQLQuery(sql);
			setParameter(query, objs);
			String[] alias = HandlerDAO.parseSqlAliases(query.getQueryString());
			if (pageNo == 1) {
				query.setFirstResult(0);
				query.setMaxResults(pageSize);
			} else if (pageNo != 0 && pageSize != 0) {
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("totalPages", totalPages);
			map.put("totalCount", totalCount);
			map.put("datas", makeList(query, HashMap.class, alias));
			return map;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	protected void setParameter(Query query, Object[] objs) {
		if (objs == null)
			return;
		int length = objs.length;
		for (int index = 0; index < length; index++) {
			query.setParameter(index, objs[index]);
		}
	}

	private long getSqlCounts(String sql, Object[] objs) {
		Session session = this.getSession();
		if (!sql.startsWith("from")) {
			sql = "select count(*) as nums from" + sql.split("FROM")[1];
			logger.logDebug("counts sql-----:" + sql);
		}
		try {
			Query query = session.createSQLQuery(sql);
			setParameter(query, objs);
			long intRtn = Long.parseLong(query.uniqueResult().toString());
			logger.logDebug("counts sql-----intRtn:" + intRtn);
			return intRtn;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	@SuppressWarnings("hiding")
	protected <T> List<T> makeList(Query query, Class<T> rtnType, String[] alias) {
		List<Object> list = query.list();
		List<T> rtnList = new ArrayList<T>(list.size());
		for (Object o : list) {
			if (o.getClass().equals(rtnType)) {
				rtnList.add((T) o);
			} else {
				rtnList.add(makeBean(query, (Object[]) o, rtnType, alias));
			}
		}
		return rtnList;
	}

	/**
	 * @description 新建对象并赋值
	 * @param query
	 * @param os
	 * @param rtnType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "hiding" })
	public <T> T makeBean(Query query, Object[] os, Class<T> rtnType, String[] alias) {
		T t = null;
		try {
			if (ObjUtils.isClass(rtnType, Map.class)) {
				t = (T) new HashMap();
			} else {
				t = rtnType.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		if (t == null) {
			throw new RuntimeException("error new instance for type [" + rtnType + "]");
		}
		int length = os.length;
		for (int index = 0; index < length; index++) {
			ObjUtils.setValue(t, alias[index], os[index]);
		}
		return t;
	}

	/**
	 * 查询指定条数据
	 * 
	 * @param sql
	 * @return
	 */
	public List<T> getHqlList(String hql, int firstResult, int maxResults) {
		Session session = null;
		Query query = null;
		try {
			session = this.getSession();
			// 查询分页数据
			query = session.createQuery(hql);
			if (maxResults != 0) {
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
			}
			List<T> list = query.list();
			return list;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

}
