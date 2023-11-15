package com.xxxxxx.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lergo.framework.annotation.LogTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FeishuNotifications {

    @Value("${name:}")
    String applactionName;

    @Value("${feishu.openapi:https://open.feishu.cn/open-apis/}")
    String openapi;
    @Value("${feishu.robot.webhook:bot/v2/hook/xxxxxxxxxxx}")
    String webhook;
    @Value("${feishu.robot.key:xxxxxxxxxxx}")
    String key;

    public enum COLOUR {

        BLUE("blue"),
        WATHET("wathet"),
        TURQUOISE("turquoise"),
        GREEN("green"),
        YELLOW("yellow"),
        ORANGE("orange"),
        RED("red"),
        CARMINE("carmine"),
        VIOLET("violet"),
        PURPLE("purple"),
        INDIGO("indigo"),
        GREY("grey"),
        DEFAULT("default");


        String code;

        COLOUR(String code) {
            this.code = code;
        }
    }

    public boolean sendMsg(String msgText) {
        return sendMsg(null, msgText, COLOUR.BLUE, null, false);    // 默认不高亮
    }

    public boolean sendMsg(String msgMarkdown, String msgText) {
        return sendMsg(msgMarkdown, msgText, COLOUR.BLUE, null, false);    // 默认不高亮
    }

    /*
        feishuNotifications.sendMsg("（原）邮件 [" + envFlag + "]" + subject, content, false,
            userAddress.stream().map(MailUser::getAddress).toArray(String[]::new));
     */
    @LogTracker
    public boolean sendMsg(String msgMarkdown, String msgText, COLOUR colour, String[] mailList, boolean isAtAll) {

        int timestamp = (int) DateUtil.currentSeconds();
        String signStr = "";
        try {
            signStr = GenSign(key, timestamp);
        } catch (Exception e) {
            log.error("飞书签名异常", e);
        }

        JSONObject card = JSONUtil.parseObj("{\n" +
                "  \"config\": {\n" +
                "    \"wide_screen_mode\": true\n" +
                "  },\n" +
                "  \"elements\": [\n" +
                "    {\n" +
                "      \"tag\": \"markdown\",\n" +
                "      \"content\": \" \"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tag\": \"hr\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tag\": \"div\",\n" +
                "      \"text\": {\n" +
                "        \"tag\": \"plain_text\",\n" +
                "        \"content\": \"  \"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"header\": {\n" +
                "    \"template\": \"" + colour.code + "\",\n" +
                "    \"title\": {\n" +
                "      \"content\": \"" + applactionName + "\",\n" +
                "      \"tag\": \"plain_text\"\n" +
                "    }\n" +
                "  }\n" +
                "}");
        card.putByPath("elements[0].content", "<font color='grey'> " + msgMarkdown + " </font>");
        card.putByPath("elements[2].text.content", msgText);
        Optional.ofNullable(mailList).map(Arrays::stream).map(v -> v.distinct().map(vv ->
                "<at email=" + vv + "></at>"
        ).collect(Collectors.joining(" "))).ifPresent(v -> card.putByPath("elements[0].content",
                card.getByPath("elements[0].content", String.class) + v));
        if (isAtAll) {
            card.putByPath("elements[0].content",
                    card.getByPath("elements[0].content", String.class) + "<at id=\"all\"></at>");
        }


        String body = JSONUtil.createObj()
                .set("timestamp", timestamp)
                .set("sign", signStr)
                .set("msg_type", "interactive")
                .set("card", card)
                //.set("msg_type", "text")
                //.set("content", JSONUtil.createObj().set("text", "<at user_id=\"all\"></at>" + msg))
                .toString();

        String post = HttpUtil.post(openapi + webhook, body);
        log.info("请求飞书机器人： {} --> {}", body, post);

        try {
            if (Integer.parseInt(JSONUtil.parseObj(post).get("code").toString()) != 0) {
                log.error("飞书机器人发送消息异常： {}", JSONUtil.parseObj(post).get("msg").toString());
                return false;
            }
        } catch (Exception e) {
            log.error("飞书机器人发送消息异常： {} {}", e.getMessage(), post);
            return false;
        }
        log.info(body);

        return true;
    }


    /*
     * 生成签名
     * https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN
     */
    static String GenSign(String secret, int timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        //把timestamp+"\n"+密钥当做签名字符串
        String stringToSign = timestamp + "\n" + secret;

        //使用HmacSHA256算法计算签名
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(new byte[]{});
        return new String(Base64Utils.encode(signData));
    }

}
