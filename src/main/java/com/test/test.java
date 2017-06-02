package com.test;

import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/5/23 0023
 * Time: 16:46
 */
public class test {

    public static void main(String[] args) throws KettleException {
        //数据库连接元对象
        DatabaseMeta dataMeta = new DatabaseMeta("", "mysql", "Native", "192.168.3.251", "etl_test", "3306", "root", "root");
        //资源库元对象
        KettleDatabaseRepositoryMeta repInfo = new KettleDatabaseRepositoryMeta();
        repInfo.setConnection(dataMeta);

        //资源库
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        repository.init(repInfo);
        repository.connect("admin", "admin");

        RepositoryDirectoryInterface directory = repository.loadRepositoryDirectoryTree();
        directory = directory.findDirectory("/");


        TransMeta transMeta = repository.loadTransformation("ndmp-etl-jianfei-trans", directory, new ProgressNullMonitorListener(), true, "1.0");
        Trans trans = new Trans(transMeta);
        trans.execute(null);
        trans.waitUntilFinished();

        JobMeta jobMeta = repository.loadJob("test-job", directory, new ProgressNullMonitorListener(), null);

        Job job = new Job(repository, jobMeta);
        job.setDaemon(true);
        job.setLogLevel(LogLevel.DEBUG);
        job.run();

        job.waitUntilFinished();
    }
}
