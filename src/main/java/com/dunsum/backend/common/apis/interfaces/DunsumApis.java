package com.dunsum.backend.common.apis.interfaces;

import com.dunsum.backend.common.vo.environment.AppConnDataVO;

public abstract class DunsumApis {

    public AppConnDataVO connData;

    public abstract void setConnData() throws Exception;
}
