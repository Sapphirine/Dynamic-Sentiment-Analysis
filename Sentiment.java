package edu.columbia.twitter.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.likethecolor.alchemy.api.Client;
import com.likethecolor.alchemy.api.call.AbstractCall;
import com.likethecolor.alchemy.api.call.SentimentCall;
import com.likethecolor.alchemy.api.call.type.CallTypeText;
import com.likethecolor.alchemy.api.entity.Response;
import com.likethecolor.alchemy.api.entity.SentimentAlchemyEntity;


public class Sentiment {
	public static void main(String[] args) throws IOException {
		final String apiKey = "xxxxxxxxxxxxxxxxxx";
		final Client client = new Client(apiKey);

		File file = new File("Sentiment_Output.txt");
		final FileOutputStream fs = new FileOutputStream(file, true);

		final AbstractCall<SentimentAlchemyEntity> sentimentCall = new SentimentCall(new CallTypeText("Data.txt"));
		final Response<SentimentAlchemyEntity> sentimentResponse = client.call(sentimentCall);
		BufferedReader br = new BufferedReader(new FileReader("Data.txt"));
		String line;

		while ((line = br.readLine()) != null){
			SentimentAlchemyEntity entity;
			final Iterator<SentimentAlchemyEntity> iter = sentimentResponse.iterator();
			while(iter.hasNext()) {
				entity = iter.next();
				String data = line + " " + sentimentResponse.getLanguage() + " " + sentimentResponse.getStatus() + " "
						+ sentimentResponse.getStatusInfo() + " " + (entity.isMixed() ? "true" : "false") 
						+ " " + entity.getScore() + " " + entity.getType() + "\n";
				byte[] contentInBytes = data.getBytes();
				fs.write(contentInBytes);
				fs.flush();
			}
		}
		br.close();
	}
}
