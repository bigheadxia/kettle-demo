package com.test;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created with IDEA
 * User: LEIJIE
 * Date: 2017/5/26 0026
 * Time: 17:19
 */
public class KettleUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(KettleUtil.class);
    static {
        try {
            KettleEnvironment.init();
        } catch (KettleException e) {
            LOGGER.error("static error",e);
        }
    }

    public static void runJob(Map<String,String> paraMap, String jobPath) {
        try {
            JobMeta jobMeta = new JobMeta(jobPath, null);
            Job job = new Job(null, jobMeta);
            if (paraMap != null && !paraMap.isEmpty()) {
                paraMap.forEach(job::setVariable);
            }
            job.start();
            job.waitUntilFinished();
            if (job.getErrors() > 0) {
                throw new Exception("Errors during job execution!");
            }
        } catch (Exception e) {
            LOGGER.error("runJob error",e);
        }
    }

    public static void runTrans(String[] params, String ktrPath) {
        try {
            KettleEnvironment.init();
            EnvUtil.environmentInit();
            TransMeta transMeta = new TransMeta(ktrPath);
            Trans trans = new Trans(transMeta);
            trans.execute(params);
            trans.waitUntilFinished();
            if (trans.getErrors() > 0) {
                throw new Exception("Errors during transformation execution!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
