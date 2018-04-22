package db.base;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import db.query.executor.QueryExecutor;

public class Teams {
	private String flashscore_team_id;
	private String name;
	private String competition;
	public Teams(String flashscore_team_id,String name,String competition) {
		this.name=name;
		this.flashscore_team_id=flashscore_team_id;
		this.competition=competition;
	}
	
	public String getFlashscore_team_id() {
		return flashscore_team_id;
	}
	public void setId(String id) {
		this.flashscore_team_id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void insertIntoDB() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Map<String,String> params=new HashMap<String,String>();
		params.put("name", this.name);
		params.put("flashscore_team_id", this.flashscore_team_id);
		QueryExecutor.excecuteUpdate("insertTeams", params);
	}
	public void updateCompetition() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Map<String,String> params=new HashMap<String,String>();
		params.put("competition", this.competition);
		params.put("flashscore_team_id", this.flashscore_team_id);
		QueryExecutor.excecuteUpdate("insertCompetition", params);
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}
}
