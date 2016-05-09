package com.lzhupload.common.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface BaseDao {
	
	List selectForList(String sqlname) throws Exception;

	List selectForList(String sqlname, Object obj) throws Exception;

	Object selectObject(String sqlname) throws Exception;

	Object selectObject(String sqlname, Object obj) throws Exception;

	Integer insertSql(String sqlname) throws Exception;

	Integer insertSql(String sqlname, Object obj) throws Exception;
	
	Long insertSqlLong(String sqlname, Object obj) throws Exception;
	
	String insertSqlString(String sqlname, Object obj) throws Exception;

	Integer insertSql(String sqlname, List objs) throws Exception;

	Integer updateSql(String sqlname) throws Exception;

	Integer updateSql(String sqlname, Object obj) throws Exception;

	Integer updateSql(String sqlname, List objs);

	Integer deleteSql(String sqlname) throws Exception;

	Integer deleteSql(String sqlname, Object obj) throws Exception;

	Integer deleteSql(String sqlname, List objs);

	Integer selectByCount(String sqlname) throws Exception;

	Integer selectByCount(String sqlname, Object obj) throws Exception;

	Double selectDouble(String sqlname) throws Exception;

	Double selectDouble(String sqlname, Object obj) throws Exception;
	
	Float selectFloat(String sqlname) throws Exception;

	Float selectFloat(String sqlname, Object obj) throws Exception;
	
	Date selectDate(String sqlname) throws Exception;

	Date selectDate(String sqlname, Object obj) throws Exception;
	
	Object getObject(String sqlname, Object obj) throws Exception;

	void doTransaction(List<HashMap<String, Object>> list) throws Exception;

	void doPatchDelTransaction(List list) throws Exception;
	
	String selectString(String sqlname, Object obj) throws Exception;
}
