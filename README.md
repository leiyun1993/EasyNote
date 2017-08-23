# EasyNote
## 使用Kotlin开发的仿华为手机EMUI备忘录，数据本地存储使用ObjectBox

### Kotlin-ObjectBox的使用

1、工程配置
```
buildscript {
    ext.kotlin_version = '1.1.3-2'
    ext.objectBoxVersion = "0.9.13"
    repositories {
        google()
        jcenter()
        maven { url "http://objectbox.net/beta-repo/" }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.0-alpha9'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "io.objectbox:objectbox-gradle-plugin:$objectBoxVersion"
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "http://objectbox.net/beta-repo/" }
    }
}
```
```
apply plugin: 'kotlin-kapt'
apply plugin: 'io.objectbox'
```
```
implementation "org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
compile('org.jetbrains.anko:anko:0.10.0') {
    exclude group: 'com.google.android', module: 'android'
}
compile "io.objectbox:objectbox-android:$objectBoxVersion"
compile "io.objectbox:objectbox-kotlin:$objectBoxVersion"
kapt "io.objectbox:objectbox-processor:$objectBoxVersion"
```
2、配置KotlinBean--->make project
```kotlin
@Entity
data class Note(
    @Id var id: Long = 0,
    var type: String? = null,
    var content: String? = null,
    var date: Date? = null
)
```
3、使用ObjectBox（基本使用）
```kotlin
class App : Application() {

    lateinit var boxStore: BoxStore
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }
}

private lateinit var noteBox: Box<Note>
private lateinit var noteQuery: Query<Note>

noteBox = (application as App).boxStore.boxFor(Note::class.java)
notesQuery = noteBox.query().order(Note_.content).build()
```
增、改
```kotlin
private fun addNote() {
    val content = contentTv.text.toString()
    val note = Note(type = type, content = content, date = date)
    if (noteID != -1L) {    //不传ID则ID自增，传入ID则为改
        note.id = noteID
    }
    noteBox.put(note)
}
```
删

```kotlin
noteBox.remove(note)
```
查

```kotlin
fun queryNotes() {      //查询全部
    val notes = noteQuery.find()
    mAdapter.setData(notes)
}

fun queryNotes(type: String) {      //查询指定type
    val builder: QueryBuilder<Note> = noteBox.query()
    val notes = builder.equal(Note_.type, type).build().find()
    mAdapter.setData(notes)
}
```

### EasyNote预览

![image](https://github.com/leiyun1993/EasyNote/raw/master/images/01.jpg)
![image](https://github.com/leiyun1993/EasyNote/raw/master/images/02.jpg)
![image](https://github.com/leiyun1993/EasyNote/raw/master/images/03.jpg)
![image](https://github.com/leiyun1993/EasyNote/raw/master/images/04.jpg)
![image](https://github.com/leiyun1993/EasyNote/raw/master/images/05.jpg)

### 相关链接

1、GreenDao老东家的[ObjectBox](https://github.com/greenrobot/ObjectBox)

2、Google 2017IO大会指定Android开发语言[Kotlin](https://github.com/JetBrains/kotlin)

3、Anko是一个使开发Android应用更简单更快捷的库[Anko](https://github.com/Kotlin/anko)
