# 开发规范

推荐依赖中间件 Redis PostgreSQL ~~MySQL/MariaDB~~

### 代码约定

* 引用优先原则 (即:相同的配置/变量定义不应出现需要在多处修改的场景)
* 代码缩进使用 <kbd>4</kbd> 个空格
* 外部调用接口应注释完整的文档

```java
/**
 * 测试接口
 * @param id
 * @param header
 * @param queryMap
 * @return boBean
 * @see <a href="https://api.xxx.com/xxx">旧接口文档</a>
 * @deprecated 该接口已经被废弃，使用<a href="https://api.xxx.com/xxx?version=1.1">新接口文档</a>
 */
```

* 配置文件中变量引用应考虑默认值

### 数据库设计约定

| 用途     |      建议命名       | JAVA-PO类型 `内容`                        | 数据库类型 `内容`                     |
|--------|:---------------:|---------------------------------------|--------------------------------|
| 主键     |     **id**      | <kbd>Long</kbd> `1724657058516766721` | <kbd>int8(64)</kbd> `雪花ID`     |
| 创建时间   | **create_time** | <kbd>Timestamp</kbd> `date`           | <kbd>timestamp(6)</kbd> `时间戳`  |
| 更新时间   | **update_time** | <kbd>Timestamp</kbd> `date`           | <kbd>timestamp(6)</kbd> `时间戳`  |
| 逻辑删除   |   **deleted**   | <kbd>Boolean</kbd> `true/false`       | <kbd>bool</kbd> `T-F/1-0`      |
| 枚举类型   |  **xxx_enum**   | <kbd>enum</kbd> `X,Y`                 | <kbd>int2(16)</kbd> `0,1,2...` |
| JSON数据 |  **xxx_json**   | <kbd>Object</kbd> `{...}`             | <kbd>jsonb</kbd> `json`        |

* 数据库映射实体类不得使用基础类型

> 例如不得使用: int/long/boolean 参考:阿里巴巴开发手册

* 数据主键使用 <kbd>bigint</kbd> <kbd>int8(64)</kbd> 类型 并可直接在内存中生成
* 数据库映射如包含时间类型优先使用 **timestamp**

> 低版本 MySQL/MariaDB 可考虑 bigint 类型替换

* 低版本PG <kbd>jsonb</kbd> 可考虑用 <kbd>json</kbd> 类型代替
* 枚举名称 下划线分割; (英文)全大写； (中文)短词不含生僻字；