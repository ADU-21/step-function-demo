package com.adu21.step.function.demo.activties;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.amazonaws.services.stepfunctions.model.GetActivityTaskResult;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Log4j2
public abstract class AbstractStepFunctionActivity<INPUT, OUTPUT> {
    @Getter
    private final String AWSStepFunctionActivityARN;
    private final AWSStepFunctionHandler awsStepFunctionHandler;
    private final Gson gson;
    private final Class<INPUT> inputClass;

    @SuppressWarnings("unchecked")
    public AbstractStepFunctionActivity(String arn, AWSStepFunctionHandler awsStepFunctionHandler, Gson gson) {
        this.AWSStepFunctionActivityARN = arn;
        this.awsStepFunctionHandler = awsStepFunctionHandler;
        this.gson = gson;

        Type type = TypeToken.of(getClass()).getSupertype(AbstractStepFunctionActivity.class).getType();
        this.inputClass = (Class<INPUT>)((ParameterizedType)type).getActualTypeArguments()[0];
    }

    protected abstract boolean validateInput(INPUT input);

    protected abstract OUTPUT executeTask(INPUT input);

    public void execute(GetActivityTaskResult activityTaskResult) {
        String taskToken = activityTaskResult.getTaskToken();
        try {
            INPUT input = getInput(activityTaskResult);

            if (!validateInput(input)) {
                log.error(getDefaultInvalidInputMessage());
                sendTaskFailure(taskToken, getDefaultInvalidInputMessage());
                return;
            }
            OUTPUT output = executeTask(input);
            sendTaskSuccess(taskToken, output);
        } catch (Exception e) {
            log.error(e);
            sendTaskError(taskToken, e);
        }
    }

    private void sendTaskError(String taskToken, Exception e) {
        String errorMessage = ObjectUtils.firstNonNull(e.getMessage(), getDefaultInvalidInputMessage());
        awsStepFunctionHandler.sendTaskFailure(taskToken, null, errorMessage);
    }

    private void sendTaskSuccess(String taskToken, OUTPUT output) {
        awsStepFunctionHandler.sendTaskSuccess(taskToken, gson.toJson(output));
    }

    private void sendTaskFailure(String taskToken, String causeMessage) {
        awsStepFunctionHandler.sendTaskFailure(taskToken, causeMessage, null);
    }

    private String getDefaultInvalidInputMessage() {
        return "Provided input was not valid";
    }

    private INPUT getInput(GetActivityTaskResult activityTaskResult) {
        return gson.fromJson(activityTaskResult.getInput(), inputClass);
    }
}
