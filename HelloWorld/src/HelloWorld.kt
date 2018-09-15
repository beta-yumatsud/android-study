/* 初Kotlinかも？ */
package sample

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

class MyIterator {
    operator fun hasNext(): Boolean = Math.random() < 0.5
    operator fun next(): String = "hello"
}

class MyIterable {
    operator fun iterator() = MyIterator()
}

tailrec fun sum(numbers: List<Long>, accumulator: Long = 0): Long =
        if (numbers.isEmpty()) accumulator
else sum(numbers.drop(1), accumulator + numbers.first())

fun square(i: Int): Int = i * i

fun first(str: String, predicate: (Char) -> Boolean): Int {
    tailrec fun go(str: String, index: Int): Int =
            when {
                str.isEmpty() -> -1
                predicate(str.first()) -> index
                else -> go(str.drop(1), index+1)
            }
    return go(str, 0)
}

fun isK(c: Char): Boolean = c == 'k'
fun isUpperCase(c: Char): Boolean = c.isUpperCase()
fun firstWhitespace(str: String): Int =
        first(str) { it.isWhitespace() }

fun getCounter(): () -> Int {
    var count = 0
    return {
        count++
    }
}

inline fun log(debug: Boolean = true, message: () -> String) {
    if (debug) {
        println(message())
    }
}

inline fun forEach(str: String, f: (Char) -> Unit) {
    for (c in str) {
        f(c)
    }
}

fun containsDigit(str: String): Boolean {
    forEach(str) {
        if (it.isDigit()) {
            return true
        }
    }
    return false
}

interface Bucket {
    fun fill()
    fun drainAway()
    fun pourTo(that: Bucket)

    val capacity: Int
    var quantity: Int
}

fun createBucket(_capacity: Int): Bucket = object : Bucket {
    override val capacity = _capacity
    override var quantity: Int = 0

    override fun fill() {
        quantity = capacity
    }

    override fun drainAway() {
        quantity = 0
    }

    override fun pourTo(that: Bucket) {
        val thatVacuity = that.capacity - that.quantity
        if (capacity <= thatVacuity) {
            that.quantity += quantity
            drainAway()
        } else {
            that.fill()
            quantity -= thatVacuity
        }
    }
}
class BucketImpl(_capacity: Int): Bucket {
    override fun fill() {
        quantity = capacity
    }

    override fun drainAway() {
        quantity = 0
    }

    override fun pourTo(that: Bucket) {
        val thatVacuity = that.capacity - that.quantity
        if (capacity <= thatVacuity) {
            that.quantity += quantity
            drainAway()
        } else {
            that.fill()
            quantity -= thatVacuity
        }
    }

    override val capacity: Int = _capacity
    override var quantity: Int = 0

}
class Person {
    var name: String = ""
        set(value) {
            println("${value}がセットされました")
            field = value
        }
    var age: Int = 0
    val nameLength: Int
        get():Int {
            return this.name.length
        }
}

class Rational(val numerator: Int, val denominator: Int = 1) {
    constructor(numerator: Int): this(numerator, 1)
    init {
        // 要求に反した場合に、例外をスローする標準ライブラリの関数
        require(denominator != 0)
    }
}

fun String.countWords(): Int =
        this.split("""\s+""".toRegex()).size

open class Pers(open val name: String) {
    open fun introduceMySelf() {
        println("I am $name")
    }
}
class Student(override val name: String, val id: Long): Pers(name) {
    override fun introduceMySelf() {
        println("I am $name(id=$id)")
    }
}

abstract class Greeter(val target: String) {
    abstract fun sayHello()
}

class EnglishGreeter(target: String): Greeter(target) {
    override fun sayHello() {
        println("Hello, $target!!")
    }
}

interface Hello {
    fun sayHello(target: String)
    fun sayHello()
}

open class JapaneseHello: Hello {
    override fun sayHello(target: String) {
        println("こんにちわ、$target さん！")
    }

    override fun sayHello() {
        println("こんにちわ、匿名さん！")
    }

}

class JapaneseHelloRecording : Hello {
    private val hello: Hello = JapaneseHello()

    private val _targets: MutableSet<String> = mutableSetOf()

    val targets: Set<String>
        get() = _targets

    override fun sayHello(target: String) {
        _targets += target
        hello.sayHello(target)
    }

    override fun sayHello() {
        hello.sayHello()
    }

}

class HelloeWithRecording(private val hello: Hello): Hello by hello {
    private val _targets: MutableSet<String> = mutableSetOf()

    val targets: Set<String>
        get() = _targets

    override fun sayHello(target: String) {
        _targets += target
        hello.sayHello(target)
    }
}
interface Hoo
interface Foo
interface Piyo: Hoo, Foo
class Baz<T> where T: Hoo, T:Foo

class Container<T>(var value: T) {
    fun copyTo(to: Container<in T>) {
        to.value = value
    }
}

fun show(container: Container<out Any>) {
    println(container.toString())
    println(container.hashCode())
    println(container.value)
}

fun squre(i: Int): Int = i * i

// operatorというものが演算子などをオーバーロードできるみたい
class Service {
    operator fun invoke(): Char = 'A'
    operator fun invoke(c: Char) = c
}

// シングルトンを作成: classの代わりのobjectを使えば良いみたい
object OreoGreetor: Hello {
    override fun sayHello(target: String) {
    }
    override fun sayHello() {
    }
}

