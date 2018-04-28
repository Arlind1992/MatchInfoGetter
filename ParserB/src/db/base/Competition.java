package db.base;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import db.query.executor.QueryExecutor;

public class Competition {
	private String competition;
	private String tournamentId;
	private String tournamentStage;
	public String getCompetition() {
		return competition;
	}
	public void setCompetition(String competition) {
		this.competition = competition;
	}
	public String getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(String tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getTournamentStage() {
		return tournamentStage;
	}
	public void setTournamentStage(String tournamentStage) {
		this.tournamentStage = tournamentStage;
	}
	public Competition(String competition,String tournamentId,String tournamentStage) {
		this.competition=competition;
		this.tournamentId=tournamentId;
		this.tournamentStage=tournamentStage;
	}
	public void insertTournamentIdAndStage() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Map<String,String> params=new HashMap<String,String>();
		params.put("competition", this.competition);
		params.put("tournamentId", this.tournamentId);
		params.put("tournamentStage", this.tournamentStage);
		
		QueryExecutor.excecuteUpdate("updateTournamentIdAndStage", params);
	}

}
