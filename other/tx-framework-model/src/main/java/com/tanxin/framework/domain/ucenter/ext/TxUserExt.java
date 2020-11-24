package com.tanxin.framework.domain.ucenter.ext;

import com.tanxin.framework.domain.ucenter.TxMenu;
import com.tanxin.framework.domain.ucenter.TxUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class TxUserExt extends TxUser {

    // 权限信息
    private List<TxMenu> permissions;

    // 企业信息
    private String companyId;
}
