package db.info.getters;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.base.Teams;
import db.query.executor.QueryExecutor;

public class DBInfoGetter {
	private static Set<String> championships;
	
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
	public static Set<String> getChampionships() {
		if(championships!=null)
			return championships;
		championships=new HashSet<String>();
		ResultSet comp=null;
		try {
			comp=QueryExecutor.excecuteQuery("getCompetitions", null);
		} catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
			System.out.println("There was a problem with getting the competitions, e: "+e);
		}
		try {
			while(comp.next()){
				String cToAdd = comp.getString("competition");
				championships.add(cToAdd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return championships;
	}
	
}
