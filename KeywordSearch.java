package edu.columbia.twitter.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class KeywordSearch {
	public static void main(String[] args) throws TwitterException{
		// //A builder that can be used to construct a twitter4j configuration with desired settings.
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true) // Fill information here
		  .setOAuthConsumerKey("xxxxxxxxxxxx")
		  .setOAuthConsumerSecret("xxxxxxxxxxxxxxxx")
		  .setOAuthAccessToken("xxxxxxxxxxx")
		  .setOAuthAccessTokenSecret("xxxxxxxxxxxxx");
		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your keyword here: ");
		String in = input.nextLine();
		Query query = new Query(in); //Mention your search term here
		query.setCount(100); //Set the tweet count
		QueryResult result = twitter.search(query);
				
		File file = new File("Data.txt");
		try (BufferedWriter br = new BufferedWriter(new FileWriter(file))){
			try(FileOutputStream fs = new FileOutputStream(file)){
				for (Status status : result.getTweets()) {
					String test = status.getText() + "\n\n";
					byte[] contentInBytes = test.getBytes();
					fs.write(contentInBytes);
					fs.flush();
			    }
			}
		}
		catch (IOException e){
			System.out.println("Unable to Write to file: " + file.toString());
		}
		System.out.println("FInished!!!!");
	}
}
