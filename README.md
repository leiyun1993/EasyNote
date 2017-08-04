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
---------------------------------------------------------------------------------------------------
```
apply plugin: 'kotlin-kapt'
apply plugin: 'io.objectbox'
```
---------------------------------------------------------------------------------------------------
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
```
@Entity
data class Note(
    @Id var id: Long = 0,
    var type: String? = null,
    var content: String? = null,
    var date: Date? = null
)
```