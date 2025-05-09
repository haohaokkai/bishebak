const menu = {
     list() {
         return [
             // 管理员角色的菜单配置
             {
                 "backMenu": [
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-full",
                                 "buttons": ["新增", "查看", "修改", "删除"],
                                 "menu": "用户",
                                 "menuJump": "列表",
                                 "tableName": "yonghu"
                             }
                         ],
                         "menu": "用户管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-wenzi",
                                 "buttons": ["新增", "查看", "修改", "删除", "审核"],
                                 "menu": "医生",
                                 "menuJump": "列表",
                                 "tableName": "yisheng"
                             }
                         ],
                         "menu": "医生管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-keyboard",
                                 "buttons": ["新增", "查看", "修改", "删除"],
                                 "menu": "公告信息",
                                 "menuJump": "列表",
                                 "tableName": "gonggaoxinxi"
                             }
                         ],
                         "menu": "公告信息管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-attentionfavor",
                                 "buttons": ["新增", "查看", "修改", "删除"],
                                 "menu": "咨询信息",
                                 "menuJump": "列表",
                                 "tableName": "zixunxinxi"
                             }
                         ],
                         "menu": "咨询信息管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-camera",
                                 "buttons": ["新增", "查看", "修改", "删除"],
                                 "menu": "诊断记录",
                                 "menuJump": "列表",
                                 "tableName": "zhenduanjilu"
                             }
                         ],
                         "menu": "诊断记录管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-copy",
                                 "buttons": ["新增", "查看", "修改", "删除"],
                                 "menu": "测试模板管理",
                                 "tableName": "exampaper"
                             }
                         ],
                         "menu": "测试模板管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-camera",
                                 "buttons": ["新增", "查看", "修改", "删除"],
                                 "menu": "试题管理",
                                 "tableName": "examquestion"
                             }
                         ],
                         "menu": "试题管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-explore",
                                 "buttons": ["新增", "查看", "修改", "删除"],
                                 "menu": "轮播图管理",
                                 "tableName": "config"
                             }
                         ],
                         "menu": "系统管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-baby",
                                 "buttons": ["查看", "删除"],
                                 "menu": "测试模板列表",
                                 "tableName": "exampaperlist"
                             },
                             {
                                 "appFrontIcon": "cuIcon-send",
                                 "buttons": ["查看", "删除"],
                                 "menu": "考试记录",
                                 "tableName": "examrecord"
                             },
                             {
                                 "appFrontIcon": "cuIcon-brand",
                                 "buttons": ["查看", "删除"],
                                 "menu": "错题本",
                                 "tableName": "examfailrecord"
                             }
                         ],
                         "menu": "考试管理"
                     }
                 ],
                 "frontMenu": [
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-album",
                                 "buttons": ["咨询", "查看"],
                                 "menu": "医生列表",
                                 "menuJump": "列表",
                                 "tableName": "yisheng"
                             }
                         ],
                         "menu": "医生模块"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-pay",
                                 "buttons": ["查看"],
                                 "menu": "公告信息列表",
                                 "menuJump": "列表",
                                 "tableName": "gonggaoxinxi"
                             }
                         ],
                         "menu": "公告信息模块"
                     }
                 ],
                 "hasBackLogin": "是",
                 "hasBackRegister": "否",
                 "hasFrontLogin": "否",
                 "hasFrontRegister": "否",
                 "roleName": "管理员",
                 "tableName": "users"
             },
             // 用户角色的菜单配置
             {
                 "backMenu": [
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-attentionfavor",
                                 "buttons": ["查看", "删除"],
                                 "menu": "咨询信息",
                                 "menuJump": "列表",
                                 "tableName": "zixunxinxi"
                             }
                         ],
                         "menu": "咨询信息管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-camera",
                                 "buttons": ["查看"],
                                 "menu": "诊断记录",
                                 "menuJump": "列表",
                                 "tableName": "zhenduanjilu"
                             }
                         ],
                         "menu": "诊断记录管理"
                     }
                 ],
                 "frontMenu": [
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-album",
                                 "buttons": ["咨询", "查看"],
                                 "menu": "医生列表",
                                 "menuJump": "列表",
                                 "tableName": "yisheng"
                             }
                         ],
                         "menu": "医生模块"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-pay",
                                 "buttons": ["查看"],
                                 "menu": "公告信息列表",
                                 "menuJump": "列表",
                                 "tableName": "gonggaoxinxi"
                             }
                         ],
                         "menu": "公告信息模块"
                     }
                 ],
                 "hasBackLogin": "否",
                 "hasBackRegister": "是",
                 "hasFrontLogin": "否",
                 "hasFrontRegister": "否",
                 "roleName": "用户",
                 "tableName": "yonghu"
             },
             // 医生角色的菜单配置
             {
                 "backMenu": [
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-keyboard",
                                 "buttons": ["查看"],
                                 "menu": "公告信息",
                                 "menuJump": "列表",
                                 "tableName": "gonggaoxinxi"
                             }
                         ],
                         "menu": "公告信息管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-attentionfavor",
                                 "buttons": ["查看", "删除", "审核", "诊断"],
                                 "menu": "咨询信息",
                                 "menuJump": "列表",
                                 "tableName": "zixunxinxi"
                             }
                         ],
                         "menu": "咨询信息管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-camera",
                                 "buttons": ["查看", "新增", "修改", "删除"],
                                 "menu": "诊断记录",
                                 "menuJump": "列表",
                                 "tableName": "zhenduanjilu"
                             }
                         ],
                         "menu": "诊断记录管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-time",
                                 "buttons": ["查看", "新增", "修改", "删除"],
                                 "menu": "我的排班",
                                 "menuJump": "列表",
                                 "tableName": "schedule"
                             }
                         ],
                         "menu": "排班管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-form",
                                 "buttons": ["查看", "修改", "删除"],
                                 "menu": "预约记录",
                                 "menuJump": "列表",
                                 "tableName": "appointment"
                             }
                         ],
                         "menu": "预约管理"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-rank",
                                 "buttons": ["查看", "下载"],
                                 "menu": "患者答题记录",
                                 "menuJump": "列表",
                                 "tableName": "examrecord"
                             }
                         ],
                         "menu": "患者答题管理"
                     }
                 ],
                 "frontMenu": [
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-album",
                                 "buttons": ["咨询", "查看"],
                                 "menu": "医生列表",
                                 "menuJump": "列表",
                                 "tableName": "yisheng"
                             }
                         ],
                         "menu": "医生模块"
                     },
                     {
                         "child": [
                             {
                                 "appFrontIcon": "cuIcon-pay",
                                 "buttons": ["查看"],
                                 "menu": "公告信息列表",
                                 "menuJump": "列表",
                                 "tableName": "gonggaoxinxi"
                             }
                         ],
                         "menu": "公告信息模块"
                     }
                 ],
                 "hasBackLogin": "是",
                 "hasBackRegister": "是",
                 "hasFrontLogin": "否",
                 "hasFrontRegister": "否",
                 "roleName": "医生",
                 "tableName": "yisheng"
             }
         ];
     }
 };
export default menu;
