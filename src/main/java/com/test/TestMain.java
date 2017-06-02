package com.test;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/5/25 0025
 * Time: 17:00
 */
public class TestMain {

    public static void main(String[] args) {
//        Map<String,String> tmp = new HashMap<>();
//        tmp.put("name","雷2杰");
//        tmp.put("pwd","123");
//        tmp.put("jsonstr","{\"name\":\"leijie\",\"pwd\":\"123\"}");
          KettleUtil.runJob(null,"/data/ApiJob.kjb");

//        runTrans(null,"/data/jsondb.ktr");
//        test4.JobFileRepository();
    }

}
