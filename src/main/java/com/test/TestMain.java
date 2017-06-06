package com.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/5/25 0025
 * Time: 17:00
 */
public class TestMain {

    public static void main(String[] args) {
        Map<String,String> tmp = new HashMap<>();
//        tmp.put("name","雷2杰");
//        tmp.put("pwd","123");
        tmp.put("json","{\"title\":\"leijie\",\"pwd\":\"123\"}");
          KettleUtil.runJob(tmp,"\\data\\test.kjb");
//            KettleUtil.runTrans(tmp,"\\data\\test.ktr");
//        runTrans(null,"/data/jsondb.ktr");
//        test4.JobFileRepository();
    }

}
