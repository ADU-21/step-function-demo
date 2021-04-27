package com.adu21.step.function.demo.model;

import lombok.Getter;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Getter
public class DeploymentInput extends AbstractDeploymentActivityInput {
    @Override
    public boolean isValid() {
        return isAbstractDeploymentActivityInputValid();
    }
}
