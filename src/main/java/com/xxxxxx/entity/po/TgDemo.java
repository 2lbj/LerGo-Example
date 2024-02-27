package com.xxxxxx.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 自动构建基准字段 [TgDemo]
 *
 * @author erGo.Li
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@Schema(title = "自动构建基准字段", description = "基础表模板")
//@Table("tg_demo")
@TableName("tg_demo")
public class TgDemo {

    @JsonSerialize(using = ToStringSerializer.class)
    //@Id(keyType = KeyType.Auto)
    @TableId
    @Schema(title = "ID")
    private Long id;

    @Schema(title = "名称")
    private String name;

//    @Schema(title = "性别")
//    @EnumValue
//    private TgDemoSexEnum sex;
//
//    @TableField(fill = FieldFill.INSERT)
//    @Schema(title = "创建时间")
//    private Timestamp createTime;
//
//    @TableField(fill = FieldFill.INSERT)
//    @TableLogic(value = "FALSE", delval = "TRUE")
//    @Schema(title = "是否已删除")
//    private Boolean deleted;
//
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    @Schema(title = "更新时间")
//    private Timestamp updateTime;
//
//    @JsonRawValue
//    private Object jsonProfile;

}
