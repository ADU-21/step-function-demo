package com.adu21.step.function.demo.activties.deployment;

import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.model.DeploymentInput;
import com.adu21.step.function.demo.model.DeploymentOutput;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Log4j2
public class DeploymentActivity extends AbstractDeploymentActivity<DeploymentInput, DeploymentOutput> {

    public DeploymentActivity(String arn,
        AWSStepFunctionHandler awsStepFunctionHandler, Gson gson) {
        super(arn, awsStepFunctionHandler, gson);
    }

    @Override
    protected boolean validateInput(DeploymentInput request) {
        return request.isValid();
    }

    @Override
    protected DeploymentOutput executeTask(DeploymentInput request) {
        log.info("Deploying...");
        return DeploymentOutput.builder()
            .deploymentId(request.getDeploymentId())
            .deploymentMessage("Deploying...")
            .build();
    }
}
