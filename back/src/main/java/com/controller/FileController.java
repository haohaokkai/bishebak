package com.controller;

import java.io.*;
import org.apache.commons.io.FileUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.R;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked","rawtypes"})
public class FileController{
	@Autowired
    private ConfigService configService;
	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file,String type) throws Exception {
		if (file.isEmpty()) {
			throw new EIException("上传文件不能为空");
		}
		// 复制一份新的文件
		MultipartFile newFile = copyMultipartFile(file);

		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		File path = new File(ResourceUtils.getURL("classpath:static").getPath());
		if(!path.exists()) {
		    path = new File("");
		}
		File upload = new File(path.getAbsolutePath(),"/upload/");
		if(!upload.exists()) {
		    upload.mkdirs();
		}
		String fileName = new Date().getTime()+"."+fileExt;
		File dest = new File(upload.getAbsolutePath()+"/"+fileName);
		file.transferTo(dest);
		File dest2 = new File("F:\\Myproject\\BISHE\\pro2.0\\project\\back\\src\\main\\resources\\static\\upload\\"+fileName);
		newFile.transferTo(dest2);
		/**
  		 * 如果使用idea或者eclipse重启项目，发现之前上传的图片或者文件丢失，将下面一行代码注释打开
   		 * 请将以下的"D:\\springbootq33sd\\src\\main\\resources\\static\\upload"替换成你本地项目的upload路径，
 		 * 并且项目路径不能存在中文、空格等特殊字符
 		 */
//		FileUtils.copyFile(dest, new File("D:\\springbootq33sd\\src\\main\\resources\\static\\upload"+"/"+fileName)); /**修改了路径以后请将该行最前面的//注释去掉**/
		if(StringUtils.isNotBlank(type) && type.equals("1")) {
			ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
			if(configEntity==null) {
				configEntity = new ConfigEntity();
				configEntity.setName("faceFile");
				configEntity.setValue(fileName);
			} else {
				configEntity.setValue(fileName);
			}
			configService.insertOrUpdate(configEntity);
		}
		return R.ok().put("file", fileName);
	}

	//复制
	public MultipartFile copyMultipartFile(MultipartFile originalFile) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			// 将原始文件内容复制到输出流
			FileCopyUtils.copy(originalFile.getInputStream(), outputStream);
			// 将输出流转换为输入流，以便创建新的 MultipartFile
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			// 使用新的输入流创建新的 MultipartFile 对象（这里以MockMultipartFile为例）
			return new MockMultipartFile(originalFile.getName(), originalFile.getOriginalFilename(),
					originalFile.getContentType(), inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Failed to copy MultipartFile", e);
		}
	}

	/**
	 * 下载文件
	 */
	@IgnoreAuth
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(@RequestParam String fileName) {
		try {
			File path = new File(ResourceUtils.getURL("classpath:static").getPath());
			if(!path.exists()) {
			    path = new File("");
			}
			File upload = new File(path.getAbsolutePath(),"/upload/");
			if(!upload.exists()) {
			    upload.mkdirs();
			}
			File file = new File(upload.getAbsolutePath()+"/"+fileName);
			if(file.exists()){
				/*if(!fileService.canRead(file, SessionManager.getSessionUser())){
					getResponse().sendError(403);
				}*/
				HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
			    headers.setContentDispositionFormData("attachment", fileName);    
			    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
