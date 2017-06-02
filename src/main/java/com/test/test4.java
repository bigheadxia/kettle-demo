package com.test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleEOFException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/5/24 0024
 * Time: 15:15
 */
public class test4 {
    public static void main(String[] args) throws KettleException {
        JobFileRepository();
    }

    public static void tranRepository(){
        Trans trans=null;
        try {
            // 初始化
            KettleEnvironment.init();//这个是4.0版本的初始化，和3.x版本的不同
            // 资源库元对象
            KettleFileRepositoryMeta repinfo = new KettleFileRepositoryMeta("","","描述","file:///G:/fileRepository");
            // 文件形式的资源库
            KettleFileRepository rep = new KettleFileRepository();
            rep.init(repinfo);
            // 转换元对象
            TransMeta transMetadel = rep.loadTransformation(rep.getTransformationID("test-tra", null), null);
            // 转换
            trans = new Trans(transMetadel);
            // 执行转换
            trans.execute(null);
            // 等待转换执行结束
            trans.waitUntilFinished();
            //抛出异常
            if(trans.getErrors()>0){
                throw new Exception("There are errors during transformation exception!(传输过程中发生异常)");
            }
        } catch (Exception e) {
            if(trans!=null){
                trans.stopAll();
            }
            e.printStackTrace();
        }
    }

    public static void JobFileRepository() {
        String jobName = "test-job";// 传输名称
        Job job = null;
        try {
            // 初始化
            KettleEnvironment.init();
            // 资源库元对象
            KettleFileRepositoryMeta repMeta = new KettleFileRepositoryMeta("", "FileKettle", "Kettle2", "file:///G:/fileRepository");
            // repMeta.setBaseDirectory("file:///D:/ETL/Kettle");;
            // 文件形式的资源库
            KettleFileRepository rep = new KettleFileRepository();
            rep.init(repMeta);
            //RepositoryDirectoryInterface directory = rep.loadRepositoryDirectoryTree();
            //RepositoryDirectoryInterface directory = rep.findDirectory("file:///D:/ETL/Kettle");
            RepositoryDirectoryInterface directory = rep.findDirectory(repMeta.getBaseDirectory());
            // 作业对象
            if (jobName != null && !"".equals(job)) {
                // JobMeta jobMeta = rep.loadJob(jobName, directory, null, null);
                JobMeta jobMeta = rep.loadJob(jobName, directory, null, null);
                jobMeta.activateParameters();
                // 作业
                job = new Job(rep,jobMeta); //必须加rep否则无法
                jobMeta.setParameterValue("client", "clientA"); //only needed for ETL
                // 执行作业
                job.start();
                // 等待作业执行结束
                job.waitUntilFinished();
                // 抛出异常
                if (job.getErrors() > 0) {
                    throw new Exception("传输过程中发生异常");
                }
            } else {
                throw new KettleEOFException("传输名为空!");
            }
        } catch (Exception e) {
            if (job != null) {
                job.stopAll();
            }
            e.printStackTrace();
        }
    }
}
