package com.chl.pullUpWeeds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chl.pullUpWeeds.entity.Comment;
import com.chl.pullUpWeeds.entity.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper extends BaseMapper<Log> {
    int addLog(Log log);

}
