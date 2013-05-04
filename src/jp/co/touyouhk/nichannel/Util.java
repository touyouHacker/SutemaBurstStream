package jp.co.touyouhk.nichannel;

public class Util {

	public static String boardUrl2EnglishName (String url){
		  //URLから板の英名抽出
        String [] urls = url.split("/");
        return urls[urls.length -1 ];
	}

}