// 列挙型 (ある意味、sealedクラスを継承するクラスが、
// 全てシングルトンオブジェクトと同じみたい)
// オブジェクトなので、コンストラクタを引数にも取れるし,
// メソッドを持たせることもできる
enum class DrinkSizeType(val milliliter: Int) {
    SMALL(250) {
        override fun message(): String = "It's a small..."
    },
    MEDIUM(350) {
        override fun message(): String = "Normal."
    },
    LARGE(500) {
        override fun message(): String = "What a big!!"
    };
    abstract fun message(): String
}

// 委譲プロパティ
class MyClass {
    var _str: String? = null
    var str: String? by object {
        operator fun getValue(thisRef: MyClass, property: KProperty<*>): String? {
            println("${property.name}がgetされました")
            return _str
        }

        operator fun setValue(thisRef: MyClass, property: KProperty<*>, value: String?) {
            println("${property.name}に${value}がsetされました")
            _str = value
        }
    }
}

fun main(args: Array<String>) {
    println("Hello, World!!")
    val x = 12

    when (x) {
        1 -> "one"
        in 2..20 -> "1 <= x <= 20"
        else -> x.toString()
    }
    /*
    val blank = when(x) {
        is String -> x.isBlank()
        else -> true
    }
    */

    for (item in MyIterable()) {
        println(item)
    }

    val result: Long = sum((1L..100).toList())
    println(result)

    println(::square)
    val functionObject: (Int) -> Int = ::square
    println(functionObject(5))

    val searchK: Int = first("fdajkd", ::isK)
    println(searchK)
    val upperRet: Int = first("Ofdas", ::isUpperCase)
    println(upperRet)

    val sqr: (Int) -> Int = { i: Int ->
        i * i
    }
    println(sqr(10))
    val sqr2: (Int) -> Int = {
        it * it
    }

    val counter = getCounter()
    println(counter())
    println(counter())

    log { "hellow, Mr.Emarson" }

    val bucket1 = createBucket(7)
    val bucket2 = createBucket(4)
    bucket1.fill()
    bucket1.pourTo(bucket2)

    println(bucket1.quantity)
    println(bucket2.quantity)

    val hanako = Person()
    hanako.name = "hanako"
    println(hanako.nameLength)

    val student: Student = Student("kumiko", 123)
    println(student.id)
    student.introduceMySelf()
    EnglishGreeter("Kotlin").sayHello()

    val hello = JapaneseHello()
    hello.sayHello()
    hello.sayHello("たろう")
    val helloRecording = JapaneseHelloRecording()
    helloRecording.sayHello()
    helloRecording.sayHello("くんろう")
    println(helloRecording.targets.size)

    val helloWithRecording = HelloeWithRecording(hello)
    helloWithRecording.sayHello()
    helloWithRecording.sayHello("hogehoge")
    helloWithRecording.sayHello("hugahuga")
    println(helloWithRecording.targets.size)

    Baz<Piyo>()

    val list: List<Any> = listOf(1, 'a', false)
    for (e in list) {
        val result: Any? = when(e) {
            is Int -> e + 5
            is Char -> e.toUpperCase()
            is Boolean -> e.not()
            else -> null
        }
        println(result)
    }

    val a: Int? = 5
    val aInc: Int? = a?.inc() // 安全呼び出し

    // NotNullな引数をとる関数にNullableを引数として渡す時は、
    // 関数letを使うと簡単に描ける
    val aSquare = a?.let { square(it) }

    // エルビス演算子
    // nullでなければそれを使用し、nullである場合は指定のデフォルト値を使う的な場合
    val huho: String? = "hogehgoehgoe"
    val huhoUpper = (huho ?: "default").toUpperCase()
    println(huhoUpper)
    // デフォルト値指定のところでthrowさせるというテクニックは割と使われるみたい
    val ds: Any = "string"
    println(ds as String)
    println(ds as? Int) // 安全キャスト

    val service = Service()
    println(service())
    println(service('B'))

    // 中置呼び出しとは、..やdownTo、step toなどがあり、これらはinfix指定されているメソッドとのこと。
    // 分解宣言
    val (name, age) = Pair("Taro", 27)
    println(name)
    println(age)
    // →これらはoperator付きメソッドcomponentNの存在によるもの
    data class UUser(val id: Long, val name: String)
    // これはcopyメソットや、分解宣言も使えるみたい
    // ただし、プライマリコンストラクタのプロパティのみ

    // 外側のクラスのオブジェクトの参照を掴むためにはinner classを使うと行ける

    // クラス内にシングルトンオブジェクトを定義したい場合は、companionを修飾すると使える
    // companionは、1つのクラスに1つまで、名前を省略可能でその場合はCompanionという名前がつく

    // sealedクラスは、そのサブクラスになれる範囲を限定するもの
    // これは列挙型にもつながるみたいよ
    println(DrinkSizeType.LARGE.message())
    // 列挙型のvaluesメソッドは全オブジェクトを配列で返す
    // valueOfは引数指定のものを返す
    println(DrinkSizeType.valueOf("SMALL"))
    println(DrinkSizeType.MEDIUM.name)
    // ordinalはそのオブジェクトの定義された順序をゼロベースで返す

    // try-catchも式であることを忘れずに。ただしfinallyは値を返さない

    // レシーバの型.(引数の型リスト)->返り値の型
    val method: Int.()->Int = Int::inc
    println(method.toString())

    // プロパティの後にbyをつけてあげると、その後に続くオブジェクトにアクセスを委譲するみたい
    val myClass = MyClass()
    println(myClass.str)
    myClass.str = "hogehogehogehgoe"
    println(myClass.str)
}