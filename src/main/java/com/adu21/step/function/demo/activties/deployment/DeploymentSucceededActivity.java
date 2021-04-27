package com.adu21.step.function.demo.activties.deployment;

import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.model.DeploymentSucceededInput;
import com.adu21.step.function.demo.model.DeploymentSucceededOutput;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/26
 */
@Log4j2
public class DeploymentSucceededActivity
    extends AbstractDeploymentActivity<DeploymentSucceededInput, DeploymentSucceededOutput> {

    public DeploymentSucceededActivity(String arn,
        AWSStepFunctionHandler awsStepFunctionHandler, Gson gson) {
        super(arn, awsStepFunctionHandler, gson);
    }

    @Override
    protected boolean validateInput(DeploymentSucceededInput request) {
        return request.isValid();
    }

    @Override
    protected DeploymentSucceededOutput executeTask(DeploymentSucceededInput request) {
        log.info("Deployment succeed!");
        return DeploymentSucceededOutput.builder()
            .deploymentId(request.getDeploymentId())
            .successMessage("Deployment succeed!")
            .build();
    }
}
