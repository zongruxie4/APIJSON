/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package apijson.orm;

import java.sql.SQLException;
import java.sql.Savepoint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import apijson.NotNull;
import apijson.RequestMethod;

/**解析器
 * @author Lemon
 */
public interface Parser<T extends Object> {

	@NotNull
	Visitor<T> getVisitor();
	Parser<T> setVisitor(@NotNull Visitor<T> visitor);

	@NotNull
	RequestMethod getMethod();
	Parser<T> setMethod(@NotNull RequestMethod method);

	int getVersion();
	Parser<T> setVersion(int version);
	
	String getTag();
	Parser<T> setTag(String tag);

	JSONObject getRequest();
	Parser<T> setRequest(JSONObject request);

	Parser<T> setNeedVerify(boolean needVerify);
	
	boolean isNeedVerifyLogin();
	Parser<T> setNeedVerifyLogin(boolean needVerifyLogin);

	boolean isNeedVerifyRole();
	Parser<T> setNeedVerifyRole(boolean needVerifyRole);

	boolean isNeedVerifyContent();
	Parser<T> setNeedVerifyContent(boolean needVerifyContent);

	
	String parse(String request);
	String parse(JSONObject request);

	JSONObject parseResponse(String request);
	JSONObject parseResponse(JSONObject request);

	// 没必要性能还差 JSONObject parseCorrectResponse(String table, JSONObject response) throws Exception;


	JSONObject parseCorrectRequest() throws Exception;
	
	JSONObject parseCorrectRequest(RequestMethod method, String tag, int version, String name, JSONObject request,
			int maxUpdateCount, SQLCreator creator) throws Exception;
	
	
	JSONObject getStructure(String table, String method, String tag, int version) throws Exception;


	JSONObject onObjectParse(JSONObject request, String parentPath, String name, SQLConfig<T> arrayConfig, boolean isSubquery, JSONObject cache) throws Exception;

	JSONArray onArrayParse(JSONObject request, String parentPath, String name, boolean isSubquery, JSONArray cache) throws Exception;

	/**解析远程函数
	 * @param key
	 * @param function
	 * @param parentPath
	 * @param currentName
	 * @param currentObject
	 * @return
	 * @throws Exception
	 */
	Object onFunctionParse(String key, String function, String parentPath, String currentName, JSONObject currentObject, boolean containRaw) throws Exception;
	
	ObjectParser<T> createObjectParser(JSONObject request, String parentPath, SQLConfig<T> arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception;

	int getMinQueryPage();
	int getMaxQueryPage();
	int getDefaultQueryCount();
	int getMaxQueryCount();
	int getMaxUpdateCount();
	int getMaxSQLCount();
	int getMaxObjectCount();
	int getMaxArrayCount();
	int getMaxQueryDepth();
	
	void putQueryResult(String path, Object result);


	Object getValueByPath(String valuePath);


	void onVerifyLogin() throws Exception;
	void onVerifyContent() throws Exception;
	void onVerifyRole(SQLConfig<T> config) throws Exception;
	
	JSONObject executeSQL(SQLConfig<T> config, boolean isSubquery) throws Exception;
	
	SQLExecutor<T> getSQLExecutor();
	Verifier<T> getVerifier();
	
	
	Boolean getGlobalFormat();
	String getGlobalRole();
	String getGlobalDatabase();
	String getGlobalDatasource();
	String getGlobalNamespace();
	String getGlobalCatalog();
	String getGlobalSchema();
	Boolean getGlobalExplain();
	String getGlobalCache();

	
	int getTransactionIsolation();
	void setTransactionIsolation(int transactionIsolation);
	
	void begin(int transactionIsolation);
	void rollback() throws SQLException;
	void rollback(Savepoint savepoint) throws SQLException;
	void commit() throws SQLException;
	void close();


}
