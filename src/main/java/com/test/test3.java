package com.test;

import org.apache.commons.vfs2.FileSelector;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/5/24 0024
 * Time: 14:48
 */
public class test3 {

    /**
     * 本测试类慎用！！！！！！！
     * @param args
     */
    public static void main(String[] args) {
            runJob("G:\\etltest\\执行数据同步.kjb");
//        runTransfer();
    }
    /**
     *java 调用kettle 转换
     */
    public static void runTransfer(){
        Trans trans=null;
        FileSelector f = null;
        try {
            // 初始化
            String fName= "G:\\etltest\\数据同步.ktr";
            // 转换元对象
            KettleEnvironment.init();//初始化
            EnvUtil.environmentInit();
            TransMeta transMeta = new TransMeta(fName);
            // 转换
            trans = new Trans(transMeta);
            String [] s = {"abc","def"};
            trans.prepareExecution(s);
            // 执行转换
            trans.execute(null);
            // 等待转换执行结束
            trans.waitUntilFinished();
            //抛出异常
            if(trans.getErrors()>0){
                throw new Exception("There are errors during transformation exception!(传输过程中发生异常)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * java 调用 kettle 的job
     * @param jobname 如： String fName= "D:\\kettle\\informix_to_am_4.ktr";
     */
    public static void runJob(String jobname){
        try {
            KettleEnvironment.init();
            //jobname 是Job脚本的路径及名称
            JobMeta jobMeta = new JobMeta(jobname, null);
            Job job = new Job(null, jobMeta);
            //向Job 脚本传递参数，脚本中获取参数值：${参数名}
            job.setParameterValue("source", "155706");
            job.start();
            job.waitUntilFinished();
            if (job.getErrors() > 0) {
                throw new Exception("There are errors during job exception!(执行job发生异常)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 配置数据源 调用资源库中的相关job 、transfer
     */
    public static void dbResource(){
        String transName="t1";

        try {
            KettleEnvironment.init();
            DatabaseMeta dataMeta = new DatabaseMeta("KettleDBRep","MSSQL","Native","127.0.0.1","etl","1433","sa","bsoft");
            KettleDatabaseRepositoryMeta repInfo = new KettleDatabaseRepositoryMeta();
            repInfo.setConnection(dataMeta);
            KettleDatabaseRepository rep = new KettleDatabaseRepository();
            rep.init(repInfo);
            rep.connect("admin", "admin");

            RepositoryDirectoryInterface dir = new RepositoryDirectory();
            dir.setObjectId(rep.getRootDirectoryID());

            TransMeta tranMeta = rep.loadTransformation(rep.getTransformationID(transName, dir), null);
            Trans trans = new Trans(tranMeta);
            trans.execute(null);
            trans.waitUntilFinished();
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }
}
