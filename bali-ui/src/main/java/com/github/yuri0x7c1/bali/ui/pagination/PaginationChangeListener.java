package com.github.yuri0x7c1.bali.ui.pagination;

import java.io.Serializable;

/**
 * Created by basakpie on 2017-05-18.
 * @author yuri0x7c1
 */
public interface PaginationChangeListener extends Serializable {
    void changed(PaginationResource event);
}
