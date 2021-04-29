package com.adu21.step.function.demo.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author LukeDu
 * @date 2021/4/28
 */
@Data
public class ActivityError {
    @SerializedName("Error")
    private String error;

    @SerializedName("Cause")
    private String cause;
}
