<template>
  <div class="main-content">
    <!-- 列表页 -->
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row  :gutter="20" class="slt" :style="{justifyContent:contents.searchBoxPosition=='1'?'flex-start':contents.searchBoxPosition=='2'?'center':'flex-end'}">
                <el-form-item :label="contents.inputTitle == 1 ? '医生账号' : ''">
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 1" prefix-icon="el-icon-search" v-model="searchForm.yishengzhanghao" placeholder="医生账号" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 2" suffix-icon="el-icon-search" v-model="searchForm.yishengzhanghao" placeholder="医生账号" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 0" v-model="searchForm.yishengzhanghao" placeholder="医生账号" clearable></el-input>
                </el-form-item>
                <el-form-item :label="contents.inputTitle == 1 ? '医生姓名' : ''">
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 1" prefix-icon="el-icon-search" v-model="searchForm.yishengxingming" placeholder="医生姓名" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 1 && contents.inputIconPosition == 2" suffix-icon="el-icon-search" v-model="searchForm.yishengxingming" placeholder="医生姓名" clearable></el-input>
                  <el-input v-if="contents.inputIcon == 0" v-model="searchForm.yishengxingming" placeholder="医生姓名" clearable></el-input>
                </el-form-item>
          <el-form-item>
            <el-button v-if="contents.searchBtnIcon == 1 && contents.searchBtnIconPosition == 1" icon="el-icon-search" type="success" @click="search()">{{ contents.searchBtnFont == 1?'查询':'' }}</el-button>
            <el-button v-if="contents.searchBtnIcon == 1 && contents.searchBtnIconPosition == 2" type="success" @click="search()">{{ contents.searchBtnFont == 1?'查询':'' }}<i class="el-icon-search el-icon--right"/></el-button>
            <el-button v-if="contents.searchBtnIcon == 0" type="success" @click="search()">{{ contents.searchBtnFont == 1?'查询':'' }}</el-button>
          </el-form-item>
        </el-row>

        <el-row class="ad" :style="{justifyContent:contents.btnAdAllBoxPosition=='1'?'flex-start':contents.btnAdAllBoxPosition=='2'?'center':'flex-end'}">
          <el-form-item>
            <el-button
              v-if="isAuth('yisheng','新增') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 1"
              type="success"
              icon="el-icon-plus"
              @click="addOrUpdateHandler()"
            >{{ contents.btnAdAllFont == 1?'新增':'' }}</el-button>
            <el-button
              v-if="isAuth('yisheng','新增') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 2"
              type="success"
              @click="addOrUpdateHandler()"
            >{{ contents.btnAdAllFont == 1?'新增':'' }}<i class="el-icon-plus el-icon--right" /></el-button>
            <el-button
              v-if="isAuth('yisheng','新增') && contents.btnAdAllIcon == 0"
              type="success"
              @click="addOrUpdateHandler()"
            >{{ contents.btnAdAllFont == 1?'新增':'' }}</el-button>
            <el-button
              v-if="isAuth('yisheng','删除') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 1 && contents.tableSelection"
              :disabled="dataListSelections.length <= 0"
              type="danger"
              icon="el-icon-delete"
              @click="deleteHandler()"
            >{{ contents.btnAdAllFont == 1?'删除':'' }}</el-button>
            <el-button
              v-if="isAuth('yisheng','删除') && contents.btnAdAllIcon == 1 && contents.btnAdAllIconPosition == 2 && contents.tableSelection"
              :disabled="dataListSelections.length <= 0"
              type="danger"
              @click="deleteHandler()"
            >{{ contents.btnAdAllFont == 1?'删除':'' }}<i class="el-icon-delete el-icon--right" /></el-button>
            <el-button
              v-if="isAuth('yisheng','删除') && contents.btnAdAllIcon == 0 && contents.tableSelection"
              :disabled="dataListSelections.length <= 0"
              type="danger"
              @click="deleteHandler()"
            >{{ contents.btnAdAllFont == 1?'删除':'' }}</el-button>





          </el-form-item>
        </el-row>
      </el-form>
      <div class="table-content">
        <el-table class="tables" :size="contents.tableSize" :show-header="contents.tableShowHeader"
            :header-row-style="headerRowStyle" :header-cell-style="headerCellStyle"
            :border="contents.tableBorder"
            :fit="contents.tableFit"
            :stripe="contents.tableStripe"
            :style="{width: '100%',fontSize:contents.tableContentFontSize,color:contents.tableContentFontColor}"
            v-if="isAuth('yisheng','查看')"
            :data="dataList"
            v-loading="dataListLoading"
            @selection-change="selectionChangeHandler">
            <el-table-column  v-if="contents.tableSelection"
                type="selection"
                :header-align="contents.tableAlign"
                align="center"
                width="50">
            </el-table-column>
            <el-table-column label="索引" :align="contents.tableAlign"  v-if="contents.tableIndex" type="index" width="50" />
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="yishengzhanghao"
                   :header-align="contents.tableAlign"
		    label="医生账号">
		     <template slot-scope="scope">
                       {{scope.row.yishengzhanghao}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="yishengxingming"
                   :header-align="contents.tableAlign"
		    label="医生姓名">
		     <template slot-scope="scope">
                       {{scope.row.yishengxingming}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="xingbie"
                   :header-align="contents.tableAlign"
		    label="性别">
		     <template slot-scope="scope">
                       {{scope.row.xingbie}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="shoujihao"
                   :header-align="contents.tableAlign"
		    label="手机号">
		     <template slot-scope="scope">
                       {{scope.row.shoujihao}}
                     </template>
                </el-table-column>
                <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign" 
                    prop="yishengzhiwei"
                   :header-align="contents.tableAlign"
		    label="医生职位">
		     <template slot-scope="scope">
                       {{scope.row.yishengzhiwei}}
                     </template>
                </el-table-column>
                  <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign"  prop="touxiang"
                   :header-align="contents.tableAlign"
                    width="200"
                    label="头像">
                    <template slot-scope="scope">
                      <div v-if="scope.row.touxiang">
                        <img :src="$base.url+scope.row.touxiang.split(',')[0]" width="100" height="100">
                      </div>
                      <div v-else>无图片</div>
                    </template>
                  </el-table-column>
              <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" 
                  prop="shhf"
                 :header-align="contents.tableAlign"
                  label="审核回复">
              </el-table-column>
              <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" 
                  prop="sfsh"
                 :header-align="contents.tableAlign"
                  label="审核状态">
                  <template slot-scope="scope">
                    <span style="margin-right:10px">{{scope.row.sfsh=='是'?'通过':'未通过'}}</span>
                  </template>
              </el-table-column>
              <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" 
                  v-if="isAuth('yisheng','审核')"
                  prop="sfsh"
                 :header-align="contents.tableAlign"
                  label="审核">
                  <template slot-scope="scope">
                    <el-button  type="text" icon="el-icon-edit" size="small" @click="shDialog(scope.row)">审核</el-button>
                  </template>
              </el-table-column>
            <el-table-column width="300" :align="contents.tableAlign" 
               :header-align="contents.tableAlign"
                label="操作">
                <template slot-scope="scope">
                <el-button v-if="isAuth('yisheng','查看') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 1" type="success" icon="el-icon-tickets" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">{{ contents.tableBtnFont == 1?'详情':'' }}</el-button>
                <el-button v-if="isAuth('yisheng','查看') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 2" type="success" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">{{ contents.tableBtnFont == 1?'详情':'' }}<i class="el-icon-tickets el-icon--right" /></el-button>
                <el-button v-if="isAuth('yisheng','查看') && contents.tableBtnIcon == 0" type="success" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">{{ contents.tableBtnFont == 1?'详情':'' }}</el-button>
                <el-button v-if="isAuth('yisheng','咨询') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 1" type="success" icon="el-icon-tickets" size="mini" @click="zixunxinxiCrossAddOrUpdateHandler(scope.row,'cross','是','','')">{{ contents.tableBtnFont == 1?'咨询':'' }}</el-button>
                <el-button v-if="isAuth('yisheng','咨询') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 2" type="success" size="mini" @click="zixunxinxiCrossAddOrUpdateHandler(scope.row,'cross','是','','')">{{ contents.tableBtnFont == 1?'咨询':'' }}<i class="el-icon-tickets el-icon--right" /></el-button>
                <el-button v-if="isAuth('yisheng','咨询') && contents.tableBtnIcon == 0" type="success" size="mini" @click="zixunxinxiCrossAddOrUpdateHandler(scope.row,'cross','是','','')">{{ contents.tableBtnFont == 1?'咨询':'' }}</el-button>
                <el-button v-if=" isAuth('yisheng','修改') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 1" type="primary" icon="el-icon-edit" size="mini" @click="addOrUpdateHandler(scope.row.id)">{{ contents.tableBtnFont == 1?'修改':'' }}</el-button>
                <el-button v-if=" isAuth('yisheng','修改') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 2" type="primary" size="mini" @click="addOrUpdateHandler(scope.row.id)">{{ contents.tableBtnFont == 1?'修改':'' }}<i class="el-icon-edit el-icon--right" /></el-button>
                <el-button v-if=" isAuth('yisheng','修改') && contents.tableBtnIcon == 0" type="primary" size="mini" @click="addOrUpdateHandler(scope.row.id)">{{ contents.tableBtnFont == 1?'修改':'' }}</el-button>




                <el-button v-if="isAuth('yisheng','删除') && contents.tableBtnIcon == 1 && contents.tableBtnIconPosition == 2" type="danger" size="mini" @click="deleteHandler(scope.row.id)">{{ contents.tableBtnFont == 1?'删除':'' }}<i class="el-icon-delete el-icon--right" /></el-button>
                <el-button v-if="isAuth('yisheng','删除') && contents.tableBtnIcon == 0" type="danger" size="mini" @click="deleteHandler(scope.row.id)">{{ contents.tableBtnFont == 1?'删除':'' }}</el-button>
                <el-button type="primary" icon="el-icon-date" size="mini" @click="scheduleManageHandler(scope.row)">预约管理</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
          clsss="pages"
          :layout="layouts"
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="Number(contents.pageEachNum)"
          :total="totalPage"
          :small="contents.pageStyle"
          class="pagination-content"
          :background="contents.pageBtnBG"
          :style="{textAlign:contents.pagePosition==1?'left':contents.pagePosition==2?'center':'right'}"
        ></el-pagination>
      </div>
    </div>
    <!-- 添加/修改页面  将父组件的search方法传递给子组件-->
    <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>

    <zixunxinxi-cross-add-or-update v-if="zixunxinxiCrossAddOrUpdateFlag" :parent="this" ref="zixunxinxiCrossaddOrUpdate"></zixunxinxi-cross-add-or-update>

    <el-dialog
      title="审核"
      :visible.sync="sfshVisiable"
      width="50%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="审核状态">
          <el-select v-model="shForm.sfsh" placeholder="审核状态">
            <el-option label="通过" value="是"></el-option>
            <el-option label="不通过" value="否"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" :rows="8" v-model="shForm.shhf"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="shDialog">取 消</el-button>
        <el-button type="primary" @click="shHandler">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 预约管理对话框 -->
    <el-dialog
      title="预约管理"
      :visible.sync="scheduleVisible"
      width="60%">
      <div v-if="scheduleVisible">
        <el-tabs v-model="scheduleTab">
          <el-tab-pane label="排班管理" name="schedule">
            <el-form :inline="true" :model="scheduleForm">
              <el-form-item label="工作日期">
                <el-date-picker
                  v-model="scheduleForm.workDate"
                  type="date"
                  placeholder="选择日期"
                  :picker-options="{ disabledDate: (time) => time.getTime() < Date.now() - 8.64e7 }"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="loadSchedules">查询</el-button>
                <el-button type="success" @click="addScheduleSlot">添加时间段</el-button>
              </el-form-item>
            </el-form>
            
            <el-table :data="scheduleList" border style="width: 100%" v-loading="scheduleLoading">
              <el-table-column prop="workDate" label="工作日期" width="120"></el-table-column>
              <el-table-column prop="timeSlot" label="时间段" width="120"></el-table-column>
              <el-table-column prop="maxPatients" label="最大预约人数" width="120"></el-table-column>
              <el-table-column prop="bookedCount" label="已预约人数" width="120"></el-table-column>
              <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                  <el-button type="warning" size="mini" @click="editScheduleSlot(scope.row)">编辑</el-button>
                  <el-button type="danger" size="mini" @click="deleteScheduleSlot(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          
          <el-tab-pane label="预约记录" name="appointment">
            <el-form :inline="true" :model="appointmentForm">
              <el-form-item label="预约日期">
                <el-date-picker
                  v-model="appointmentForm.appointDate"
                  type="date"
                  placeholder="选择日期"
                  value-format="yyyy-MM-dd">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="appointmentForm.status" placeholder="预约状态">
                  <el-option label="全部" value=""></el-option>
                  <el-option label="待确认" value="PENDING"></el-option>
                  <el-option label="已确认" value="CONFIRMED"></el-option>
                  <el-option label="已取消" value="CANCELED"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="loadAppointments">查询</el-button>
              </el-form-item>
            </el-form>
            
            <el-table :data="appointmentList" border style="width: 100%" v-loading="appointmentLoading">
              <el-table-column prop="appointDate" label="预约日期" width="120"></el-table-column>
              <el-table-column prop="timeSlot" label="时间段" width="120"></el-table-column>
              <el-table-column prop="userId" label="用户ID" width="100"></el-table-column>
              <el-table-column prop="userName" label="用户姓名" width="120"></el-table-column>
              <el-table-column prop="status" label="状态" width="100">
                <template slot-scope="scope">
                  <el-tag :type="scope.row.status === 'CONFIRMED' ? 'success' : scope.row.status === 'PENDING' ? 'warning' : 'info'">
                    {{ scope.row.status === 'CONFIRMED' ? '已确认' : scope.row.status === 'PENDING' ? '待确认' : '已取消' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                  <el-button 
                    v-if="scope.row.status === 'PENDING'" 
                    type="success" 
                    size="mini" 
                    @click="updateAppointmentStatus(scope.row.id, 'CONFIRMED')">确认
                  </el-button>
                  <el-button 
                    v-if="scope.row.status === 'PENDING'" 
                    type="danger" 
                    size="mini" 
                    @click="updateAppointmentStatus(scope.row.id, 'CANCELED')">取消
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
      
      <!-- 添加/编辑时间段对话框 -->
      <el-dialog
        :title="scheduleDialogType === 'add' ? '添加时间段' : '编辑时间段'"
        :visible.sync="scheduleDialogVisible"
        width="40%"
        append-to-body>
        <el-form :model="scheduleSlotForm" :rules="scheduleSlotRules" ref="scheduleSlotForm" label-width="100px">
          <el-form-item label="工作日期" prop="workDate">
            <el-date-picker
              v-model="scheduleSlotForm.workDate"
              type="date"
              placeholder="选择日期"
              :picker-options="{ disabledDate: (time) => time.getTime() < Date.now() - 8.64e7 }"
              value-format="yyyy-MM-dd"
              style="width: 100%">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="时间段" prop="timeSlot">
            <el-select v-model="scheduleSlotForm.timeSlot" placeholder="请选择时间段" style="width: 100%">
              <el-option label="08:00-09:00" value="08:00-09:00"></el-option>
              <el-option label="09:00-10:00" value="09:00-10:00"></el-option>
              <el-option label="10:00-11:00" value="10:00-11:00"></el-option>
              <el-option label="11:00-12:00" value="11:00-12:00"></el-option>
              <el-option label="14:00-15:00" value="14:00-15:00"></el-option>
              <el-option label="15:00-16:00" value="15:00-16:00"></el-option>
              <el-option label="16:00-17:00" value="16:00-17:00"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="最大预约人数" prop="maxPatients">
            <el-input-number v-model="scheduleSlotForm.maxPatients" :min="1" :max="50" style="width: 100%"></el-input-number>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="scheduleDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitScheduleSlot">确 定</el-button>
        </span>
      </el-dialog>
    </el-dialog>

  </div>
</template>
<script>
import axios from 'axios'
import AddOrUpdate from "./add-or-update";
import zixunxinxiCrossAddOrUpdate from "../zixunxinxi/add-or-update";
export default {
  data() {
    return {
      searchForm: {
        key: ""
      },
      form:{},
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      sfshVisiable: false,
      shForm: {},
      chartVisiable: false,
      addOrUpdateFlag:false,
      zixunxinxiCrossAddOrUpdateFlag: false,
      // 预约管理相关
      scheduleVisible: false,
      scheduleTab: 'schedule',
      currentDoctor: null,
      scheduleForm: {
        workDate: new Date().toISOString().split('T')[0]
      },
      scheduleList: [],
      scheduleLoading: false,
      appointmentForm: {
        appointDate: new Date().toISOString().split('T')[0],
        status: ''
      },
      appointmentList: [],
      appointmentLoading: false,
      scheduleDialogVisible: false,
      scheduleDialogType: 'add',
      scheduleSlotForm: {
        workDate: '',
        timeSlot: '',
        maxPatients: 10
      },
      scheduleSlotRules: {
        workDate: [
          { required: true, message: '请选择工作日期', trigger: 'change' }
        ],
        timeSlot: [
          { required: true, message: '请选择时间段', trigger: 'change' }
        ],
        maxPatients: [
          { required: true, message: '请输入最大预约人数', trigger: 'blur' }
        ]
      },
      contents:{"searchBtnFontColor":"rgba(0, 0, 0, 1)","pagePosition":"2","inputFontSize":"14px","inputBorderRadius":"4px","tableBtnDelFontColor":"rgba(109, 154, 212, 0.8)","tableBtnIconPosition":"1","searchBtnHeight":"30px","tableBgColor":"rgba(255, 255, 255, 1)","inputIconColor":"#C0C4CC","searchBtnBorderRadius":"4px","tableStripe":false,"btnAdAllWarnFontColor":"#333","tableBtnDelBgColor":"#fff","searchBtnIcon":"1","tableSize":"medium","searchBtnBorderStyle":"solid","text":{"padding":"0","boxShadow":"0 0 0px rgba(0,0,0,.1)","margin":"20% auto 0","borderColor":"rgba(0,0,0,.3)","backgroundColor":"rgba(247, 247, 247, 0)","color":"#333","borderRadius":"6px","borderWidth":"0","width":"auto","lineHeight":"auto","fontSize":"50px","borderStyle":"solid"},"tableSelection":true,"searchBtnBorderWidth":"0 0 2px 0","tableContentFontSize":"14px","searchBtnBgColor":"rgba(109, 154, 212, 0.8)","inputTitleSize":"14px","btnAdAllBorderColor":"rgba(109, 154, 212, 0.8)","pageJumper":true,"btnAdAllIconPosition":"1","searchBoxPosition":"1","tableBtnDetailFontColor":"rgba(109, 154, 212, 0.8)","tableBtnHeight":"40px","pagePager":true,"searchBtnBorderColor":"rgba(0, 0, 0, 1)","tableHeaderFontColor":"rgba(0, 0, 0, 1)","inputTitle":"1","tableBtnBorderRadius":"40px","btnAdAllFont":"1","btnAdAllDelFontColor":"#333","tableBtnIcon":"1","btnAdAllHeight":"40px","btnAdAllWarnBgColor":"#fff","btnAdAllBorderWidth":"3px","tableStripeFontColor":"#606266","tableBtnBorderStyle":"solid","inputHeight":"30px","btnAdAllBorderRadius":"40px","btnAdAllDelBgColor":"#fff","pagePrevNext":true,"btnAdAllAddBgColor":"#fff","searchBtnFont":"1","tableIndex":true,"btnAdAllIcon":"1","tableSortable":false,"pageSizes":true,"tableFit":true,"pageBtnBG":false,"searchBtnFontSize":"14px","tableBtnEditBgColor":"#fff","box":{"padding":"10px","boxShadow":"0 0 6px rgba(0,0,0,0)","flag":"2","backgroundImage":"","background":"#fff"},"inputBorderWidth":"0 0 2px 0","inputFontPosition":"1","inputFontColor":"rgba(0, 0, 0, 1)","pageEachNum":10,"tableHeaderBgColor":"rgba(109, 154, 212, 0.8)","inputTitleColor":"#333","btnAdAllBoxPosition":"1","tableBtnDetailBgColor":"#fff","inputIcon":"0","searchBtnIconPosition":"1","btnAdAllFontSize":"14px","inputBorderStyle":"solid","tableHoverFontColor":"#333","inputBgColor":"rgba(109, 154, 212, 0.8)","pageStyle":true,"pageTotal":true,"btnAdAllAddFontColor":"#333","tableBtnFont":"1","tableContentFontColor":"#606266","inputBorderColor":"rgba(0, 0, 0, 1)","tableShowHeader":true,"tableHoverBgColor":"rgba(255, 255, 255, 1)","tableBtnFontSize":"14px","tableBtnBorderColor":"rgba(109, 154, 212, 0.8)","inputIconPosition":"1","tableBorder":true,"btnAdAllBorderStyle":"solid","tableBtnBorderWidth":"3px","tableStripeBgColor":"#F5F7FA","tableBtnEditFontColor":"rgba(109, 154, 212, 0.8)","tableAlign":"center"},
      layouts: '',


    };
  },
  created() {
    this.init();
    this.getDataList();
    this.contentStyleChange()
  },
  mounted() {

  },
  filters: {
    htmlfilter: function (val) {
      return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
    }
  },
  components: {
    AddOrUpdate,
    zixunxinxiCrossAddOrUpdate,
  },
  methods: {

    contentStyleChange() {
      this.contentSearchStyleChange()
      this.contentBtnAdAllStyleChange()
      this.contentSearchBtnStyleChange()
      this.contentTableBtnStyleChange()
      this.contentPageStyleChange()
    },
    contentSearchStyleChange() {
      this.$nextTick(()=>{
        document.querySelectorAll('.form-content .slt .el-input__inner').forEach(el=>{
          let textAlign = 'left'
          if(this.contents.inputFontPosition == 2) textAlign = 'center'
          if(this.contents.inputFontPosition == 3) textAlign = 'right'
          el.style.textAlign = textAlign
          el.style.height = this.contents.inputHeight
          el.style.lineHeight = this.contents.inputHeight
          el.style.color = this.contents.inputFontColor
          el.style.fontSize = this.contents.inputFontSize
          el.style.borderWidth = this.contents.inputBorderWidth
          el.style.borderStyle = this.contents.inputBorderStyle
          el.style.borderColor = this.contents.inputBorderColor
          el.style.borderRadius = this.contents.inputBorderRadius
          el.style.backgroundColor = this.contents.inputBgColor
        })
        if(this.contents.inputTitle) {
          document.querySelectorAll('.form-content .slt .el-form-item__label').forEach(el=>{
            el.style.color = this.contents.inputTitleColor
            el.style.fontSize = this.contents.inputTitleSize
            el.style.lineHeight = this.contents.inputHeight
          })
        }
        setTimeout(()=>{
          document.querySelectorAll('.form-content .slt .el-input__prefix').forEach(el=>{
            el.style.color = this.contents.inputIconColor
            el.style.lineHeight = this.contents.inputHeight
          })
          document.querySelectorAll('.form-content .slt .el-input__suffix').forEach(el=>{
            el.style.color = this.contents.inputIconColor
            el.style.lineHeight = this.contents.inputHeight
          })
          document.querySelectorAll('.form-content .slt .el-input__icon').forEach(el=>{
            el.style.lineHeight = this.contents.inputHeight
          })
        },10)

      })
    },
    // 搜索按钮
    contentSearchBtnStyleChange() {
      this.$nextTick(()=>{
        document.querySelectorAll('.form-content .slt .el-button--success').forEach(el=>{
          el.style.height = this.contents.searchBtnHeight
          el.style.color = this.contents.searchBtnFontColor
          el.style.fontSize = this.contents.searchBtnFontSize
          el.style.borderWidth = this.contents.searchBtnBorderWidth
          el.style.borderStyle = this.contents.searchBtnBorderStyle
          el.style.borderColor = this.contents.searchBtnBorderColor
          el.style.borderRadius = this.contents.searchBtnBorderRadius
          el.style.backgroundColor = this.contents.searchBtnBgColor
        })
      })
    },
    // 新增、批量删除
    contentBtnAdAllStyleChange() {
      this.$nextTick(()=>{
        document.querySelectorAll('.form-content .ad .el-button--success').forEach(el=>{
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllAddFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllAddBgColor
        })
        document.querySelectorAll('.form-content .ad .el-button--danger').forEach(el=>{
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllDelFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllDelBgColor
        })
        document.querySelectorAll('.form-content .ad .el-button--warning').forEach(el=>{
          el.style.height = this.contents.btnAdAllHeight
          el.style.color = this.contents.btnAdAllWarnFontColor
          el.style.fontSize = this.contents.btnAdAllFontSize
          el.style.borderWidth = this.contents.btnAdAllBorderWidth
          el.style.borderStyle = this.contents.btnAdAllBorderStyle
          el.style.borderColor = this.contents.btnAdAllBorderColor
          el.style.borderRadius = this.contents.btnAdAllBorderRadius
          el.style.backgroundColor = this.contents.btnAdAllWarnBgColor
        })
      })
    },
    // 表格
    // rowStyle({ row, rowIndex}) {
    //   if (rowIndex % 2 == 1) {
    //     if(this.contents.tableStripe) {
    //       return {color:this.contents.tableStripeFontColor}
    //     }
    //   } else {
    //     return ''
    //   }
    // },
    // cellStyle({ row, rowIndex}){
    //   if (rowIndex % 2 == 1) {
    //     if(this.contents.tableStripe) {
    //       return {backgroundColor:this.contents.tableStripeBgColor}
    //     }
    //   } else {
    //     return ''
    //   }
    // },
    headerRowStyle({ row, rowIndex}){
      return {color: this.contents.tableHeaderFontColor}
    },
    headerCellStyle({ row, rowIndex}){
      return {backgroundColor: this.contents.tableHeaderBgColor}
    },
    // 表格按钮
    contentTableBtnStyleChange(){
      // this.$nextTick(()=>{
      //   setTimeout(()=>{
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--success').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnDetailFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnDetailBgColor
      //     })
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--primary').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnEditFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnEditBgColor
      //     })
      //     document.querySelectorAll('.table-content .tables .el-table__body .el-button--danger').forEach(el=>{
      //       el.style.height = this.contents.tableBtnHeight
      //       el.style.color = this.contents.tableBtnDelFontColor
      //       el.style.fontSize = this.contents.tableBtnFontSize
      //       el.style.borderWidth = this.contents.tableBtnBorderWidth
      //       el.style.borderStyle = this.contents.tableBtnBorderStyle
      //       el.style.borderColor = this.contents.tableBtnBorderColor
      //       el.style.borderRadius = this.contents.tableBtnBorderRadius
      //       el.style.backgroundColor = this.contents.tableBtnDelBgColor
      //     })

      //   }, 50)
      // })
    },
    // 分页
    contentPageStyleChange(){
      let arr = []

      if(this.contents.pageTotal) arr.push('total')
      if(this.contents.pageSizes) arr.push('sizes')
      if(this.contents.pagePrevNext){
        arr.push('prev')
        if(this.contents.pagePager) arr.push('pager')
        arr.push('next')
      }
      if(this.contents.pageJumper) arr.push('jumper')
      this.layouts = arr.join()
      this.contents.pageEachNum = 10
    },

    zixunxinxiCrossAddOrUpdateHandler(row,type,crossOptAudit,statusColumnName,tips,statusColumnValue){
	if(crossOptAudit=='是'&&row.sfsh!='是') {
	    this.$message({
	      message: "请审核通过后再操作",
	      type: "success",
	      duration: 1500,
	      onClose: () => {
	      }
	    });
		return
	}
      this.showFlag = false;
      this.addOrUpdateFlag = false;
      this.zixunxinxiCrossAddOrUpdateFlag = true;
      this.$storage.set('crossObj',row);
      this.$storage.set('crossTable','yisheng');
      this.$storage.set('statusColumnName',statusColumnName);
      this.$storage.set('statusColumnValue',statusColumnValue);
      this.$storage.set('tips',tips);
	if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
		var obj = this.$storage.getObj('crossObj');
		for (var o in obj){
		  if(o==statusColumnName && obj[o]==statusColumnValue){
		    this.$message({
		      message: tips,
		      type: "success",
		      duration: 1500,
		      onClose: () => {
			this.getDataList();
		      }
		    });
		      this.showFlag = true;
		      this.zixunxinxiCrossAddOrUpdateFlag = false;
			return;
		  }
		}
	}
      this.$nextTick(() => {
      this.$refs.zixunxinxiCrossaddOrUpdate.init(row.id,type);
      });
    },
    init () {
    },
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },

    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: 'id',
      }
          if(this.searchForm.yishengzhanghao!='' && this.searchForm.yishengzhanghao!=undefined){
            params['yishengzhanghao'] = '%' + this.searchForm.yishengzhanghao + '%'
          }
          if(this.searchForm.yishengxingming!='' && this.searchForm.yishengxingming!=undefined){
            params['yishengxingming'] = '%' + this.searchForm.yishengxingming + '%'
          }
      this.$http({
        url: "yisheng/page",
        method: "get",
        params: params
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.total;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },
    // 添加/修改
    addOrUpdateHandler(id,type) {
      this.showFlag = false;
      this.addOrUpdateFlag = true;
      this.crossAddOrUpdateFlag = false;
      if(type!='info'){
        type = 'else';
      }
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id,type);
      });
    },
    // 查看评论
    // 审核窗口
    shDialog(row){
      this.sfshVisiable = !this.sfshVisiable;
      if(row){
        this.shForm = {
          yishengzhanghao: row.yishengzhanghao,
          yishengxingming: row.yishengxingming,
          mima: row.mima,
          xingbie: row.xingbie,
          shoujihao: row.shoujihao,
          yishengzhiwei: row.yishengzhiwei,
          yishengjianjie: row.yishengjianjie,
          touxiang: row.touxiang,
          sfsh: row.sfsh,
          shhf: row.shhf,
          id: row.id
        }
      }
    },
    // 审核
    shHandler(){
      this.$confirm(`确定操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: "yisheng/update",
          method: "post",
          data: this.shForm
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
                this.shDialog()
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 下载
    download(file){
      window.open(`${file}`)
    },
    // 删除
    deleteHandler(id) {
      var ids = id
        ? [Number(id)]
        : this.dataListSelections.map(item => {
            return Number(item.id);
          });
      this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: "yisheng/delete",
          method: "post",
          data: ids
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.search();
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 预约管理按钮点击处理
    scheduleManageHandler(row) {
      this.currentDoctor = row;
      this.scheduleVisible = true;
      this.scheduleTab = 'schedule';
      this.scheduleForm.workDate = new Date().toISOString().split('T')[0];
      this.loadSchedules();
    },
    
    // 加载排班信息
    loadSchedules() {
      if (!this.currentDoctor) return;
      
      this.scheduleLoading = true;
      this.$http({
        url: "schedule/page",
        method: "get",
        params: {
          page: 1,
          limit: 100,
          doctorId: this.currentDoctor.id,
          workDate: this.scheduleForm.workDate
        }
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.scheduleList = data.data.list;
        } else {
          this.scheduleList = [];
          this.$message.error(data.msg || '获取排班信息失败');
        }
        this.scheduleLoading = false;
      }).catch(() => {
        this.scheduleLoading = false;
        this.$message.error('获取排班信息请求失败');
      });
    },
    
    // 加载预约记录
    loadAppointments() {
      if (!this.currentDoctor) return;
      
      this.appointmentLoading = true;
      let params = {
        page: 1,
        limit: 100,
        doctorId: this.currentDoctor.id
      };
      
      if (this.appointmentForm.appointDate) {
        params.appointDate = this.appointmentForm.appointDate;
      }
      
      if (this.appointmentForm.status) {
        params.status = this.appointmentForm.status;
      }
      
      this.$http({
        url: "appointment/page",
        method: "get",
        params: params
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.appointmentList = data.data.list;
        } else {
          this.appointmentList = [];
          this.$message.error(data.msg || '获取预约记录失败');
        }
        this.appointmentLoading = false;
      }).catch(() => {
        this.appointmentLoading = false;
        this.$message.error('获取预约记录请求失败');
      });
    },
    
    // 添加时间段
    addScheduleSlot() {
      this.scheduleDialogType = 'add';
      this.scheduleSlotForm = {
        workDate: this.scheduleForm.workDate,
        timeSlot: '',
        maxPatients: 10
      };
      this.scheduleDialogVisible = true;
    },
    
    // 编辑时间段
    editScheduleSlot(row) {
      this.scheduleDialogType = 'edit';
      this.scheduleSlotForm = {
        id: row.id,
        workDate: row.workDate,
        timeSlot: row.timeSlot,
        maxPatients: row.maxPatients,
        version: row.version
      };
      this.scheduleDialogVisible = true;
    },
    
    // 删除时间段
    deleteScheduleSlot(row) {
      this.$confirm('确定要删除该排班时间段吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: "schedule/delete",
          method: "post",
          data: [row.id]
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.loadSchedules();
          } else {
            this.$message.error(data.msg || '删除失败');
          }
        }).catch(() => {
          this.$message.error('删除请求失败');
        });
      }).catch(() => {});
    },
    
    // 提交排班时间段
    submitScheduleSlot() {
      this.$refs.scheduleSlotForm.validate((valid) => {
        if (valid) {
          const data = {
            ...this.scheduleSlotForm,
            doctorId: this.currentDoctor.id
          };
          
          const url = this.scheduleDialogType === 'add' ? 'schedule/save' : 'schedule/update';
          
          this.$http({
            url: url,
            method: "post",
            data: data
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: this.scheduleDialogType === 'add' ? '添加成功' : '更新成功',
                type: 'success'
              });
              this.scheduleDialogVisible = false;
              this.loadSchedules();
            } else {
              this.$message.error(data.msg || (this.scheduleDialogType === 'add' ? '添加失败' : '更新失败'));
            }
          }).catch(() => {
            this.$message.error(this.scheduleDialogType === 'add' ? '添加请求失败' : '更新请求失败');
          });
        }
      });
    },
    
    // 更新预约状态
    updateAppointmentStatus(id, status) {
      const statusText = status === 'CONFIRMED' ? '确认' : '取消';
      
      this.$confirm(`确定要${statusText}该预约吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: "appointment/update",
          method: "post",
          data: {
            id: id,
            status: status
          }
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$message({
              message: `${statusText}成功`,
              type: 'success'
            });
            this.loadAppointments();
          } else {
            this.$message.error(data.msg || `${statusText}失败`);
          }
        }).catch(() => {
          this.$message.error(`${statusText}请求失败`);
        });
      }).catch(() => {});
    },


  }

};
</script>
<style lang="scss" scoped>
  .slt {
    margin: 0 !important;
    display: flex;
  }

  .ad {
    margin: 0 !important;
    display: flex;
  }

  .pages {
    & ::v-deep el-pagination__sizes{
      & ::v-deep el-input__inner {
        height: 22px;
        line-height: 22px;
      }
    }
  }
  

  .el-button+.el-button {
    margin:0;
  } 

  .tables {
	& ::v-deep .el-button--success {
		height: 40px;
		color: rgba(109, 154, 212, 0.8);
		font-size: 14px;
		border-width: 3px;
		border-style: solid;
		border-color: rgba(109, 154, 212, 0.8);
		border-radius: 40px;
		background-color: #fff;
	}
	
	& ::v-deep .el-button--primary {
		height: 40px;
		color: rgba(109, 154, 212, 0.8);
		font-size: 14px;
		border-width: 3px;
		border-style: solid;
		border-color: rgba(109, 154, 212, 0.8);
		border-radius: 40px;
		background-color: #fff;
	}
	
	& ::v-deep .el-button--danger {
		height: 40px;
		color: rgba(109, 154, 212, 0.8);
		font-size: 14px;
		border-width: 3px;
		border-style: solid;
		border-color: rgba(109, 154, 212, 0.8);
		border-radius: 40px;
		background-color: #fff;
	}

    & ::v-deep .el-button {
      margin: 4px;
    }
  }
	.form-content {
		background: transparent;
	}
	.table-content {
		background: transparent;
	}
	
	.tables ::v-deep .el-table__body tr {
				background-color: rgba(255, 255, 255, 1) !important;
				color: #606266 !important;
	 }
	.tables ::v-deep .el-table__body tr.el-table__row--striped td {
	    background: transparent;
	}
	.tables ::v-deep .el-table__body tr.el-table__row--striped {
		background-color: #F5F7FA !important;
		color: #606266 !important;
	}
	
	 .tables ::v-deep .el-table__body tr:hover>td {
	   	   background-color: rgba(255, 255, 255, 1) !important;
	   	   	   color: #333 !important;
	   	 }
	 
</style>
