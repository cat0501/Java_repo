package com.zjl.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.LinkedHashMap;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/2/22 10:05
 */
public class JsonTest1 {

    public static void main(String[] args) {
        String str = "[{\n" +
                "\t\"code\": 8577301908888,\n" +
                "\t\"delayTime\": \"0\",\n" +
                "\t\"description\": \"hhh\",\n" +
                "\t\"environmentCode\": 8487131783552,\n" +
                "\t\"failRetryInterval\": \"1\",\n" +
                "\t\"failRetryTimes\": \"0\",\n" +
                "\t\"flag\": \"YES\",\n" +
                "\t\"name\": \"task_node_009\",\n" +
                "\t\"taskParams\": {\n" +
                "\t\t\"localParams\": [],\n" +
                "\t\t\"rawScript\": \"echo \\\"success!\\\"\",\n" +
                "\t\t\"resourceList\": []\n" +
                "\t},\n" +
                "\t\"taskPriority\": \"MEDIUM\",\n" +
                "\t\"taskType\": \"SHELL\",\n" +
                "\t\"timeout\": 1,\n" +
                "\t\"timeoutFlag\": \"CLOSE\",\n" +
                "\t\"timeoutNotifyStrategy\": \"\",\n" +
                "\t\"workerGroup\": \"default\",\n" +
                "\t\"cpuQuota\": -1,\n" +
                "\t\"memoryMax\": -1,\n" +
                "\t\"taskExecuteType\": \"BATCH\"\n" +
                "}]";


        str = str.substring(1, str.length()-1);
        LinkedHashMap<String, Object> jsonMap = JSON.parseObject(str,LinkedHashMap.class, Feature.OrderedField);
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.putAll(jsonMap);

        // 获取 Json 串中某个字段值
        String code = jsonObject.getString("code");
        long parseInt = Long.parseLong(code.trim());
        System.out.println(parseInt);

        // 替换 Json 串中某个字段值
        jsonObject.put("code", 1);//替换key对应的值
        str = jsonObject.toJSONString();
        str = "[" + str + "]";
        System.out.println(str);

        //str.substring(1, str.length()-1);
        // 将json数据解析成JSONObject对象，以key-value形式存在
        //JSONObject jsonObject = JSONObject.parseObject(str);
        //String code = jsonObject.getString("code");
        //System.out.println(code);
        //System.out.println(str);

    }

}
