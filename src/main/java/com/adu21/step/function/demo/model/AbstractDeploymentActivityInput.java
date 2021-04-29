package com.adu21.step.function.demo.model;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDeploymentActivityInput {
    @NonNull
    private UUID deploymentId;

    protected boolean isAbstractDeploymentActivityInputValid() {
        return deploymentId != null;
    }

    public abstract boolean isValid();
}
