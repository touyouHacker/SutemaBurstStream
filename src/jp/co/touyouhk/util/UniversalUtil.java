package jp.co.touyouhk.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

// 汎用的なメソッド群

public final class UniversalUtil {
	public UniversalUtil() {
		// 空のコンストラクタ
	}

	public static String getTimeString() {
		Date d = new Date();
		return String.valueOf(d.getTime());
	}

	public static void createFile(String fileName) {

		File file1 = new File(fileName);
		try {
			if (file1.createNewFile()) {

			} else {
				// ファイルの作成に失敗してもなにもしない
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * フルパスのフォルダ名を指定し存在しなければ作成します 存在しているかまたは作成成功ならTrue,作成できなかったらfalse
	 *
	 * @param folderName
	 */
	public static boolean createDirectryExistCheak(String folderName) {

		if (isBlank(folderName)) {
			return false;
		}

		File file = new File(folderName);

		if (file.exists()) {
			return true;
		} else {
			return file.mkdirs();
		}

	}

	/**
	 * 文字がNULLまたは空文字かを判定します
	 * NULLもしくは空文字であればtrue、それ以外ならfalse
	 * @param var
	 * @return
	 */
	public static boolean isBlank(String var) {
		if (var == null || var.equals(""))
			return true;

		return false;
	}

	public static String nowDate(){
        String dateString = "";
        Calendar cal = Calendar.getInstance();
        dateString += String.valueOf( cal.get(Calendar.YEAR)) + "年" ;
        dateString += String.valueOf( cal.get(Calendar.MONTH) + 1) + "月";
        dateString += String.valueOf( cal.get(Calendar.DATE)) + "日";
        dateString += String.valueOf( cal.get(Calendar.HOUR_OF_DAY)) + "時";
        dateString += String.valueOf( cal.get(Calendar.MINUTE)) + "分";
        dateString += String.valueOf( cal.get(Calendar.SECOND)) + "秒";
        return dateString;
}


}