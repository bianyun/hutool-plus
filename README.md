# Hutool Plus

[![Java 8](https://img.shields.io/badge/Java-1.8-F79224.svg?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAACXBIWXMAABYlAAAWJQFJUiTwAAAEl0lEQVR4AcVXW28bRRj1TwAJXvgZiFIJyhtIVEJUBJAoRTxSHqAPoAYoBYk2aQqmJW2SukKqEirFduOg2BHQNA6QtqSNL0VE1Lbi2EkT3x3H67u968vhm/XG9W3XGy5ipLFn5vvmnDM7M9/MaABo/s+8J/Lh4eHHLBbLIZvNNuDxeIyUrVI2sjZmYz57GZAqAXq9/oDX650SBCFH4IqJ+TBf1occe+IrOoyMjDzh8/mmFRnJWCsWgWq1w431ZRhKQmQFmEymF2k0yQ7ULg1FuwMll6uLBWAYhHVQTkRXAfPz8292RZNp5IbOQljfkLHWmyXMDr6OBlL7giJSmzGl1SI9OoYatVczGfG/zaVRlbBbOFsqNF+P8zyfafToUUi8fwzJL06h5HAgazCgHIkoCmDYjINgG7yNAmv0+/3mHpyiuUYLbvudo+AGz6Dw6yK40wOo5vNqukLiaPA2CgaDYZ8qBHIqB4MIPfuc6B7c/wzK0ajarqKfxCVyNwTQ3p1Ri1LjBex8cgKVdBrRV19H4r1jip++HVfieiiAotejtF34dkelejkcRmF2FoLPh+CTTwGVsuherrLlqJwYF3E+Ql4aUYXZbD6k3KVujWdKmF2JwrmZqjfks0AwgNzNW0jmeWSKAsx/RHBlaQvlSmdgauaQOOsCKI4PNRvlyjfccfTp7Bi/uwWDI4jJexHobQHMeDhYPXGkSQBLz19YRihF0VEhSZx1AXSgTCn4tpiCXAGnfliFlcQs+XZwm/L3v4fxZygDgUY9aQ9Cd+sBqjXlqZA46wLcbre1hUWhkimWceW3TYwtbuDiL+vQ3XyAq/RFXOEMYjRFroi6MMJOUaLZu4AKjVJr9aN/2oXzCz4MXvfiOJU3EuriwO7YWgSomYJvb2/i9I+r2NzOIVcq44Yrhok7W5i0BXHXn0SqIIjt3lgOIa64tylQswgv07z2m+5j8Ccvzi34MUGf3egIiYtxnISM0nScpy/zzc/rsNBOKfCV3cF2/W9ZhGq3IUOKpIuYc8UxfS+Ma84QiQhCTwuPLcS5+zHaohxtx3pM6MosNbZsQzWBiPd6wa+uKmGqtnUEIuqpWVtbUwzFya+0YLkSj6PkdIIdSFUKxSzVBIGO4izKgQBKKyuo8TyqhYJo6/YjcT0MxeSkMRqNiocRR2c+NzKKwuKieAQnP/tcPA+KS3eQvTYF7sJF5K/PIXG8HzsffUwC5AMRcT3NOFkWf3YrSsdxishTusvg3R6kxi4hevgIslMm7Hx6UszF5WWwnBwYJJv8hUr2OGYilC4k6fEJbH/wIWJvHEbeuoDwSy+jkkohsG8/Ym+9jaLDicgrfciaLYj0vUZwnannhYS6aOSuZIkTJ8UpYKNPnhlC6pJOZOC0XyM3YxbnnX0N7uyXyHx3tZOdWnpeyZgAltsvpZVYDPGj76KynegKrKaRMI/s4jf/t6yBZgOpPciu1CI4rfi/mxgGw2rGbi7LCmBO7FGh5mEiJ+4fPUyaVdK2OUB710SLqOfTjPkwX9anGUOurPgF2juxhyd7gNrt9o7HKWv7zx6n7UL+zfpf58rlvaO5TikAAAAASUVORK5CYII=)](https://www.oracle.com/java/technologies/downloads/#java8)&thinsp;
[![Gradle Status](https://img.shields.io/badge/Gradle-7.6-559CFD.svg?logo=Gradle)](https://docs.gradle.org/7.6/userguide/userguide.html)&thinsp;
[![CI Build](https://github.com/bianyun/hutool-plus/actions/workflows/gradle.yml/badge.svg)](https://github.com/bianyun/hutool-plus/actions/workflows/gradle.yml)&thinsp;
[![codecov](https://codecov.io/gh/bianyun/hutool-plus/branch/main/graph/badge.svg?token=CB4O9XUD21)](https://codecov.io/gh/bianyun/hutool-plus)&thinsp;
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/38b548f4494c4bd4940a1b0505e3e3d3)](https://www.codacy.com/gh/bianyun/hutool-plus/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bianyun/hutool-plus&amp;utm_campaign=Badge_Grade)
&thinsp;

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
在项目的 pom.xml 中加入需要的组件: 

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
