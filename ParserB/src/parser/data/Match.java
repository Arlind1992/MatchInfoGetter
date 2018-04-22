package parser.data;

import java.util.ArrayList;
import java.util.List;

import db.base.Coef;

public class Match {
	
	private String flashId;
	private String homeTeam;
	private String awayTeam;
	private List<Coef> coeficients;
	private String competition;
	private int homeGoals=-1;
	private int awayGoals=-1;
	public Match(String flashId,String home,String away,int homeGoals,int awayGoals) {
		this.flashId=flashId;
		this.homeTeam=home;
		this.awayTeam=away;
		coeficients=new ArrayList<Coef>();
		this.homeGoals=homeGoals;
		this.awayGoals=awayGoals;
	}
	
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	
	public String getFlashId() {
		return flashId;
	}
	public void setFlashId(String flashId) {
		this.flashId = flashId;
	}

	public List<Coef> getCoeficients() {
		return coeficients;
	}

	public void setCoeficients(List<Coef> coeficients) {
		this.coeficients = coeficients;
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public void setHomeGoals(int homeGoals) {
		this.homeGoals = homeGoals;
	}

	public int getAwayGoals() {
		return awayGoals;
	}

	public void setAwayGoals(int awayGoals) {
		this.awayGoals = awayGoals;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}
}
