package com.test;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/6/5 0005
 * Time: 16:10
 */
public class SlaveServer {

    @Value("${kettleHostName}")
    private String kettleHostName = "192.168.30.65";
    @Value("${kettlePort:8081}")
    private String kettlePort = "8081";
    @Value("${kettleUserName:cluster}")
    private String kettleUserName = "cluster" ;
    @Value("${kettlePassword:cluster}")
    private String kettlePassword = "cluster";

    private SlaveServer getRemoteServer() {

        // 创建工作服务器的连接

//        if(remoteServer == null){
//
//            synchronized(SlaveServerExecutor.class){
//
//                if(remoteServer != null){
//
//                    return remoteServer ;
//
//                }
//
//                try {
//
//                    remoteServer = new SlaveServer();
//
//                    remoteServer.setHostname(kettleHostName);
//
//                    remoteServer.setPort(kettlePort);
//
//                    remoteServer.setUsername(kettleUserName);
//
//                    remoteServer.setPassword(kettlePassword);
//
//                    return remoteServer;
//
//                } catch (Exception e) {
//
//                    remoteServer = null ;
//
//                    log.error("创建SlaveServer失败:{}", e.getMessage());
//
//                }
//
//                return remoteServer;
//
//            }
//
//        }
//
//        return remoteServer;
//
        return null;
    }

}
