package parser.functions;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.base.Coef;
import db.query.executor.QueryExecutor;
import parser.data.Match;

public class ResultParser {
	private static Set<String> championships;
	
	public static List<Match> getMatchesByChampionships(String toParse){
		List<Match> toReturn=new ArrayList<Match>();
		String[] champis=toParse.split("ZA÷");
		System.out.println("size of champis = "+champis.length);
		for(String check:champis) {
			int indexTocheck=check.indexOf("¬");
			if(indexTocheck>6 ) {
				String champToCheck=check.substring(0,indexTocheck);
				if(getChampionships().contains(champToCheck)) {	
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
