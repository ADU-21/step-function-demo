package com.adu21.step.function.demo.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author LukeDu
 * @date 2021/4/26
 */
@Getter
@SuperBuilder
public class DeploymentSucceededOutput extends AbstractDeploymentActivityOutput {
    private final String successMessage;
}
