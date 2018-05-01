package parser.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public  class  InfoFlashscoreGetter {
	
	public static String getAllGames(int days) {
		return getInfoURI("https://d.flashscore.com/x/feed/f_1_"+Integer.toString(days)+"_1_en_1");
    }
	
	public static String getCoef(String flashID) {
		return getInfoURI("https://d.flashscore.com/x/feed/df_dos_1_"+flashID+"_");
	}
	
	
	private static String getInfoURI(String uri) {
		  // create custom http headers for httpclient
        List<Header> defaultHeaders = Arrays.asList(
                new BasicHeader("X-Default-Header", "default header httpclient"));

        // setting custom http headers on the httpclient
        CloseableHttpClient httpclient = HttpClients
                .custom()
                .setDefaultHeaders(defaultHeaders)
                .build();

        try {

            // setting custom http headers on the http request
            HttpUriRequest request = RequestBuilder.get()
                    .setUri(uri)
                    .setHeader("Referer","https://d.flashscore.com/x/feed/proxy-local")
                    .setHeader("x-fsign", "SW9D1eZo")
                    .setHeader("x-geoip","1")
                    .setHeader("x-referer","https://www.flashscore.com/")
                    .setHeader("X-Requested-With","XMLHttpRequest")
                    .setHeader("authority", "d.flashscore.com")
                    .build();

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(request, responseHandler);

            return responseBody;
        } catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return "";
	}
	public static String getMatchHtml(String matchId) {
			return callURL("https://www.flashscore.com/match/"+matchId);
	}
	
	
	
	private static String callURL(String myURL) {
		System.out.println("Requeted URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(2*60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						"UTF-8");
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} 
 
		return sb.toString();
	}	
	
	public static String getInfoPerTeam(String teamid,String teamName) {
		String htmlTeam=callURL("https://www.flashscore.com/team/"+teamName+"/"+teamid+"/results/");
		String toReturn=htmlTeam.substring(htmlTeam.indexOf("<div id=\"participant-page-data-results\">")+"<div id=\"participant-page-data-results\">".length(),htmlTeam.length());
		toReturn=toReturn.substring(0,toReturn.indexOf("</div>"));
		return toReturn;
	}
	public static String[] getCompInfo(String nation,String league) {
		String htmlComp=callURL("https://www.flashscore.com/football/"+nation+"/"+league+"/");
		String[] toReturn=new String[2];
		int indexOfTor=htmlComp.indexOf("\"tournament\":");
		toReturn[0]=htmlComp.substring(indexOfTor+"\"tournament\":".length()+1,indexOfTor+"\"tournament\":".length()+9 );
		int indexOfTorSt=htmlComp.indexOf("\"tournamentStage\":");
		toReturn[1]=htmlComp.substring(indexOfTorSt+"\"tournamentStage\":".length()+1,indexOfTorSt+"\"tournamentStage\":".length()+9 );
		return toReturn;		
	}
	
	public static String getClassifications(String compId,String compStage,String type) {
		return getInfoURI("https://d.flashscore.com/x/feed/ss_1_"+compId+"_"+compStage+"_table_"+type);
	}
	
}
