package db.base;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import db.query.executor.QueryExecutor;

public class Coef {
	private String flash_id;
	private String homeW;
	private String draw;
	private String awayW;
	private String provider;
	public Coef(String flash_id,String homeW,String draw,String awayW,String provider) {
		this.flash_id=flash_id;
		this.homeW=homeW;
		this.draw=draw;
		this.provider=provider;
		this.setAwayW(awayW);
	}
	public String getFlash_id() {
		return flash_id;
	}
	public void setFlash_id(String flash_id) {
		this.flash_id = flash_id;
	}
	public String getHomeW() {
		return homeW;
	}
	public void setHomeW(String homwW) {
		this.homeW = homwW;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getAwayW() {
		return awayW;
	}
	public void setAwayW(String awayW) {
		this.awayW = awayW;
	}
	public void insertIntoDb() throws ClassNotFoundException, FileNotFoundException, SQLException {
		Map<String,String> params=new HashMap<String,String>();

		params.put("homeW", this.homeW);
		params.put("draw", this.draw);
		params.put("awayW", this.awayW);
		params.put("flashscore_id", this.flash_id);
		params.put("provider", this.provider);
		QueryExecutor.excecuteUpdate("insertCoef", params);
	}
	
	
}
