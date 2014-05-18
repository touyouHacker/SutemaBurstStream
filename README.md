#SutemaBurstStream
===========================

SutemaBurstStream (SBS) ステマ バースト ストリーム
#説明
2chの新スレッド検知アラートシステムです。


具体的には設定ファイルに指定した板から特定のキーワードのスレがたったらメールでお知らせしてくれるツールです。


荒らしやステルスマーケティング活動を推奨してるわけではないので2chへの書き込機能はつけていません。
あくまで検知のみ。

自分の関係する会社や団体のスレがたった時に風評被害を抑えるために即座に対応できるようにするためにも使用できます。

#使い方
http://d.hatena.ne.jp/thk/20130505
公式サイトはそのうち作ります

#動作に必要なもの
JAVAとJAVAが動くOS（Windows/MacOSX/Linux）とSMTPが使えるメールアドレス（Gmail推奨）

#ビルド時に必要な物
- JDK1.7+
- Ant or Eclipse


#使用ライブラリ
本ソフトウェアは以下のライブラリを使用しています。

##SQLite
https://bitbucket.org/xerial/sqlite-jdbc#Download

##jackson
http://jackson.codehaus.org/

##commons-email
http://commons.apache.org/proper/commons-email/download_email.cgi
