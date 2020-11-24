package com.tanxin.framework.domain.course.response;

import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName CoursePublishResult
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/21 20:07
 */
@Data
@ToString
@NoArgsConstructor
public class CoursePublishResult extends ResponseResult {
    String previewUrl;

    public CoursePublishResult(ResultCode resultCode, String previewUrl) {
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
