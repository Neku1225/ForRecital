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
		// �p�X���[�h�Ȃ��Ń��O�C�����邽�߂̏���
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

						// �c�C�[�g������s�L���������i���p�X�y�[�X�ɕϊ��j
						strText = strText.replaceAll("\r\n", " ");
						strText = strText.replaceAll("\r", " ");
						strText = strText.replaceAll("\n", " ");

						// �c�C�[�g����^�u�L���������i���p�X�y�[�X�ɕϊ��j
						strText = strText.replaceAll("\t", " ");

						strText = strText.replaceAll(hashtag, " ");

						panel.setMessage(strText);

						System.out.println(strText + "\n");
					}
				}
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// �c�C�[�g���폜���ꂽ���ɒʒm�����悤�ł�
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(panel, "�c�C�[�g�擾���ɃG���[���N���܂���");
			}

			public void onStallWarning(StallWarning arg0) {
			}

			public void onScrubGeo(long arg0, long arg1) {
				// �c�C�[�g�̈ʒu��񂪍폜���ꂽ�ꍇ�ɒʒm�����悤�ł�
			}
		};
		twitterStream = new TwitterStreamFactory(conf).getInstance();
		twitterStream.addListener(listener);

		// Tweet �擾
		twitterStream.filter(Filter);
	}

}