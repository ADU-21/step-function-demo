package com.adu21.step.function.demo.activties;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Log4j2
public abstract class AbstractStepFunctionActivity<INPUT, OUTPUT> {
    private final AWSStepFunctionHandler awsStepFunctionHandler;
    private final Gson gson;
    private final Class<INPUT> inputClass;

    public AbstractStepFunctionActivity(AWSStepFunctionHandler awsStepFunctionHandler, Gson gson) {
        this.awsStepFunctionHandler = awsStepFunctionHandler;
        this.gson = gson;

        Type type = TypeToken.of(getClass()).getSupertype(AbstractStepFunctionActivity.class).getType();
        this.inputClass = (Class<INPUT>)((ParameterizedType)type).getActualTypeArguments()[0];
    }

    protected abstract boolean validateInput(INPUT input);

    protected abstract OUTPUT executeTask(INPUT input);

    public void execute() {
        executeTask(null);
    }

    ;
}
