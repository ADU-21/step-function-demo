package com.adu21.step.function.demo.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author LukeDu
 * @date 2021/4/28
 */
@Getter
@SuperBuilder
public class DeploymentFailedOutput extends AbstractDeploymentActivityOutput {
    private final String errorMessage;
    private final String cause;
}
