package com.adu21.step.function.demo.model;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDeploymentActivityInput {
    private UUID deploymentId;

    protected boolean isAbstractDeploymentActivityInputValid() {
        return deploymentId != null;
    }

    public abstract boolean isValid();
}
