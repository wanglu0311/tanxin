package com.tanxin.framework.domain.course.request;

import com.tanxin.framework.model.request.RequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/4/13.
 */
@AllArgsConstructor
@Data
@ToString
public class CourseListRequest extends RequestData {
    // 公司Id
    private String companyId;
}
