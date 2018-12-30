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

