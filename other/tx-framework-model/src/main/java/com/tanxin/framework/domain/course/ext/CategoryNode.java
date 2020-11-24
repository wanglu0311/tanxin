package com.tanxin.framework.domain.course.ext;

import com.tanxin.framework.domain.course.Category;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/2/7.
 */
@Data
//@ToString
public class CategoryNode extends Category {

    List<CategoryNode> children;

    @Override
    public String toString() {
        System.out.println(super.toString());
        return "CategoryNode{" +
                "children=" + children +
                '}';
    }
}
