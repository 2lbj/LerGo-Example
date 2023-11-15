[![Author](https://img.shields.io/badge/Author-hexLi-666699)](https://2lbj.github.io/) [![last-commit](https://img.shields.io/github/last-commit/2lbj/lergo-spring-boot-starter)](https://github.com/2lbj/lergo-spring-boot-starter) [![license](https://img.shields.io/badge/license-Apache%202.0-orange)](./LICENSE)

# LerGo-Example (模板工程)

[李二狗](https://github.com/2lbj/lergo-spring-boot-starter) 框架最佳实践

# 开发规范

推荐依赖中间件 Redis PostgreSQL ~~MySQL/MariaDB~~

### 数据库设计约定

* 主键 **id** <kbd>int8(64)</kbd> 内容:`雪花ID`
* 创建时间 **create_time** <kbd>timestamp(6)</kbd> 内容:`时间戳`
* 更新时间 **update_time** <kbd>timestamp(6)</kbd> 内容:`时间戳`
* 逻辑删除 **deleted** <kbd>bool</kbd> 内容:`T-F/1-0`
* JSON数据 **xxx_json** <kbd>jsonb</kbd> 内容:`json`

> 旧版本PG 可考虑用 json 类型代替

### 代码约定

* 数据库映射实体类不得使用基础类型

> 例如不得使用: int/long/boolean *阿里巴巴开发手册*

* 数据库映射如包含时间类型请尽量使用 **java.sql.Timestamp**

> MySQL/MariaDB 可考虑Long类型替换
