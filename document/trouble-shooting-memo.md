## 概要
Android開発時に、様々なエラーなどで開発が止まることがある。
その時に実施したことなどをまとめておく。

## 対応リスト
### アプリを起動しようとすると「Please select Android SDK」と出てしまう時の対応
* [こちらのリンク](https://stackoverflow.com/questions/34353220/android-studio-please-select-android-sdk/50000408#50000408)にある「FIX FOR 3.1.2 OR NEWER VERSIONS」が割とうまくいった。
* 上記リンクに記載の他の方法も試して最終的にこれでいけたが、他の方法で直るならそちらでもOKそう。

### 「Application Installation Failed」問題
* minバージョンの変更、および実機に入れようとしたらおきたことがあった
* 参考にしたのは[Android Studio で Installation Failed と出る問題の解決方法](https://www.ecoop.net/memo/archives/android-studio-application-installation-failed.html)に記載の方法
* 「Instant Run」をオフにしたらいけたが、そもそもの原因解決には至っていないので、オフにしたくない時に起きたら再度考える
