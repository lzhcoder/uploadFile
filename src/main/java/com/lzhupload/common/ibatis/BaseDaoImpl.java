package com.lzhupload.common.ibatis;

import java.net.ProxySelector;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ibatis.dao.client.DaoManager;
import com.ibatis.dao.client.template.SqlMapDaoTemplate;

 

public class BaseDaoImpl extends SqlMapDaoTemplate implements BaseDao {

	public BaseDaoImpl(DaoManager daoManager) {
		super(daoManager);
		ProxySelector.setDefault(null);
	}

	public String insertSqlString(String sqlname, Object obj) throws Exception {
		// TODO Auto-generated method stub
		return (String)insert(sqlname, obj);
	}
 
	public Integer insertSql(String sqlname) throws Exception {		
		return (Integer) insert(sqlname);
	}

	public Integer insertSql(String sqlname, Object obj) throws Exception {
		return (Integer)insert(sqlname, obj);
	}
	
	public Long insertSqlLong(String sqlname, Object obj) throws Exception {
		return (Long)insert(sqlname, obj);
	}
	
	public Integer insertSql(String sqlname, List objs) {
		try {
			//this.daoManager.startTransaction();
			for (int i = 0; i < objs.size(); i++) {
				Integer flag = this.insertSql(sqlname, objs.get(i));
			}
			//this.daoManager.commitTransaction();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			//this.daoManager.endTransaction();
		}
	}

	public Integer updateSql(String sqlname) throws Exception {
		return (Integer) update(sqlname);
	}

	public Integer updateSql(String sqlname, Object obj) throws Exception {
		return (Integer) update(sqlname, obj);
	}

	public Integer updateSql(String sqlname, List objs) {
		try {
			//this.daoManager.startTransaction();
			for (int i = 0; i < objs.size(); i++) {
				int flag = this.updateSql(sqlname, objs.get(i));
			}
			//this.daoManager.commitTransaction();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			//this.daoManager.endTransaction();
		}

	}

	public Integer deleteSql(String sqlname) throws Exception {
		return (Integer) delete(sqlname);
	}

	public Integer deleteSql(String sqlname, Object obj) throws Exception {
		return (Integer) delete(sqlname, obj);
	}

	public Integer deleteSql(String sqlname, List objs) {
		try {
			
			//this.daoManager.startTransaction();
			for (int i = 0; i < objs.size(); i++) {
				int flag = this.deleteSql(sqlname, objs.get(i));
			}
			//this.daoManager.commitTransaction();
			return 1;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			//this.daoManager.endTransaction();
		}
	}

	public List selectForList(String sqlname) throws Exception {
		List list = queryForList(sqlname);
		return list;
	}

	public List selectForList(String sqlname, Object obj) throws Exception {
		List list = queryForList(sqlname, obj);
		return list;
	}
	
	public Object selectObject(String sqlname) throws Exception {
		return queryForObject(sqlname);
	}
	
	public Object selectObject(String sqlname, Object obj) throws Exception {
		return queryForObject(sqlname, obj);
	}

	public Integer selectByCount(String sqlname) throws Exception {
		return (Integer) queryForObject(sqlname);
	}

	public Integer selectByCount(String sqlname, Object obj) throws Exception {
		return (Integer) this.queryForObject(sqlname, obj);
	}
	
	public Double selectDouble(String sqlname) throws Exception {
		return (Double) queryForObject(sqlname);
	}

	public Double selectDouble(String sqlname, Object obj) throws Exception {
		return (Double) this.queryForObject(sqlname, obj);
	}

	public Object getObject(String sqlname, Object obj) throws Exception {		
		return this.queryForObject(sqlname, obj);
	}
	
	public void doTransaction(List<HashMap<String, Object>> list)throws Exception {
		try {
			//this.daoManager.startTransaction();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = list.get(i);
				String sqlname = String.valueOf(map.get("sqlname"));
				Object obj = map.get("obj");
				int flag = this.deleteSql(sqlname, obj);
			}
			//this.daoManager.commitTransaction();
		} catch (Exception e) {
			throw e;
		} finally {
			//this.daoManager.endTransaction();
		}
	}
	
	public void doPatchDelTransaction(List list)throws Exception {
		try {
			//this.daoManager.startTransaction();
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap)list.get(i);
				String sqlname = String.valueOf(map.get("sqlname"));
				List obj = (List)map.get("obj");
				for (int j = 0; j < obj.size(); j++) {
					int flag = this.deleteSql(sqlname, obj.get(j));
				}
			}
			//this.daoManager.commitTransaction();
		} catch (Exception e) {
			throw e;
		} finally {
			//this.daoManager.endTransaction();
		}
	}

	public String selectString(String sqlname, Object obj) throws Exception {
		return (String) this.queryForObject(sqlname, obj);
	}

	public Date selectDate(String sqlname) throws Exception {
		return (Date) this.queryForObject(sqlname);
	}

	public Date selectDate(String sqlname, Object obj) throws Exception {
		return (Date) this.queryForObject(sqlname,obj);
	}

	public Float selectFloat(String sqlname) throws Exception {
		return (Float) this.queryForObject(sqlname);
	}

	public Float selectFloat(String sqlname, Object obj) throws Exception {
		return (Float) this.queryForObject(sqlname,obj);
	}

}
