/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nselive;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author kmacharia
 */
public class NSELive {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String url = "http://www.nellydata.com/CapitalFM/livedata.asp";
            //fetch data
            String docString = Jsoup.connect(url).get().toString();
            String[] tBodyArray = docString.split("<tbody>");
            String[] tableArray = tBodyArray[1].split("</tbody>");
            String tableContent = tableArray[0].trim();
            //delete header rows
            String[] headerlessContent = tableContent.split("Low</strong> </td>");
            String[] rowArray = headerlessContent[1].split("<tr>");
            //skip rowArray[0] which has string "</tr>
            for (int i = 1; i <= rowArray.length - 1; i++) {
                String rowContent = rowArray[i];
                String[] cellArray = rowContent.split("</td>");
                Stock stock = new Stock();
                String[] idArray = cellArray[0].split("mycell\">");
                stock.setId(Integer.parseInt(idArray[1]));
                String[] stockNameArray1 = cellArray[1].split("<strong>");
                String[] stockNameArray2 = stockNameArray1[1].split("</strong>");
                stock.setName(stockNameArray2[0]);
                String[] priceYesterdayArray = cellArray[2].split("mycell\">");
                stock.setPriceYesterday(Double.parseDouble(priceYesterdayArray[1].replace(",", "")));
                String[] currentPriceArray = cellArray[3].split("style2\">");
                stock.setCurrentPrice(Double.parseDouble(currentPriceArray[1].replace(",", "")));
                if (stock.getCurrentPrice() != stock.getPriceYesterday()) {
                    String tweet = "";
                    //TODO: Change to hourly updates
                    if (stock.getCurrentPrice() > stock.getPriceYesterday()) {
                        tweet = stock.getName().toUpperCase() + " has RISEN to " + stock.getCurrentPrice() + " from " + stock.getPriceYesterday() + " yesterday";
                    } else if (stock.getCurrentPrice() < stock.getPriceYesterday()) {
                        tweet = stock.getName().toUpperCase() + " has FALLEN to " + stock.getCurrentPrice() + " from " + stock.getPriceYesterday() + " yesterday";
                    }
                    //get the following from your twitter account
                    String consumerKey = "yourConsumerKey";
                    String consumerSecret = "yourConsumerSecret";
                    String accessToken = "yourAccessToken";
                    String accessSecret = "yourAccessSecret";

                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true)
                            .setOAuthConsumerKey(consumerKey)
                            .setOAuthConsumerSecret(consumerSecret)
                            .setOAuthAccessToken(accessToken)
                            .setOAuthAccessTokenSecret(accessSecret);

                    try {
                        TwitterFactory factory = new TwitterFactory(cb.build());
                        Twitter twitter = factory.getInstance();

                        Status status = twitter.updateStatus(tweet);
                        System.out.println("NEW TWEET: " + status.getText());
                    } catch (TwitterException te) {
                        te.printStackTrace();
                        System.exit(-1);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Ooops! No data this time. Our connection timed out :(");
            Logger.getLogger(NSELive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
