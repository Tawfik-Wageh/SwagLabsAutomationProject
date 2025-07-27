package Listeners;


import Utils.GeneralUtils;
import Utils.LogsUtil;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethodListener implements org.testng.IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        org.testng.IInvokedMethodListener.super.beforeInvocation(method, testResult, context);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        org.testng.IInvokedMethodListener.super.afterInvocation(method, testResult, context);

        // ++++++++++++++++++++++++++
        //  to take a full screenshot using Shutterbug :
        //  GeneralUtils.takingFullScreenshot(getDriver(), new P02_LandingPage(getDriver()).getNumberOfSelectedProductsOnCart());
        // ++++++++++++++++++++++++++

        // ++++++++++++++++++++++++++++++++++++++
        // to add last Log's file to Allure Report
        File logFile = GeneralUtils.getLatestFile(LogsUtil.LOGS_PATH);
        try {
            assert logFile != null;
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // +++++++++++++++++++++++++++++++++++++++++


        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtil.info("Test Case" + testResult.getName() + "Failed");
            GeneralUtils.takingScreenShot(getDriver(), testResult.getName());

        }
    }
}
