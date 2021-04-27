package com.adu21.step.function.demo.thread;

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
    private static final String ARN = "arn:aws:states:us-east-1:393344114027:activity:Deployment";
    @NonNull
    private final T stepFunctionActivity;
    @NonNull
    private final AWSStepFunctionHandler awsStepFunctionHandler;

    @Override
    public void run() {
        log.info("running...");
        GetActivityTaskResult activityTaskResult = awsStepFunctionHandler.getActivityTaskResult(ARN);
        if (activityTaskResult != null && activityTaskResult.getTaskToken() != null) {
            stepFunctionActivity.execute(activityTaskResult);
        } else {
            log.error("No activity task found for activity {} and ARN {}", stepFunctionActivity.getClass().getName(),
                ARN);
        }
    }
}
