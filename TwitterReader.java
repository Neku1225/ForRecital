import javax.swing.JOptionPane;

import twitter4j.FilterQuery;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterReader {
	final String consumerKey = "5gvlkLeKcA9y3or5Sg5fS2tLm";
	final String consumerSecret = "Fqpn8NeU6NqDtXeOZWU2bDKQ0RxwVa4NdRYSsdaqVmr1IFUrgS";
	final String accessToken = "3168259224-N2AxNMrGh5oXS2L9foLLEfpDUnVWZlZB3OiJgdo";
	final String accessSecret = "eJeRde0qcPVdaAqfhUG3mehZvOSQiIXYPtxK8H22t6jM3";
	StringPanel panel;
	StatusListener listener;
	ConfigurationBuilder cb;
	FilterQuery Filter;
	TwitterStream twitterStream;
	QueryResult result;
	Configuration conf;
	String hashtag;

	public TwitterReader(StringPanel setPanel, String setHashtag) {
		panel = setPanel;
		// パスワードなしでログインするための処理
		cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessToken);
		cb.setOAuthAccessTokenSecret(accessSecret);
		conf = cb.build();

		Filter = new FilterQuery();
		hashtag = "#" + setHashtag;
		Filter.track(new String[] { hashtag });

		listener = new StatusListener() {
			public void onStatus(Status status) {
				if (status.getURLEntities().length == 0 && status.getMediaEntities().length == 0) {
					if (status.isRetweet() == false && status.getInReplyToStatusId() == -1) {
						String strText = status.getText();

						// ツイートから改行記号を除去（半角スペースに変換）
						strText = strText.replaceAll("\r\n", " ");
						strText = strText.replaceAll("\r", " ");
						strText = strText.replaceAll("\n", " ");

						// ツイートからタブ記号を除去（半角スペースに変換）
						strText = strText.replaceAll("\t", " ");

						strText = strText.replaceAll(hashtag, " ");

						panel.setMessage(strText);

						System.out.println(strText + "\n");
					}
				}
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// ツイートが削除された時に通知されるようです
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(panel, "ツイート取得中にエラーが起きました");
			}

			public void onStallWarning(StallWarning arg0) {
			}

			public void onScrubGeo(long arg0, long arg1) {
				// ツイートの位置情報が削除された場合に通知されるようです
			}
		};
		twitterStream = new TwitterStreamFactory(conf).getInstance();
		twitterStream.addListener(listener);

		// Tweet 取得
		twitterStream.filter(Filter);
	}

}