# TitlebarColorGradient
透明状态栏+渐变标题栏（透明状态栏可以去掉没有影响）。筛选控件的使用。

引用自：https://github.com/sfsheng0322/StickyHeaderListView

![图片](https://github.com/yueyue10/TitlebarColorGradient/blob/master/Screenshot_20191127-110650.jpg?raw=true)

根据上面的工程修改而来。down下来以后还需要修改一些配置。

1.在app目录下的build.gradle里面修改为：
```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    // ButterKnife
    compile 'com.jakewharton:butterknife:8.4.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
    // Glide
    compile 'com.github.bumptech.glide:glide:3.7.0'
    compile 'com.android.support:appcompat-v7:25.0.1'
    compile 'com.android.support:design:25.0.1'
}
```
2.在工程的根目录下的build.gradle里面修改为： 
```
buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.3'
        classpath 'com.jakewharton:butterknife-gradle-plugin:8.4.0'
    }
}
```
