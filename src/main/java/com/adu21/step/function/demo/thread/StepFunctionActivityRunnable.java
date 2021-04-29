package com.adu21.step.function.demo.thread;

import java.util.concurrent.ExecutorService;

import com.adu21.step.function.demo.activties.AbstractStepFunctionActivity;
import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/24
 */
@Log4j2
@RequiredArgsConstructor
public class StepFunctionActivityRunnable<T extends AbstractStepFunctionActivity<?, ?>> implements Runnable {
    @NonNull
    private final T stepFunctionActivity;
    @NonNull
    private final AWSStepFunctionHandler awsStepFunctionHandler;
    @NonNull
    private final ExecutorService stepFunctionActivityExecutor;

    @Override
    public void run() {
        String awsStepFunctionActivityARN = stepFunctionActivity.getAWSStepFunctionActivityARN();
        log.debug("Check {}...", awsStepFunctionActivityARN);
        GetActivityTaskResult activityTaskResult = awsStepFunctionHandler.getActivityTaskResult(
            awsStepFunctionActivityARN);
        if (activityTaskResult != null && activityTaskResult.getTaskToken() != null) {
            log.debug("Executing {}", awsStepFunctionActivityARN);
            stepFunctionActivityExecutor.submit(() -> {
                try {
                    stepFunctionActivity.execute(activityTaskResult);
                } catch (Exception e) {
                    log.error("Exception executing activity {} for ARN {}", stepFunctionActivity.getClass().getName(),
                        awsStepFunctionActivityARN, e);
                }
            });
        } else {
            log.debug("No activity task found for activity {} and ARN {}", stepFunctionActivity.getClass().getName(),
                awsStepFunctionActivityARN);
        }
    }
}
