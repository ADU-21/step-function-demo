package com.adu21.step.function.demo.model;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
public class DeploymentInput extends AbstractDeploymentActivityInput {
    @Override
    public boolean isValid() {
        return isAbstractDeploymentActivityInputValid();
    }
}
