# Hutool Plus

## 简介

### 由来
Hutool Plus 是一个工具类库，之所以叫 Hutool Plus，是由于平时工作中主要用到的工具类库就是
[hutool](https://github.com/dromara/hutool)，hutool 是一个大而全的工具类库，但是也偶尔会有一些情形是 hutool 没有覆盖到的，
此时本人就会基于 hutool 和其它优秀的工具类库再封装一些工具类，久而久之就形成了这个 Hutool Plus 工具类库。

### 初心
此工具类库只是作为自己工作中总结的一些工具类的一个输出而已，与 hutool、apache commons、guava 等工具类库没有可比性。
即便如此，我也希望自己能将这个小小的工具类库不断完善下去。


## 包含组件

| 模块                  | 介绍                              |
|---------------------|---------------------------------|
| hutool-plus-core    | 核心常用组件（日期时间、集合、单位计量、字符串、正则表达式等） |
| hutool-plus-extra   | 附加组件（JSON工具、日志工具等）              |
| hutool-plus-media   | 媒体相关组件（媒体类型、文档相关、图片工具等）         |
| hutool-plus-spring5 | Spring 5.x 相关工具                 |


可以根据需求对每个模块单独引入。

## 文档 

由于时间仓促，没有准备专门的文档，不过可以参考工具方法的注释和源码中的单元测试代码。

## 安装

### Maven
在项目的 pom.xml 中加入需要的组件（:

```xml
<dependencies>
    <dependency>
        <groupId>plus.hutool</groupId>
        <artifactId>hutool-plus-core</artifactId>
        <version>0.1.0</version>
    </dependency>
    <dependency>
        <groupId>plus.hutool</groupId>
        <artifactId>hutool-plus-extra</artifactId>
        <version>0.1.0</version>
    </dependency>
    <dependency>
        <groupId>plus.hutool</groupId>
        <artifactId>hutool-plus-media</artifactId>
        <version>0.1.0</version>
    </dependency>
    <dependency>
        <groupId>plus.hutool</groupId>
        <artifactId>hutool-plus-spring5</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

### Gradle
```gradle
dependencies {
    implementation("plus.hutool:hutool-plus-core:0.1.0")
    implementation("plus.hutool:hutool-plus-extra:0.1.0")
    implementation("plus.hutool:hutool-plus-media:0.1.0")
    implementation("plus.hutool:hutool-plus-spring5:0.1.0")
}
```

> 注意：Hutool Plus 仅支持 JDK8
