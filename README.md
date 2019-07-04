# 一行代码实现onActivityResult

## 概述
[![](https://jitpack.io/v/Brook007/ActivityResultUtil.svg)](https://github.com/Brook007/ActivityResultUtil)
[![](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://github.com/Brook007/ActivityResultUtil)
[![](https://img.shields.io/badge/API_Live-14+-brightgreen.svg)](https://github.com/Brook007/ActivityResultUtil)
[![](https://img.shields.io/badge/License-Apache_2-brightgreen.svg)](https://github.com/Brook007/ActivityResultUtil/blob/master/LICENSE)
[![](https://img.shields.io/badge/Author-Brook007-orange.svg)](https://github.com/Brook007)


## 引入依赖
### Gradle方式--适合Android Studio用户
在根项目的build.gradle中添加下面的代码
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

然后在需要使用的模块的build.gradle中添加以下代码
```groovy
dependencies {
    implementation 'com.github.Brook007:ActivityResultUtil:1.0.2'
}
```

## 示例代码

仅需下面代码就可以实现activity结果的回调了

```java
Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
// 如果限制图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");

ActivityResultUtil.with(this).requestCode(10000).startActivityForResult(intentToPickPic, new ActivityResultUtil.Callback() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {

            }
        });
```

## 开源协议  LICENSE

    Copyright (c) 2016-present, Brook007 Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
