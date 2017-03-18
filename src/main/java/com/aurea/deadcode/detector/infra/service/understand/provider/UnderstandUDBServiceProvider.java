package com.aurea.deadcode.detector.infra.service.understand.provider;

import com.aurea.deadcode.detector.infra.service.understand.UnderstandUDBService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UnderstandUDBServiceProvider implements UnderstandUDBService {

    @Override
    public String createUDB(final String repositoryUrl, final String udbName) {

        final String baseDir = System.getProperty("user.dir");
        final CommandLine cmd1 = new CommandLine(
                baseDir + "/create_udb.sh");
        cmd1.addArgument(repositoryUrl);
        cmd1.addArgument(udbName);

        final Executor exec = new DefaultExecutor();
        try {
            exec.execute(cmd1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/src/main/resources/analyze/udb/" +
                udbName +
                ".udb";
    }

    @Override
    public void deleteUDB(String repositoryUrl, String udbName) {

        final String baseDir = System.getProperty("user.dir");
        final CommandLine cmd1 = new CommandLine(
                baseDir + "/delete_given_analyzed_files.sh");
        cmd1.addArgument(repositoryUrl);
        cmd1.addArgument(udbName);

        final Executor exec = new DefaultExecutor();
        try {
            exec.execute(cmd1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}