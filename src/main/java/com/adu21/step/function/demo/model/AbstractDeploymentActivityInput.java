package com.adu21.step.function.demo.model;

import java.util.UUID;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
public abstract class AbstractDeploymentActivityInput {
    private UUID deploymentId;

    protected boolean isAbstractDeploymentActivityInputValid() {
        return deploymentId != null;
    }

    public abstract boolean isValid();
}
