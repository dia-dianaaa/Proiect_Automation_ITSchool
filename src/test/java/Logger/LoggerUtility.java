package Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public final class LoggerUtility {

    private static final String SUITE_LOGS_PATH = "target/logs/suite/";
    private static final String REGRESSION_LOGS_PATH = "target/logs/";
    private static final Logger LOGGER = LogManager.getLogger();

    private LoggerUtility() {
    }

    public static synchronized void startTestCase(String testName) {
        ThreadContext.put("threadName", testName);
        LOGGER.info("===== Execution started: " + testName + " =====");
    }

    public static synchronized void endTestCase(String testName) {
        ThreadContext.put("threadName", testName);
        LOGGER.info("===== Execution ended: " + testName + " =====");
    }

    public static synchronized void infoTestCase(String message) {
        LOGGER.info(Thread.currentThread().getName() + " " + getCallInfo() + " " + message);
    }

    public static synchronized void errorLog(String message) {
        LOGGER.error(Thread.currentThread().getName() + " " + getCallInfo() + " " + message);
    }

    public static synchronized String getCallInfo() {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        return className + " : " + methodName + "=>";
    }

    public static void mergeFiles() {
        File dir = new File(SUITE_LOGS_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        String[] fileNames = dir.list();
        if (fileNames == null || fileNames.length == 0) {
            return;
        }

        File regressionDir = new File(REGRESSION_LOGS_PATH);
        if (!regressionDir.exists()) {
            regressionDir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(REGRESSION_LOGS_PATH + "RegressionLogs.log")) {
            for (String fileName : fileNames) {
                File file = new File(dir, fileName);
                writer.println("Content " + fileName);
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    while (line != null) {
                        writer.println(line);
                        line = reader.readLine();
                    }
                }
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
