package com.adu21.step.function.demo.activties.deployment;

import com.adu21.step.function.demo.handler.AWSStepFunctionHandler;
import com.adu21.step.function.demo.model.DeploymentFailedInput;
import com.adu21.step.function.demo.model.DeploymentFailedOutput;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.extern.log4j.Log4j2;

/**
 * @author LukeDu
 * @date 2021/4/28
 */
@Log4j2
@Singleton
public class DeploymentFailedActivity
    extends AbstractDeploymentActivity<DeploymentFailedInput, DeploymentFailedOutput> {
    @Inject
    public DeploymentFailedActivity(@Named("aws.step.function.activity.deploymentFailed.arn") String arn,
        AWSStepFunctionHandler awsStepFunctionHandler, Gson gson) {
        super(arn, awsStepFunctionHandler, gson);
    }

    @Override
    protected boolean validateInput(DeploymentFailedInput request) {
        return request.isValid();
    }

    @Override
    protected DeploymentFailedOutput executeTask(DeploymentFailedInput request) {
        log.info("Deployment Failed!");
        return DeploymentFailedOutput.builder()
            .deploymentId(request.getDeploymentId())
            .errorMessage(request.getError().getError())
            .cause(request.getError().getCause())
            .build();
    }
}
