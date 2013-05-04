package jp.co.touyouhk.nichannel.subjecttext;

public class SubjectTextEntity implements Cloneable, Comparable<SubjectTextEntity> {

	//10桁のスレ番号
	private String threadNumber;

	//スレタイ
	private String title;

	//レス数
	private int threadResCount;

	//DAT落ちの場合1、その他、処理で仮想的なフラグとして使用
	private int deleteFlag;

	//スレの勢いを格納
	private int resInterval;

	public String getThreadNumber() {
		return threadNumber;
	}

	public void setThreadNumber(String threadNumber) {
		this.threadNumber = threadNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getThreadResCount() {
		return threadResCount;
	}

	public void setThreadResCount(int threadResCount) {
		this.threadResCount = threadResCount;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getResInterval() {
		return resInterval;
	}

	public void setResInterval(int resInterval) {
		this.resInterval = resInterval;
	}

	public Object clone() {
		try {
			return (super.clone());
		} catch (CloneNotSupportedException e) {
			throw (new InternalError(e.getMessage()));
		}
	}

	/**
	 * resIntervalで降順にソートします
	 */
	public int compareTo(SubjectTextEntity subjectTextEntity) {

		return -(this.resInterval - subjectTextEntity.getResInterval());
	}
}
