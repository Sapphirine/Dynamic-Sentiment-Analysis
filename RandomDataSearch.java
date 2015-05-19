package edu.columbia.twitter.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public final class RandomDataSearch {
	public static void main(String[] args) throws TwitterException, IOException {
		
		//Details
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("o0mrxTWmGIyUZZuEqSt02jw63")
		.setOAuthConsumerSecret("cScTqQ9kOIAy6W9kbNo18sZFTNsIobRnlxrmKa7AiONkEC4oLN")
		.setOAuthAccessToken("3035599293-A7swrDIgqix1e1Hrfb7DkA6Ze6xeAawDOtvffdO")
		.setOAuthAccessTokenSecret("ek8z0zPPFZiXk0JA6Kp91oq53PBit3EYT2jaloU31LJqq");

		File file = new File("Data.txt");
		final FileOutputStream fs = new FileOutputStream(file, true);
			

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				String data = status.getText() + " Status by: " + status.getUser().getScreenName() 
						+ " with Location: " + status.getGeoLocation() + "\n\n";
				data.replaceAll("#",""); //Removing the hashtag for better Sentiment Analysis
				
				byte[] contentInBytes = data.getBytes();
				
				if(!status.isRetweet() && !data.contains("@") && !data.contains("http") 
						&& !data.contains("https") && status.getGeoLocation() != null) {
					try {
						fs.write(contentInBytes);
						fs.flush();
						System.out.println("DONE!!!");
					} catch (IOException e) {
						// Exception Handling
						System.out.println("Unable to open the File!! ERROR!!..");
					}
				}
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				//System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning:" + warning);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};
		twitterStream.addListener(listener);
		twitterStream.sample();
	}
}
