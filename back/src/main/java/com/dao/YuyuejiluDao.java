package com.dao;

import com.entity.YonghuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import com.entity.YuyuejiluEntity;
import com.entity.vo.YuyuejiluVO;
import org.apache.ibatis.annotations.Param;
import com.entity.vo.YonghuVO;
import com.entity.view.YonghuView;


/**
 *
 *
 * @author
 * @email
 * @date 2022-03-16 16:17:03
 */
public interface YuyuejiluDao extends BaseMapper<YuyuejiluEntity> {

    List<YuyuejiluVO> selectByDoctorAndDate(@Param("ew") Wrapper<YuyuejiluEntity> wrapper) ;

    List<YonghuVO> selectListVO(@Param("ew") Wrapper<YonghuEntity> wrapper);

    YonghuVO selectVO(@Param("ew") Wrapper<YonghuEntity> wrapper);

    List<YonghuView> selectListView(@Param("ew") Wrapper<YonghuEntity> wrapper);

    List<YonghuView> selectListView(Pagination page,@Param("ew") Wrapper<YonghuEntity> wrapper);

    YonghuView selectView(@Param("ew") Wrapper<YonghuEntity> wrapper);


}
