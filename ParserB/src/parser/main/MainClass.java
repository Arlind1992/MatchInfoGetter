package parser.main;


import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import db.DbConnector;
import db.base.Coef;
import db.base.GameResults;
import db.base.Games;
import db.base.Teams;
import db.info.getters.DBInfoGetter;
import db.query.executor.QueryExecutor;
import parser.data.Match;
import parser.functions.InfoFlashscoreGetter;
import parser.functions.ResultParser;

public class MainClass {

	public static void main(String [ ] args) {
		Element e=null;
		try {
			int day=1;
			Date d=null;//getDate(day);
			//parse by day
			/*
			Date d=getDate(day);
			String toParse=InfoFlashscoreGetter.getAllGames(day);
			insertGamesInDb(toParse,d);
			*/
			//parse by team
			List<Teams>all= DBInfoGetter.getAllTeams();
			boolean con=false;
			for(Teams t:all) {
				if(t.getName().equals("veria"))
					con=true;
				if(con) {
					Thread.sleep(40000);
					String toParse=InfoFlashscoreGetter.getInfoPerTeam( t.getFlashscore_team_id(),t.getName());
					insertGamesInDb(toParse,d);
				}
			}
			//Teams team=new Teams("AC Milan");
			//team.insertIntoDB();
			/*QueryExecutor query=new QueryExecutor();
			
			ResultSet rs = query.excecuteQuery("test", null);
			//STEP 5: Extract data from result set
			while(rs.next()){
			//Retrieve by column name
			int id = rs.getInt("id");
			String age = rs.getString("Name");
			System.out.println(id);
			//rs.
			// String first = rs.getString("first");
			// String last = rs.getString("last");
			}*/
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
	
	private static Date getDate(int day) {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, day);
	    return cal.getTime();
	}
	//if d is null is a game where can not find the date
	private static void insertGamesInDb(String toParse,Date d) throws ClassNotFoundException, FileNotFoundException, SQLException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<Match> matches=ResultParser.getMatchesByChampionships(toParse);
		for(Match m:matches) {
			String[] teamIds=ResultParser.getMatchId(InfoFlashscoreGetter.getMatchHtml(m.getFlashId()));
			Teams homeTeam=new Teams(teamIds[0],m.getHomeTeam(),m.getCompetition());
			Teams awayTeam=new Teams(teamIds[1],m.getAwayTeam(),m.getCompetition());
			homeTeam.insertIntoDB();
			homeTeam.updateCompetition();
			awayTeam.insertIntoDB();
			awayTeam.updateCompetition();
			if(d!=null) {
				new Games(m.getFlashId(),homeTeam.getFlashscore_team_id(),awayTeam.getFlashscore_team_id(),df.format(d)).insertIntoDB();;
			}else {
				new Games(m.getFlashId(),homeTeam.getFlashscore_team_id(),awayTeam.getFlashscore_team_id(),"10-10-1990").insertIntoDB();;	
			}
			Coef mCoef=ResultParser.getBetCoeficient(InfoFlashscoreGetter.getCoef(m.getFlashId()), m.getFlashId());
			if(mCoef.getHomeW() == null || mCoef.getHomeW().isEmpty()|| mCoef.getHomeW().equals("-"))
				continue;
			mCoef.insertIntoDb();
			if(m.getAwayGoals()!=-1) {
				new GameResults(m.getFlashId(),Integer.toString(m.getHomeGoals()),Integer.toString(m.getAwayGoals())).insertIntoDb();;
			}
			
			System.out.println("ID: "+m.getFlashId()+" homeT: "+m.getHomeTeam()+" awayT: "+ m.getAwayTeam() 
			+ " 1-"+mCoef.getHomeW() +" x-"+mCoef.getDraw() +" 2-"+ mCoef.getAwayW()
			);
		}
	}
	
}