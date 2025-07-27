package Listeners;

import Utils.LogsUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        LogsUtil.info("Test Case" + result.getName() + "Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        LogsUtil.info("Test Case" + result.getName() + "Passed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        LogsUtil.info("Test Case" + result.getName() + "Skipped");
    }
}
