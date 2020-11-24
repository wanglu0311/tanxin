package com.tanxin.framework.domain.ucenter.ext;

import com.tanxin.framework.domain.course.ext.CategoryNode;
import com.tanxin.framework.domain.ucenter.TxMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class TxMenuExt extends TxMenu {

    List<CategoryNode> children;
}
