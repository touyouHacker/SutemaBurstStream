package jp.co.touyouhk.nichannel.board;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import jp.co.touyouhk.util.UniversalUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @author Wizard1
 * 2chの板一覧を管理するクラスです
 */
public class BoardList {

	private HashMap<String, String> boardMap;

	public BoardList(String url) throws IOException {
		boardMap = getList(url);
	}

	/*
	 * 2chのbbsmenuから板の和名とURLのハッシュを作成します
	 * @param url 例:http://www2.2ch.net/bbsmenu.html
	 */
	private HashMap<String, String> getList(String bbsUrl) throws IOException {

		HashMap<String, String> boradMap = new HashMap<String, String>();


		URL url = new URL(bbsUrl);
		Document doc = Jsoup.parse((InputStream)url.getContent(), "SJIS", url.getHost() + url.getPath());
		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			String linkHref = link.attr("href");
			String linkText = link.text();

			if (UniversalUtil.isBlank(linkHref) || UniversalUtil.isBlank(linkText)) {
				continue;
			}

			// System.out.println(linkText + " " + linkHref);

			boradMap.put(linkText, linkHref);

		}

		return boradMap;

	}

	/*
	 * 板名を和名を元にURLを返却します
	 *
	 * @param boardName 板名の和名
	 * @return 板のURL
	 */
	public String getUrl(String boardName) {
		return boardMap.get(boardName);
	}

}