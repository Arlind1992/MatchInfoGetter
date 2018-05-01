package parser.main;


import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import db.DbConnector;
import db.base.Classification;
import db.base.Coef;
import db.base.Competition;
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
			//insertAllClassifications();
			//String[] st=InfoFlashscoreGetter.getCompInfo("france", "ligue-1");
			//System.out.println("id-"+ st[0]+" st- "+st[1]);
			int day=0;
			//parse by day
			Date d=getDate(day);
			String toParse=InfoFlashscoreGetter.getAllGames(day);
			insertGamesInDb(toParse,d);
			System.out.println("Date : "+new SimpleDateFormat("yyyy-MM-dd").format(d) );
			//parse by team
			
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
			//needed only if new teams available
			/*homeTeam.insertIntoDB();
			homeTeam.updateCompetition();
			awayTeam.insertIntoDB();
			awayTeam.updateCompetition();*/
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
	public static void updateCompInfo() throws ClassNotFoundException, FileNotFoundException, SQLException {
		for(String champ:DBInfoGetter.getChampionships()) {
			String[] sp=champ.split(":");
			String nation=sp[0].toLowerCase();
			String[] leag=sp[1].trim().split(" ");
			String league="";
			if(leag.length==1) {
				league=leag[0];
			}else {
				league=(leag[0]+"-"+leag[1]).replace(".", "").toLowerCase();
			}
			String[] st=InfoFlashscoreGetter.getCompInfo(nation, league);
			System.out.println("id-"+ st[0]+" st- "+st[1]);
			new Competition(champ,st[0],st[1]).insertTournamentIdAndStage();;
		}
	}
	public static void insertAllClassifications() throws ClassNotFoundException, FileNotFoundException, SQLException {
		List<String> types = new ArrayList<String>();
		types.add("overall");
		types.add("home");
		types.add("away");
		//System.out.println(InfoFlashscoreGetter.getClassifications("hn9DAGLG", "M5Lr4AbP"));
		for(String t:types) {
			for(Competition comp:DBInfoGetter.getAllCompetitions()) {
				System.out.println(comp.getCompetition()+" "+t);
				List<Classification> classList=ResultParser.getClassifications(InfoFlashscoreGetter.getClassifications(comp.getTournamentId(), comp.getTournamentStage(),t),comp.getTournamentId(),comp.getTournamentStage(),t);
				System.out.println("Comp: "+comp.getCompetition());
				for(Classification c:classList) {
					System.out.println("ID "+c.getTeamId()+" D= "+c.getDraws()+" GS="+c.getGoalsScored()+" GT= "+c.getGoalsTaken()+
							" P= "+c.getPoints()+" W= "+c.getWins()+" L= "+c.getLosses());
					if(c.getPoints().length()>3)
						c.setPoints(c.getPoints().substring(c.getPoints().indexOf(">")+1, c.getPoints().length()));
					c.insertIntoDb();
				}
				
			}
		}
	}
	
	public static void insertAllTeams() throws ClassNotFoundException, FileNotFoundException, SQLException, InterruptedException {
		Date d=null;//getDate(day);
		List<Teams>all= DBInfoGetter.getAllTeams();
		boolean con=false;
		for(Teams t:all) {
			if(t.getName().equals("as-roma"))
				con=true;
			if(con) {
				Thread.sleep(60000);
				String toParse=InfoFlashscoreGetter.getInfoPerTeam( t.getFlashscore_team_id(),t.getName());
				insertGamesInDb(toParse,d);
			}
		}
	}
	
	
}
