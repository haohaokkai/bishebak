package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ZhenduanjiluEntity;
import com.entity.view.ZhenduanjiluView;

import com.service.ZhenduanjiluService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 诊断记录
 * 后端接口
 * @author 
 * @email 
 * @date 2022-03-16 16:17:03
 */
@RestController
@RequestMapping("/zhenduanjilu")
public class ZhenduanjiluController {
    @Autowired
    private ZhenduanjiluService zhenduanjiluService;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ZhenduanjiluEntity zhenduanjilu,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			zhenduanjilu.setYonghuzhanghao((String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("yisheng")) {
			zhenduanjilu.setYishengzhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<ZhenduanjiluEntity> ew = new EntityWrapper<ZhenduanjiluEntity>();
		PageUtils page = zhenduanjiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zhenduanjilu), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ZhenduanjiluEntity zhenduanjilu, 
		HttpServletRequest request){
        EntityWrapper<ZhenduanjiluEntity> ew = new EntityWrapper<ZhenduanjiluEntity>();
		PageUtils page = zhenduanjiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zhenduanjilu), params), params));
        return R.ok().put("data", page);
    }

	@IgnoreAuth
	@RequestMapping("/list/{yonghuzhanghao}")
	public R listById(@RequestParam Map<String, Object> params,ZhenduanjiluEntity zhenduanjilu,@PathVariable("yonghuzhanghao") String Yonghuzhanghao,
				  HttpServletRequest request){
		zhenduanjilu.setYonghuzhanghao(Yonghuzhanghao);
		EntityWrapper<ZhenduanjiluEntity> ew = new EntityWrapper<ZhenduanjiluEntity>();
		params.put("yonghuzhanghao",Yonghuzhanghao);
		// 如果前端传递了 "yonghuzhanghao" 参数，就将其添加为查询条件
//		if (params.containsKey("yonghuzhanghao")) {
//			ew.eq("yonghuzhanghao", params.get("yonghuzhanghao").toString());  // 使用模糊查询
//		}


		PageUtils page = zhenduanjiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zhenduanjilu), params), params));

		return R.ok().put("data", page);
	}


	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ZhenduanjiluEntity zhenduanjilu){
       	EntityWrapper<ZhenduanjiluEntity> ew = new EntityWrapper<ZhenduanjiluEntity>();
      	ew.allEq(MPUtil.allEQMapPre( zhenduanjilu, "zhenduanjilu")); 
        return R.ok().put("data", zhenduanjiluService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ZhenduanjiluEntity zhenduanjilu){
        EntityWrapper< ZhenduanjiluEntity> ew = new EntityWrapper< ZhenduanjiluEntity>();
 		ew.allEq(MPUtil.allEQMapPre( zhenduanjilu, "zhenduanjilu")); 
		ZhenduanjiluView zhenduanjiluView =  zhenduanjiluService.selectView(ew);
		return R.ok("查询诊断记录成功").put("data", zhenduanjiluView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ZhenduanjiluEntity zhenduanjilu = zhenduanjiluService.selectById(id);
        return R.ok().put("data", zhenduanjilu);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ZhenduanjiluEntity zhenduanjilu = zhenduanjiluService.selectById(id);
        return R.ok().put("data", zhenduanjilu);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZhenduanjiluEntity zhenduanjilu, HttpServletRequest request){
    	zhenduanjilu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(zhenduanjilu);
        zhenduanjiluService.insert(zhenduanjilu);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ZhenduanjiluEntity zhenduanjilu, HttpServletRequest request){
    	zhenduanjilu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(zhenduanjilu);
        zhenduanjiluService.insert(zhenduanjilu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ZhenduanjiluEntity zhenduanjilu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(zhenduanjilu);
        zhenduanjiluService.updateById(zhenduanjilu);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        zhenduanjiluService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<ZhenduanjiluEntity> wrapper = new EntityWrapper<ZhenduanjiluEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			wrapper.eq("yonghuzhanghao", (String)request.getSession().getAttribute("username"));
		}
		if(tableName.equals("yisheng")) {
			wrapper.eq("yishengzhanghao", (String)request.getSession().getAttribute("username"));
		}

		int count = zhenduanjiluService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	







}
