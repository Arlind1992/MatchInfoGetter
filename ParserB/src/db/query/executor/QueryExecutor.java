package db.query.executor;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;

import db.DbConnector;

public class QueryExecutor {
	private static final String path=System.getProperty("user.dir")+ "\\query\\";
	
	public static ResultSet excecuteQuery(String query,Map<String,String> params ) throws ClassNotFoundException, SQLException, FileNotFoundException {
		Statement stmt = null;
		stmt = DbConnector.getConnection().createStatement();
		String sql=substituteParams(getQueryByName(query), params);
		return stmt.executeQuery(sql);
	}
	public static void excecuteUpdate(String query,Map<String,String> params) throws SQLException, ClassNotFoundException, FileNotFoundException {
		Statement stmt = null;
		stmt = DbConnector.getConnection().createStatement();
		String sql=substituteParams(getQueryByName(query), params);
		stmt.executeUpdate(sql);
	}
	

	public  String getPath() {
		return path;
	}

	
	private static String getQueryByName(String queryName) throws FileNotFoundException {
		String queryToReturn="";
		File file = new File(path+queryName);
	    Scanner scanner= new Scanner(file);
		
	    while(scanner.hasNextLine()) {
	    	queryToReturn=queryToReturn+" "+scanner.nextLine();
	    }
	    scanner.close();
	    return queryToReturn;
	}
	private static String substituteParams(String query,Map<String,String> params) {
		String toReturnQuery=query;
		if(params==null) {
			return toReturnQuery;
		}
		for(String key:params.keySet()) {
			toReturnQuery=toReturnQuery.replaceAll("\\{"+key+"\\}", params.get(key));
		}
		return toReturnQuery;
		
	}
	
	
}
