package jp.co.touyouhk.nichannel.subjecttext;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.touyouhk.util.UniversalUtil;

/**
 * @author Wizard1
 * 2chのSubjectTextを処理するユーティリティ
 *
 * */

public class SubjectTextUtil {



	/**
	 * subject.txt　をダウンロード 取得し、生成したファイル名をフルパスでStringに返します。<br>
	 * 生成するファイル名は実行時の時間を表す（1970/00:00:00からの）秒数です。
	 * @param URL
	 * @param localFilePath 保存先フォルダ
	 * @return 保存したtxtのフルパス。取得失敗の時はNULL
	 */
	public static String download(String URL, String localFilePath) {
		String subjectUrl = URL + "subject.txt";
		String subjectFileName  = null;
		String subjectFileNamePath = null;
		try {
			int num;

			byte buf[] = new byte[4069];

			URL url = new URL(subjectUrl);

			Object obj = url.getContent();

			 subjectFileName = "subject" + UniversalUtil.getTimeString()
					+ ".txt";

			subjectFileNamePath = localFilePath
					+ System.getProperties().getProperty("file.separator")
					+ subjectFileName;

			UniversalUtil.createFile(subjectFileNamePath);

			FileOutputStream fo = new FileOutputStream(subjectFileNamePath);

			if (obj instanceof InputStream) {
				InputStream di = (InputStream) obj;
				while ((num = di.read(buf)) != -1) {

					fo.write(buf, 0, num);

				}
				di.close();
			}

			fo.close();

		} catch (Exception e) {
			e.printStackTrace();

			subjectFileNamePath = null;
		}

		return subjectFileNamePath;
	}

	/**
	 * subject.txtをJavaのモデル型に変換します<br>
	 * DBへの挿入を想定しEntity形式へ変換<br>
	 *
	 * @param filename subject.txtのファイルパス
	 * @return　変換したエンティティリスト
	 */
	public static List<SubjectTextEntity> subjectText2Entity(String filename) {
		List<SubjectTextEntity> enlist = new ArrayList<SubjectTextEntity>();

		try {

			// 文字コードを指定しないとデフォルトの文字コードがSJISでないJavaVM(Linux-OpenJDK[UTF8]など)
			// で文字が化けてDBに挿入される
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					filename), "SJIS");

			BufferedReader br = new BufferedReader(isr);

			String line;

			String threadNumber;

			String title;

			String threadResCountString;

			int threadResCount;

			while ((line = br.readLine()) != null) {
				SubjectTextEntity model = new SubjectTextEntity();

				// 1237284546.dat<>PS3に『ｶﾞﾝﾀﾞﾑ戦記』『NINJA
				// GAIDENΣ2』『ﾛﾛﾅのｱﾄﾘｴ』と新作が続々発表！完全にPS3時代の到来か？★2 (913)
				// タイトルにどんな文字がはいるのかわからないのでsplitは危険
				// 原始的に文字数でカットが無難

				threadNumber = line.substring(0, 10);

				String Lkakko = "(";

				String Rkakko = ")";

				title = line.substring(16,
						line.lastIndexOf(Lkakko.codePointAt(0)));

				threadResCountString = line.substring(
						line.lastIndexOf(Lkakko.codePointAt(0)) + 1,
						line.lastIndexOf(Rkakko.codePointAt(0)));

				threadResCount = new Integer(threadResCountString);

				model.setThreadNumber(threadNumber);
				model.setTitle(title);
				model.setThreadResCount(threadResCount);

				enlist.add(model);
			}

			br.close();
			isr.close();
		} catch (Exception e) {
			System.out.println("例外発生→ " + e.toString());
		}

		return enlist;
	}

	/**
	 * スレッドの10桁の番号から標準的なDatのURLへ変換します。
	 * (2chブラウザが読み込んでいる本物のdatではなく一般的なブラウザで開くときのURL)
	 *
	 * @param boardURL 板のURL http://kamome.2ch.net/magazin/
	 * @param threadNumer スレの10桁の番号 1316691172
	 * @return URL http://kamome.2ch.net/test/read.cgi/magazin/1316691172/
	 */
	public static String treadNumber2datURL(String boardURL, String threadNumer) {
		String datURL = null;

		String[] splitedUrl = boardURL.split("/");

		String hostName = splitedUrl[splitedUrl.length - 2];
		String boardName = splitedUrl[splitedUrl.length - 1];

		datURL = "http://" + hostName + "/test/read.cgi/" + boardName + "/"
				+ threadNumer + "/";

		return datURL;
	}

	/**
	 * SubjectTextのエンティティリストを受け取り、スレッドのランキングを生成します
	 *
	 * @param oldList
	 * @param newList
	 * @return
	 */
	public static List<SubjectTextEntity> generateTreadRanking(
			List<SubjectTextEntity> oldList, List<SubjectTextEntity> newList) {
		/**
		 * ランキング算出法
		 * 	[1]OLDのスレ番号をベースにNEWのスレ番号を探しレス数の差分を算出rankingListに挿入
		 * 	[2]OLDにあってNEWにあるレコードに関してはNEWエンティティにdeleteFlagに-1という仮想的なフラグを立てる
		 * 	[3]OLDにあってNEWにないスレ番号はDAT落ち、ランキングには載せない
		 * 	[4]NEWエンティティでdeleteFlagに-1が立っていないものは新スレッドとし、勢い値=レス数としrankingListに挿入
		 * 	[5]rankingListをレス数をベースにソート
		 */

		List<SubjectTextEntity> rankingList = new ArrayList<SubjectTextEntity>();

		for (SubjectTextEntity oldEntity : oldList) {

			for (SubjectTextEntity newEntity : newList) {

				// [1]
				if (oldEntity.getThreadNumber().equals(
						newEntity.getThreadNumber())) {
					// NEWをベースにランキングのエンティティを生成
					SubjectTextEntity updateEntity = (SubjectTextEntity) newEntity
							.clone();

					// NEWのレス数 - OLDのレス数　を勢い値として算出
					updateEntity.setResInterval(newEntity.getThreadResCount()
							- oldEntity.getThreadResCount());

					rankingList.add(updateEntity);

					// [2]
					newEntity.setDeleteFlag(-1);
				}

			}

		}

		// [4]
		for (SubjectTextEntity newEntity : newList) {

			if (newEntity.getDeleteFlag() != -1) {
				// 新スレと判断
				SubjectTextEntity insertEntity = (SubjectTextEntity) newEntity
						.clone();

				// 勢い値=現在のスレ数として設定
				insertEntity.setResInterval(insertEntity.getThreadResCount());

				rankingList.add(insertEntity);
			}

		}

		// [5]
		SubjectTextEntity[] array = rankingList
				.toArray(new SubjectTextEntity[rankingList.size()]);
		Arrays.sort(array);

		//ソート済のリストを返却
		return Arrays.asList(array);
	}

}
