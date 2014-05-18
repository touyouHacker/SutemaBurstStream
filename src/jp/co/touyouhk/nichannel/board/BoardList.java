package jp.co.touyouhk.nichannel.board;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @author Wizard1
 * 2chの板一覧を管理するクラスです
 */
public class BoardList {

	private HashMap<String, String> boardMap;

	private List<String> categoryList = new ArrayList<String>();
	private HashMap<String, List<String>> categoryMap;

	public BoardList(String bbsUrl) throws IOException {
		URL url = new URL(bbsUrl);

		String bbsMenuHtmlString = IOUtils.toString(url, "SJIS");

		Document doc = Jsoup.parseBodyFragment(bbsMenuHtmlString);

		boardMap = createBoradMap(doc);
		categoryList = createCategoryList(doc);
		categoryMap = createCategoryMap(bbsMenuHtmlString);
	}

	/*
	 * 2chのbbsmenuから板の和名とURLのハッシュを作成します
	 * @param url 例:http://www2.2ch.net/bbsmenu.html
	 */
	private HashMap<String, String> createBoradMap(Document doc) throws IOException {

		HashMap<String, String> boradMap = new HashMap<String, String>();

		Elements links = doc.getElementsByTag("a");
		for (Element link : links) {
			String linkHref = link.attr("href");
			String linkText = link.text();

			if (StringUtil.isBlank(linkHref) || StringUtil.isBlank(linkText)) {
				continue;
			}

			boradMap.put(linkText, linkHref);

		}

		return boradMap;

	}

	private List<String> createCategoryList(Document doc) {
		List<String> categoryList = new ArrayList<String>();

		Elements categories = doc.getElementsByTag("b");
		for (Element category : categories) {
			categoryList.add(category.text());
		}

		return categoryList;
	}

	private HashMap<String, List<String>> createCategoryMap(String bbsMenuHtmlString) throws IOException {
		HashMap<String, List<String>> categoryMap = new HashMap<String, List<String>>();


		String [] categoryGroupStrings = bbsMenuHtmlString.split("(?m)\n$");
		List<String> categoryGroupList = Arrays.asList(categoryGroupStrings);


		for (String categoryGroup: categoryGroupList) {
			Document doc = Jsoup.parseBodyFragment(categoryGroup);
			if (doc.getElementsByTag("b").size() == 0) {
				continue;
			}

			String categoryName = doc.getElementsByTag("b").get(0).text();
			List<String> itaNames = new ArrayList<>();
			Elements links = doc.getElementsByTag("a");
			for (Element link : links) {
				if (StringUtil.isBlank(link.text())) {
					continue;
				}
				itaNames.add(link.text());
			}

			categoryMap.put(categoryName, itaNames);
		}

		return categoryMap;
	}

	/*
	 * 板名の和名を元にURLを返却します
	 *
	 * @param boardName 板名の和名
	 * @return 板のURL
	 */
	public String getUrl(String boardName) {
		return boardMap.get(boardName);
	}

	/*
	 * 板カテゴリ一覧を返します
	 */
	public List<String> getCategoryList() {
		return categoryList;
	}

	/*
	 * 板カテゴリを指定し、その子ノードである板一覧をリストで返却します
	 */
	public List<String> getCategoryMap(String category) {
		return categoryMap.get(category);
	}
}