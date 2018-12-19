## SMSAuthParser [![](https://jitpack.io/v/WindSekirun/SMSAuthParser.svg)](https://jitpack.io/#WindSekirun/SMSAuthParser)

[![Kotlin](https://img.shields.io/badge/kotlin-1.2.0-blue.svg)](http://kotlinlang.org)	[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

Simple SMS Authorization Library in Android Application, written in [Kotlin](http://kotlinlang.org).

<img src="https://github.com/WindSekirun/SMSAuthParser/blob/master/sample.jpg" width="175" height="360">

It provides...
 * Parsing SMS Authorization Message and export as Number
   * ex) Confirm adding a Steam Authenticator with 12377 -> '12377'
 * Check Runtime Permission (API 23 and higher)
 * Available options to set regex, containsCondition
 
### Usages
*rootProject/build.gradle*
```	
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

*app/build.gradle*
```
dependencies {
    implementation 'com.github.WindSekirun:SMSAuthParser:1.0.0'
}
```

### Usages
No permission declare needed, permission will declare automatically by merge manifest.

#### Kotlin

```Kotlin
private val parser = SMSAuthParser()

val authOption = AuthOption.Builder()
                .setContext(this) // necessary
                .setContainsCondition("Steam") // optional
                .setParsingRegex("[0-9]+") // optional, default is [0-9]+
                .setCallback { this@MainActivity.alert("Parsing Result: %s".format(it)) } // necessary
                .build()
parser.initialize(authOption)
```

#### Java
```Java
private SMSAuthParser parser = new SMSAuthParser();

AuthOption authOption = new AuthOption.Builder()
                .setContext(this) // necessary
                .setParsingRegex("[0-9]+") // optional, default is [0-9]+
                .setContainsCondition("Steam") // optional
                .setCallback(s -> { // necessary
                    RichUtils.alert(Test.this, String.format("Parsing Result: %s", s));
                }).build();
        
parser.initialize(authOption);
```

#### Release
You should called ```parser.release()``` in onDestroy()

### License 
```
Copyright 2017 WindSekirun (DongGil, Seo)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
