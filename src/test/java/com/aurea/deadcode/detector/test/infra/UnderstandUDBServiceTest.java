package com.aurea.deadcode.detector.test.infra;

import com.aurea.deadcode.detector.test.support.TestSupport;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UnderstandUDBServiceTest extends TestSupport {

    private final static Path currentRelativePath = Paths.get("");
    private final static String baseDir = currentRelativePath.toAbsolutePath().toString();
    private static final String UDB_DIR = baseDir + "/src/main/resources/analyze/udb";

    @Override
    public void setUp() {

    }

    @Test
    public void createUDB() {

        final String repositoryUrl = "https://github.com/viniciusluisr/simple-functor";
        final String udbName = "simple-functor";
        final String baseDir = System.getProperty("user.dir");

        final CommandLine cmd1 = new CommandLine(baseDir + "/create_udb.sh");
        final CommandLine cmd2 = new CommandLine(baseDir + "/clean_analyzed_files.sh");
        cmd1.addArgument(repositoryUrl);
        cmd1.addArgument(udbName);

        File udbDir = new File(UDB_DIR);

        final Executor exec = new DefaultExecutor();
        try {
            exec.execute(cmd1);
            assertEquals(udbDir.exists() && udbDir.listFiles().length > 0, true);
            assertEquals(udbDir.exists() && udbDir.listFiles().length > 0, true);

            exec.execute(cmd2);
            assertEquals(udbDir.exists() && udbDir.listFiles().length == 0, true);
            assertEquals(udbDir.exists() && udbDir.listFiles().length == 0, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
