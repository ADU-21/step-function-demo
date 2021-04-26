package com.adu21.step.function.demo.activties.deployment;

import com.adu21.step.function.demo.activties.AbstractStepFunctionActivity;
import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.model.AbstractDeploymentActivityInput;
import com.adu21.step.function.demo.model.AbstractDeploymentActivityOutput;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Log4j2
public abstract class AbstractDeploymentActivity<INPUT extends AbstractDeploymentActivityInput,
    OUTPUT extends AbstractDeploymentActivityOutput>
    extends AbstractStepFunctionActivity<INPUT, OUTPUT> {
    public AbstractDeploymentActivity(AWSStepFunctionHandler awsStepFunctionHandler,
        Gson gson) {
        super(awsStepFunctionHandler, gson);
    }
}
