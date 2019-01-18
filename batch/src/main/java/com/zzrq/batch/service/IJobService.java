package com.zzrq.batch.service;

import com.zzrq.batch.dto.JobDetails;
import com.zzrq.batch.dto.Triggers;

import java.util.List;
import java.util.Map;

public interface IJobService {

    void addJob(String jobName, String jobClassName, String jobGroupName, String cronExpression) throws Exception;

    void jobPause(String jobName, String jobGroupName) throws Exception;

    void jobResume(String jobName, String jobGroupName) throws Exception;

    void jobReschedule(String jobName, String jobGroupName, String cronExpression) throws Exception;

    void jobDelete(String jobClassName, String jobGroupName) throws Exception;

    List<Map<String, String>> queryJob();

    List<JobDetails> queryJobDetail(Integer pageNum, Integer pageSize);

    List<Triggers> queryJobTrigger(Integer pageNum, Integer pageSize);
}
