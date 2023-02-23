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
public class JsonTest2 {

    public static void main(String[] args) {
        String str = "[{\n" +
                "\t\"code\": 8577301901199,\n" +
                "\t\"delayTime\": \"0\",\n" +
                "\t\"description\": \"\",\n" +
                "\t\"environmentCode\": 8487131783552,\n" +
                "\t\"failRetryInterval\": \"1\",\n" +
                "\t\"failRetryTimes\": \"0\",\n" +
                "\t\"flag\": \"YES\",\n" +
                "\t\"name\": \"task_node_001\",\n" +
                "\t\"taskParams\": {\n" +
                "\t\t\"localParams\": [],\n" +
                "\t\t\"rawScript\": \"echo \\\"succ\\\"\",\n" +
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

        str = "hello";


    }

}
