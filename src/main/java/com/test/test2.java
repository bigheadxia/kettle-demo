package com.test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoriesMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/5/23 0023
 * Time: 17:09
 */
public class test2 {

    public static void main(String[] args) throws KettleException {
//        executeJob("/","执行数据同步");
        System.getProperty("user.dir");
        System.getProperty("KETTLE_HOME");
    }

    public void test() throws KettleException {
        //初始化环境
        KettleEnvironment.init();
        //创建DB资源库
        KettleDatabaseRepository repository=new KettleDatabaseRepository();
        DatabaseMeta databaseMeta=new DatabaseMeta("","mysql","jdbc","localhost","kettle","3306","root","root");
        //选择资源库
        KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta=new KettleDatabaseRepositoryMeta("Kettle","Kettle","Transformation description",databaseMeta);
        repository.init(kettleDatabaseRepositoryMeta);
        //连接资源库
        repository.connect("admin","admin");
        RepositoryDirectoryInterface directoryInterface=repository.loadRepositoryDirectoryTree();
        directoryInterface = directoryInterface.findDirectory("/");
        //选择转换
//        TransMeta transMeta=repository.loadTransformation("大数据测试",directoryInterface,null,true,null);
//        Trans trans=new Trans(transMeta);
//        trans.execute(null);
//        trans.waitUntilFinished();//等待直到数据结束
//        if(trans.getErrors()>0){
//            System.out.println("transformation error");
//        }else{
//            System.out.println("transformation successfully");
//        }
        JobMeta jobMeta = repository.loadJob("数据同步", directoryInterface, new ProgressNullMonitorListener(), null);

        Job job = new Job(repository, jobMeta);
        job.setDaemon(true);
//        job.setLogLevel(LogLevel.DEBUG);
        job.run();

        job.waitUntilFinished();
        if(job.getErrors()>0){
            System.out.println("job error");
        }else{
            System.out.println("job successfully");
        }

    }

    /**
     * Kettle执行Job
     * @throws KettleException
     */
    public static void executeJob(String dir, String jobname) throws KettleException {

        RepositoriesMeta repositoriesMeta = new RepositoriesMeta();
        // 从文件读取登陆过的资源库信息
        repositoriesMeta.readData();
        // 选择登陆过的资源库
        RepositoryMeta repositoryMeta = repositoriesMeta.findRepository("FileKettle");
        // 获得资源库实例
        Repository repository = PluginRegistry.getInstance().loadClass(RepositoryPluginType.class, repositoryMeta, Repository.class);
        repository.init(repositoryMeta);
        // 连接资源库
        repository.connect("admin", "admin");

        RepositoryDirectoryInterface tree = repository.loadRepositoryDirectoryTree();
        RepositoryDirectoryInterface fooBar = tree.findDirectory(dir);
        JobMeta jobMeta = repository.loadJob(jobname, fooBar, null, null);
        // 执行指定作业
        Job job = new Job(repository, jobMeta);
        job.start();
        job.waitUntilFinished();
        Result result = job.getResult();
        result.getRows();
        if (job.getErrors() > 0) {
            throw new RuntimeException("There were errors during transformation execution.");
        }
        repository.disconnect();
    }

    public static void test2() throws KettleException {

    }
}
