package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.ExamrecordDao;
import com.entity.ExamrecordEntity;
import com.service.ExamrecordService;
import com.entity.vo.ExamrecordVO;
import com.entity.view.ExamrecordView;

@Service("examrecordService")
public class ExamrecordServiceImpl extends ServiceImpl<ExamrecordDao, ExamrecordEntity> implements ExamrecordService {
	
	@Override
	public PageUtils queryPageGroupBy(Map<String, Object> params, Wrapper<ExamrecordEntity> wrapper) {
		Page<ExamrecordView> page =new Query<ExamrecordView>(params).getPage();
        page.setRecords(baseMapper.selectGroupBy(page,wrapper));
    	PageUtils pageUtil = new PageUtils(page);
    	return pageUtil;
	}
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamrecordEntity> page = this.selectPage(
                new Query<ExamrecordEntity>(params).getPage(),
                new EntityWrapper<ExamrecordEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ExamrecordEntity> wrapper) {
		  Page<ExamrecordView> page =new Query<ExamrecordView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}
    
    @Override
	public List<ExamrecordVO> selectListVO(Wrapper<ExamrecordEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamrecordVO selectVO(Wrapper<ExamrecordEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamrecordView> selectListView(Wrapper<ExamrecordEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamrecordView selectView(Wrapper<ExamrecordEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

	@Override
	public PageUtils queryDoctorPatientRecords(Map<String, Object> params, Long doctorId) {
		Page<ExamrecordView> page = new Query<ExamrecordView>(params).getPage();
		String username = (String) params.get("username");
		String papername = (String) params.get("papername");
		page.setRecords(baseMapper.selectDoctorPatientRecords(page, doctorId, username, papername));
		return new PageUtils(page);
	}
}
