[![Author](https://img.shields.io/badge/Author-hexLi-666699)](https://2lbj.github.io/)
[![last-commit](https://img.shields.io/github/last-commit/2lbj/lergo-spring-boot-starter)](https://github.com/2lbj/lergo-spring-boot-starter)
[![license](https://img.shields.io/badge/license-Apache%202.0-orange)](./LICENSE)

# LerGo-Example (模板工程)

[李二狗](https://github.com/2lbj/lergo-spring-boot-starter) 框架最佳实践

# 开发规范

推荐依赖中间件 Redis PostgreSQL ~~MySQL/MariaDB~~

### 数据库设计约定

| 逻辑     |      建议命名       | JAVA-PO类型 `内容`                        | 数据库类型 `内容`                     |
|--------|:---------------:|---------------------------------------|--------------------------------|
| 主键     |     **id**      | <kbd>Long</kbd> `1724657058516766721` | <kbd>int8(64)</kbd> `雪花ID`     |
| 创建时间   | **create_time** | <kbd>Timestamp</kbd> `date`           | <kbd>timestamp(6)</kbd> `时间戳`  |
| 更新时间   | **update_time** | <kbd>Timestamp</kbd> `date`           | <kbd>timestamp(6)</kbd> `时间戳`  |
| 逻辑删除   |   **deleted**   | <kbd>Boolean</kbd> `true/false`       | <kbd>bool</kbd> `T-F/1-0`      |
| 枚举类型   |  **xxx_enum**   | <kbd>enum</kbd> `X,Y`                 | <kbd>int2(16)</kbd> `0,1,2...` |
| JSON数据 |  **xxx_json**   | <kbd>Object</kbd> `{...}`             | <kbd>jsonb</kbd> `json`        |

* 数据库映射实体类不得使用基础类型
  > 例如不得使用: int/long/boolean 参考:阿里巴巴开发手册
* 数据主键使用 <kbd>bigint</kbd> <kbd>int8(64)</kbd> 类型
* 数据库映射如包含时间类型优先使用 **timestamp**
  > 低版本 MySQL/MariaDB 可考虑 bigint 类型替换
* 低版本PG <kbd>jsonb</kbd> 可考虑用 <kbd>json</kbd> 类型代替
* 枚举名称 下划线分割; (英文)全大写； (中文)短词不含生僻字；

### 代码约定

*

      参考:阿里巴巴开发手册
