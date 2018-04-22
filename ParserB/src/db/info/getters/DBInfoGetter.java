package db.info.getters;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.base.Teams;
import db.query.executor.QueryExecutor;

public class DBInfoGetter {

	public static List<Teams> getAllTeams() throws ClassNotFoundException, FileNotFoundException, SQLException{
		List<Teams> toReturn=new ArrayList<Teams>();
		ResultSet res=QueryExecutor.excecuteQuery("getAllGames", null);
		while(res.next()){
			//Retrieve by column name
			String flash_id = res.getString("flashscore_team_id");
			String name = res.getString("name");
			String competition = res.getString("competition");
			System.out.println(flash_id+" - "+name);
			toReturn.add(new Teams(flash_id,name,competition));
		}
		
		return toReturn;
	}
	
}
