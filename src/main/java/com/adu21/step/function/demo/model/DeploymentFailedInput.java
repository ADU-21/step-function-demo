package com.adu21.step.function.demo.model;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author LukeDu
 * @date 2021/4/28
 */
@Getter
public class DeploymentFailedInput extends AbstractDeploymentActivityInput {
    @NonNull
    private ActivityError error;

    @Override
    public boolean isValid() {
        return true;
    }
}
