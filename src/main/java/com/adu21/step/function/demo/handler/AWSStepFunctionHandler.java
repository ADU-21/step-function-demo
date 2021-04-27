package com.adu21.step.function.demo.handler;

import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskRequest;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskResult;
import com.amazonaws.services.stepfunctions.model.SendTaskFailureRequest;
import com.amazonaws.services.stepfunctions.model.SendTaskFailureResult;
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessRequest;
import com.amazonaws.services.stepfunctions.model.SendTaskSuccessResult;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Log4j2
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AWSStepFunctionHandler {
    // https://docs.aws.amazon.com/step-functions/latest/apireference/API_SendTaskFailure.html
    private static final int MAX_CAUSE_MESSAGE_LENGTH = 32768;
    private static final int MAX_ERROR_MESSAGE_LENGTH = 256;
    private final AWSStepFunctions awsStepFunctionClient;

    public GetActivityTaskResult getActivityTaskResult(String stepFunctionActivityARN) {
        GetActivityTaskRequest getActivityTaskRequest = new GetActivityTaskRequest().withActivityArn(
            stepFunctionActivityARN);
        return awsStepFunctionClient.getActivityTask(getActivityTaskRequest);
    }

    public void sendTaskFailure(String taskToken, String causeMessage, String errorMessage) {
        SendTaskFailureRequest sendTaskFailureRequest = new SendTaskFailureRequest()
            .withTaskToken(taskToken);

        if (causeMessage != null) {
            sendTaskFailureRequest.withCause(StringUtils.abbreviate(causeMessage, MAX_CAUSE_MESSAGE_LENGTH));
        }

        if (errorMessage != null) {
            sendTaskFailureRequest.withCause(StringUtils.abbreviate(errorMessage, MAX_ERROR_MESSAGE_LENGTH));
        }

        SendTaskFailureResult sendTaskFailureResult = awsStepFunctionClient.sendTaskFailure(sendTaskFailureRequest);

        if (sendTaskFailureResult != null && sendTaskFailureResult.getSdkHttpMetadata() != null
            && HttpStatus.SC_OK != sendTaskFailureResult.getSdkHttpMetadata().getHttpStatusCode()) {
            log.error("Failed to send task failure for token: {}", taskToken);
        }
    }

    public void sendTaskSuccess(String taskToken, String outputMessage) {
        SendTaskSuccessRequest sendTaskSuccessRequest = new SendTaskSuccessRequest().withTaskToken(taskToken);

        if (outputMessage != null) {
            sendTaskSuccessRequest.withOutput(outputMessage);
        }

        SendTaskSuccessResult sendTaskSuccessResult = awsStepFunctionClient.sendTaskSuccess(sendTaskSuccessRequest);

        if (sendTaskSuccessResult != null && sendTaskSuccessResult.getSdkHttpMetadata() != null
            && HttpStatus.SC_OK != sendTaskSuccessResult.getSdkHttpMetadata().getHttpStatusCode()) {
            log.error("Failed to send task success for token: {}", taskToken);
        }
    }
}
