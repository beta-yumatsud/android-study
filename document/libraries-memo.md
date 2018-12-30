## 概要
* Androidでよく使うライブラリ関連で勉強した時のメモ
* 古い情報や深く理解できていない状態でのメモもありますので、ご了承ください

## Dagger
* 参考文献
  * [Android Dagger2 with Kotlin](https://qiita.com/SYABU555/items/43b9d9edc100c92970d0)
  * [Kotlinで DI （Dependency Injection）〜Dagger編](https://qiita.com/sudachi808/items/a05237e1294639ea41dd)
* Module
  1. 自動生成するインスタンスを `@Module` で宣言
  2. 1で宣言した `Moduleクラス` を通じて生成するインスタンスの宣言を `@Provides` で宣言。命名規則は `provide+生成するクラス名()` とする。
  3. `@Singleton` をつけることでシングルトンにすることも可能
* Component
  1. 実際にインスタンスを挿入する対象クラスを定義するインターフェース宣言 (これでどのクラスがどのモジュールを使っているかがわかる)
  2. injectしたクラスに、宣言されているフィールドにインスタンが挿入される (継承先などは含まない)
* Inject
  * Builder
    * 上記で作成したComponentを作成
    * `Dagger+Componentクラス名` でクラスが作成されているのでBuilderを使いつつ、利用するModuleをaddしていく
  * Field
    * var変数の前に `＠Inject` をつける
    * NonNullにする場合は `lateinit` をつけることが多い
  * Class
    * プライマリconstructorの前に＠Injectをつければ引数に対してDIできる
    * `private val` で宣言できるのがメリット
* SubComponent
  1. Componentの親子関係を定義できる
  2. 親に定義したScopeを定義できないので、Scopeを分けるときに利用する
  3. 例としてはApplicationのScopeとActivityのScopeを分けて、ApplicationのComponentのサブとしてActivityのComponentを作る的な
* その他
  * `@Named` で@Injectする際に別名をつけることができる
  * `@Scope` で@Provideするインスタンスの生存期間を指定できる (@Singletonもそのうちの1つ)
  * dependenciesの設定などは2つ目の参考リンクを見ると良いかも

## databinding
* 参考
  * [公式](https://developer.android.com/topic/libraries/data-binding/)
  * [Android + Kotlin + DataBinding](https://qiita.com/funeasy-soft/items/ab5fc3f8f770a91adc25)
  * [Andorid DataBinding with Kotlin](https://qiita.com/SYABU555/items/3ca6f43135e79c0fa8ca)
  * [KotlinでAndroidの双方向DataBindingを利用する](https://re-engines.com/2017/07/19/kotlin%E3%81%A7android%E3%81%AE%E5%8F%8C%E6%96%B9%E5%90%91databinding%E3%82%92%E5%88%A9%E7%94%A8%E3%81%99%E3%82%8B/)
* 基本構造
  * layoutファイル
    * layoutタグで全体を囲む
    * その配下にdataタグを用意し、variableタグを指定
    * variableタグのtypeに利用するクラスを指定する
    * variableの指定したnameで各Viewに必要なものをセットする
  * 関連付け
    * DataBindingUtilクラスのsetContentViewを利用してbindingを生成(inflateでの生成も可能)
    * そのbindingの1で指定したnameに対して、typeで指定したオブジェクトをセットしてあげる(ex. `binding.user = User()` )
    * もしくはviewに指定しているものを指定することも可能 (ex `binding.name = user.name` 的な)
  * build.gradle
    * 基本は公式を参考に
    * 公式のget startedで `android.databinding.enableV2=true` の設定があった方が良いかも説
* イベントの紐付けも可能
  * layoutのonClickイベントなどに `() -> hogehoge.buttonClicked()` とかを紐づけることも可能
  * もしくは `hogehoge::buttonClicked` などのメソッド参照も利用可能
* データ監視
  * Observable
    * variableのタイプで渡すクラスのフィールドを `ObservableField<String>` のように指定する
  * Bindable(双方向)
    * variableのタイプで渡すクラスで `BaseObservable` を継承
    * `@Bindable` でgetやsetなどを指定する
    * レイアウトには `@={}` のようにイコールをつける
* レイアウトのその他
  * Frameworkにある定数などはimportして利用可能
  * 三項演算子なども利用可能
  * null合体演算子と言われる、 `@{data.name ?? @string/no_name}` のようにnullだったらデフォルト値とかも指定できる
  * `BindingAdapterアノテーション` はレイアウトに対してsetterを宣言できる

## Reactive系
* 参考
  * [Android/KotlinでRxAndroidを使うときに参考にしたサイト](https://wannabe-jellyfish.hatenablog.com/entry/2017/01/29/120537)
  * [RxAndroidをカジュアルに使ってみるとか](http://kirimin.hatenablog.com/entry/2015/02/11/221228)
  * [RxJava+RetrofitでAPI通信周りを実装するうえで最低限の知識を30分で詰め込む](https://qiita.com/FumihikoSHIROYAMA/items/201536d9b45ef21b6bc7)
  * [ReactiveX/RxKotlin](https://github.com/ReactiveX/RxKotlin)
* まず簡単に言うと...
  * 短い関数をチェーンしていき、ストリーム的にイベント結果のデータを加工する処理を定義できる
  * かつ、遅延実行・非同期コールバックを行う事が出来るのが大きなメリット(だと思ってる)
* 要素
  * Observable
    * 処理を実行して結果を伝えるオブジェクト(データソースを提供するもの)
  * Observer
    * 結果を受け取った時の処理を決める
  * `Observable` が `Observer` を `subscribe` する形で、 `Observable` の処理結果を `Observer` でどうするか決めてあげる的なね
* 流れ (APIを実行して結果を返す的なものを例にあげる)
  * APIの実行結果のレスポンス型をObservableとして定義
  * APIを実行し、Observableを受け取り、必要な処理をチェーンで結ぶ
  * 上記の最後で、subscribeをおき、その中では受け取った結果をどうするかの処理を書いておく
＊ チェーン要素
  * subscribeOn
    * 処理をどのスレッドでやるか定義
    * 非同期処理だと、 `Schedulers.io()` などで別スレで実行できる
  * observeOn
    * コールバックをどのスレッドでやるか
    * デフォルトはmainThread？
  * subscribe
    * 実行した結果、何をするか定義するところ
    * Observableの型によって、 `onSuccess` `onError` `onCompleted` `onError` `onNext` などが呼ばれる
  * `map`、`filter`、`merge`などを使うと結果の処理をいい感じに加工できるので、必要に応じて使う
* [Observableの種類](http://reactivex.io/documentation/operators.html)
  * Single
    * `onSuccess` か `onError` のどちらかが1回だけ呼ばれる
    * HTTP GETのように成功してレスポンスを1度だけ受け取るか、または失敗するようなものに向いている
  * Completable
    * `onComplete` か `onError` のどちらかが1回だけ呼ばれる
    * レスポンスが空のHTTP POSTなどに向いている
  * Zip
    * 複数のObservableの完了を待ち合わせ、それを処理する関数に渡して変換するためのオペレータ
    * 複数のAPIを叩いて待ち合わせる時に利用
* 購読解除
  * Disposable
    * subscribeメソッドは返り値として `Disposable` を返す
    * `Disposable#dispose()` を呼ぶと以後のイベントは通知されて来なくなる
  * CompositeDisposable
    * 複数のDisposableをまとめて購読を解除するための `CompositeDisposable` も提供されている

