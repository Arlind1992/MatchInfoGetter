package parser.functions;

import java.io.FileNotFoundException;
import java.lang.instrument.ClassFileTransformer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.base.Classification;
import db.base.Coef;
import db.info.getters.DBInfoGetter;
import db.query.executor.QueryExecutor;
import parser.data.Match;

public class ResultParser {
	
	public static List<Match> getMatchesByChampionships(String toParse){
		List<Match> toReturn=new ArrayList<Match>();
		String[] champis=toParse.split("ZA÷");
		System.out.println("size of champis = "+champis.length);
		for(String check:champis) {
			int indexTocheck=check.indexOf("¬");
			if(indexTocheck>6 ) {
				String champToCheck=check.substring(0,indexTocheck);
				if(DBInfoGetter.getChampionships().contains(champToCheck)) {	
					toReturn.addAll(getMatches(check,champToCheck));
				}
			}
		}
		return toReturn;
	}
	
	public static List<Match> getMatches(String toParse,String competition) {
		List<Match> toReturn=new ArrayList<Match>();
		String[] rows=toParse.split("~");
		for(int i=0;i<rows.length;i++) {
			if(rows[i].startsWith("AA")) {
				Match toAdd=parseRow(rows[i]);
				toAdd.setCompetition(competition);
				toReturn.add(toAdd);
			}
		}
		return toReturn;
	}
	
	private static Match parseRow(String row) {
		String[] allInfo=row.split("¬");
		String homeT="";
		String awayT="";
		String flashID="";
		int goalHome=-1;
		int goalAway=-1;
		for(int i=0;i<allInfo.length;i++) {
			if(allInfo[i].startsWith("AA")) {
				flashID=allInfo[i].split("÷")[1];
			}
			if(allInfo[i].startsWith("WU")) {
				homeT=allInfo[i].split("÷")[1];
			}
			if(allInfo[i].startsWith("WV")) {
				awayT=allInfo[i].split("÷")[1];
			}
			if(allInfo[i].startsWith("AG")) {
				goalHome=Integer.parseInt(allInfo[i].split("÷")[1]);
			}
			if(allInfo[i].startsWith("AH")) {
				goalAway=Integer.parseInt(allInfo[i].split("÷")[1]);
			}
			
		}
		return new Match(flashID,homeT,awayT,goalHome,goalAway);
	}
	public static Coef getBetCoeficient(String toParse,String flashId) {
		String[] rows=toParse.split("~");
		for(int i=0;i<rows.length;i++) {
			String[] inRow=rows[i].split("¬");
			for(int j=0;j<inRow.length;j++) {
				if(inRow[i].startsWith("MI")) {
					return createCoef(inRow[i],flashId);
				}
			}
		}
		return null;
	}
	private static Coef createCoef(String toParse,String flashId) {
		String[] coefs=toParse.split("÷")[1].split("\\|");
		String njish=coefs[0];
		String x=coefs[1];
		String dysh=coefs[2];
		//games not quoted by bet365 get give parsing errors when trying to get transformed into number (value is -)
		return new Coef(flashId,njish,x,dysh,"bet365");
	}
	
	public static String[] getMatchId(String html) {
		String[] toReturn=new String[2];
		int encInd= html.indexOf("participantEncodedIds");
		String idMatches=html.substring(encInd+25,encInd+45);
		toReturn=idMatches.replace("'", "").split(",");
		
		return toReturn;
		
	}
	
	public static List<Classification> getClassifications(String toParse,String competitionId,String competitionStage,String type){
		List<Classification> toReturn=new ArrayList<Classification>();
		String[] allRows=toParse.split("<tr class=\"");
		for(String row:allRows) {
			if(row.startsWith("odd")||row.startsWith("even"))
				toReturn.add(parseClassifRow(row,competitionId,competitionStage,type));
		}
		return toReturn;
	}
	private static Classification parseClassifRow(String toParse,String competitionId,String competitionStage,String type) {
		String week=getBetCol(toParse,"matches_played");
		int parInd=toParse.indexOf("glib-participant-");
		String teamId=toParse.substring(parInd+"glib-participant-".length(),parInd+"glib-participant-".length()+8);
		String[] totGoals=getBetCol(toParse,"goals").split(":");
		String goalsScored=totGoals[0];
		String goalsTaken=totGoals[1];
		String wins=getBetCol(toParse,"wins");
		String draws=getBetCol(toParse,"draws");
		String losses=getBetCol(toParse,"losses");
		String colSub=toParse.substring(toParse.indexOf("col_goals")+10, toParse.length());
		String points=getBetCol(colSub,"goals");
		
		
		return new Classification(week,competitionId,competitionStage,teamId,goalsScored,goalsTaken,wins,draws,losses,points,type);
	}
	private static String getBetCol(String toParse,String col) {	
		String colSub=toParse.substring(toParse.indexOf("col_"+col), toParse.length());
		return colSub.substring(("col_"+col).length()+2, colSub.indexOf("</"));
		
	}
	
}
