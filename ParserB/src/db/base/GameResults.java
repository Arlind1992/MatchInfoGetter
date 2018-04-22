package db.base;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import db.query.executor.QueryExecutor;

public class GameResults {
	private String flashscore_match_id;
	private String goalHome;
	private String goalAway;
	public GameResults(String flash_id,String goalHome,String goalAway) {
		this.flashscore_match_id=flash_id;
		this.goalAway=goalAway;
		this.goalHome=goalHome;
	}
	public String getFlashscore_match_id() {
		return flashscore_match_id;
	}
	public void setFlashscore_match_id(String flashscore_match_id) {
		this.flashscore_match_id = flashscore_match_id;
	}
	public String getGoalHome() {
		return goalHome;
	}
	public void setGoalHome(String goalHome) {
		this.goalHome = goalHome;
	}
	public String getGoalAway() {
		return goalAway;
	}
	public void setGoalAway(String goalAway) {
		this.goalAway = goalAway;
	}
	public void insertIntoDb() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Map<String,String> params=new HashMap<String,String>();

		params.put("goalHome", this.goalHome);
		params.put("goalAway", this.goalAway);
		params.put("flashscore_match_id", this.flashscore_match_id);
		QueryExecutor.excecuteUpdate("insertResults", params);
	}
	
}
