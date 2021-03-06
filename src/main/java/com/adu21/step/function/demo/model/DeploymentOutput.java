package com.adu21.step.function.demo.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author LukeDu
 * @date 2021/4/25
 */
@Getter
@SuperBuilder
public class DeploymentOutput extends AbstractDeploymentActivityOutput {
    private final String deploymentMessage;
}
