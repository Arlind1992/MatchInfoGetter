package db.base;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import db.query.executor.QueryExecutor;

public class Classification {
	private String week;
	private String competitionId;
	private String competitionStage;
	private String teamId;
	private String goalsScored;
	private String goalsTaken;
	private String wins;
	private String draws;
	private String losses;
	private String points;
	private String type;
	public Classification(String week,String compId,String compStage,String teamId,
			String goalsScored,String goalsTaken,String wins,String draws,String loses,String points,String type) {
		this.week=week;
		this.competitionId=compId;
		this.competitionStage=compStage;
		this.goalsScored=goalsScored;
		this.goalsTaken=goalsTaken;
		this.wins=wins;
		this.draws=draws;
		this.losses=loses;
		this.points=points;
		this.teamId=teamId;
		this.type=type;
	}
	
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getCompetitionId() {
		return competitionId;
	}
	public void setCompetitionId(String competitionId) {
		this.competitionId = competitionId;
	}
	public String getCompetitionStage() {
		return competitionStage;
	}
	public void setCompetitionStage(String competitionStage) {
		this.competitionStage = competitionStage;
	}
	public String getGoalsScored() {
		return goalsScored;
	}
	public void setGoalsScored(String goalsScored) {
		this.goalsScored = goalsScored;
	}
	public String getGoalsTaken() {
		return goalsTaken;
	}
	public void setGoalsTaken(String goalsTaken) {
		this.goalsTaken = goalsTaken;
	}
	public String getWins() {
		return wins;
	}
	public void setWins(String wins) {
		this.wins = wins;
	}
	public String getDraws() {
		return draws;
	}
	public void setDraws(String draws) {
		this.draws = draws;
	}
	public String getLosses() {
		return losses;
	}
	public void setLoses(String loses) {
		this.losses = loses;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public void insertIntoDb() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Map<String,String> params=new HashMap<String,String>();

		params.put("week", this.week);
		params.put("competitionId", this.competitionId);
		params.put("competitionStage", this.competitionStage);
		params.put("teamId", this.teamId);
		params.put("goalsScored", this.goalsScored);
		params.put("goalsTaken", this.goalsTaken);
		params.put("wins", this.wins);
		params.put("draws", this.draws);
		params.put("losses", this.losses);
		params.put("points", this.points);
		params.put("type", this.type);
		QueryExecutor.excecuteUpdate("insertClassification", params);
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
