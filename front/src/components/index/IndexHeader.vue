<template>
	<!-- <el-header>
		<el-menu background-color="#00c292" text-color="#FFFFFF" active-text-color="#FFFFFF" mode="horizontal">
			<div class="fl title">{{this.$project.projectName}}</div>
			<div class="fr logout" style="display:flex;">
				<el-menu-item index="3">
					<div>{{this.$storage.get('role')}} {{this.$storage.get('adminName')}}</div>
				</el-menu-item>
				<el-menu-item @click="onLogout" index="2">
					<div>退出登录</div>
				</el-menu-item>
			</div>
		</el-menu>
	</el-header> -->
	<div class="navbar" :style="headerStyle">
		<div class="title-menu" :style="{justifyContent:heads.headTitleStyle=='1'?'flex-start':'center'}">
			<el-image v-if="heads.headTitleImg" class="title-img" :style="{width:heads.headTitleImgWidth,height:heads.headTitleImgHeight,boxShadow:heads.headTitleImgBoxShadow,borderRadius:heads.headTitleImgBorderRadius}" :src="heads.headTitleImgUrl" fit="cover"></el-image>
			<div class="title-name" :style="{color:heads.headFontColor,fontSize:heads.headFontSize}">{{this.$project.projectName}}</div>
		</div>
		<div class="right-menu">
			<div class="user-info" :style="{color:heads.headUserInfoFontColor,fontSize:heads.headUserInfoFontSize}">{{this.$storage.get('role')}} {{this.$storage.get('adminName')}}</div>
			<div v-if="this.$storage.get('role')!='管理员'" class="logout" :style="{color:heads.headLogoutFontColor,fontSize:heads.headLogoutFontSize}" @click="onIndexTap">退出到前台</div>
			<div class="logout" :style="{color:heads.headLogoutFontColor,fontSize:heads.headLogoutFontSize}" @click="onLogout">退出登录</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				dialogVisible: false,
				ruleForm: {},
				user: {},
				heads: {
					"headLogoutFontHoverColor": "#fff",
					"headFontSize": "24px",
					"headUserInfoFontColor": "rgba(255, 255, 255, 0.9)",
					"headBoxShadow": "0 2px 10px rgba(30, 144, 255, 0.2)",
					"headTitleImgHeight": "44px",
					"headLogoutFontHoverBgColor": "rgba(30, 144, 255, 0.3)",
					"headFontColor": "rgba(255, 255, 255, 0.9)",
					"headTitleImg": false,
					"headHeight": "80px",
					"headTitleImgBorderRadius": "22px",
					"headTitleImgUrl": "http://codegen.caihongy.cn/20201021/cc7d45d9c8164b58b18351764eba9be1.jpg",
					"headBgColor": "linear-gradient(135deg, #1E90FF, rgba(138, 174, 220, 0.8))",
					"headTitleImgBoxShadow": "0 2px 10px rgba(30, 144, 255, 0.2)",
					"headLogoutFontColor": "rgba(255, 255, 255, 0.9)",
					"headUserInfoFontSize": "16px",
					"headTitleImgWidth": "44px",
					"headTitleStyle": "2",
					"headLogoutFontSize": "16px"
				},
				menuList: [],
				dynamicMenuRoutes: [],
				role: '',
			};
		},
		created() {
			this.setHeaderStyle()
		},
		mounted() {
			let sessionTable = this.$storage.get("sessionTable")
			this.$http({
				url: sessionTable + '/session',
				method: "get"
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {
					this.user = data.data;
					this.$storage.set('userid',data.data.id);
				} else {
					let message = this.$message
					message.error(data.msg);
				}
			});
			this.role = this.$storage.get('role')
		},
		methods: {
			onLogout() {
				let storage = this.$storage
				let router = this.$router
				storage.clear()
				router.replace({
					name: "login"
				});
			},
      			onIndexTap(){
      				window.location.href = `${this.$base.indexUrl}`
    			},
			setHeaderStyle() {
			  this.$nextTick(()=>{
			    document.querySelectorAll('.navbar .right-menu .logout').forEach(el=>{
			      el.addEventListener("mouseenter", e => {
			        e.stopPropagation()
			        el.style.backgroundColor = this.heads.headLogoutFontHoverBgColor
					el.style.color = this.heads.headLogoutFontHoverColor
			      })
			      el.addEventListener("mouseleave", e => {
			        e.stopPropagation()
			        el.style.backgroundColor = "transparent"
					el.style.color = this.heads.headLogoutFontColor
			      })
			    })
			  })
			},
		},
		computed: {
			headerStyle() {
				return {
					backgroundImage: `url(${require('@/assets/head-img.png')})`,
					backgroundRepeat: 'no-repeat',
					backgroundPosition: 'center center',
					backgroundSize: 'cover'
				}
			}
		}
	};
</script>


<style lang="scss" scoped>
	.navbar {
		height: 60px;
		line-height: 60px;
		width: 100%;
		padding: 0 34px;
		box-sizing: border-box;
		background: linear-gradient(135deg, #1E90FF, rgba(138, 174, 220, 0.8));
		position: relative;
		z-index: 111;
		box-shadow: 0 2px 10px rgba(30, 144, 255, 0.2);
		
		.right-menu {
			position: absolute;
			right: 34px;
			top: 0;
			height: 100%;
			display: flex;
			justify-content: flex-end;
			align-items: center;
			z-index: 111;
			
			.user-info {
				font-size: 16px;
				color: rgba(255, 255, 255, 0.9);
				padding: 0 12px;
				text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
			}
			
			.logout {
				font-size: 16px;
				color: rgba(255, 255, 255, 0.9);
				padding: 0 12px;
				cursor: pointer;
				transition: all 0.3s;
				border-radius: 4px;
				
				&:hover {
					background: rgba(30, 144, 255, 0.3);
					color: #fff;
					box-shadow: 0 2px 6px rgba(30, 144, 255, 0.2);
				}
			}
		}
		
		.title-menu {
			display: flex;
			justify-content: flex-start;
			align-items: center;
			width: 100%;
			height: 100%;
			
			.title-img {
				width: 44px;
				height: 44px;
				border-radius: 22px;
				box-shadow: 0 2px 10px rgba(30, 144, 255, 0.2);
				margin-right: 16px;
			}
			
			.title-name {
				font-size: 24px;
				color: rgba(255, 255, 255, 0.9);
				font-weight: 700;
				text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
			}
		}
	}
	// .el-header .fr {
	// 	float: right;
	// }

	// .el-header .fl {
	// 	float: left;
	// }

	// .el-header {
	// 	width: 100%;
	// 	color: #333;
	// 	text-align: center;
	// 	line-height: 60px;
	// 	padding: 0;
	// 	z-index: 99;
	// }

	// .logo {
	// 	width: 60px;
	// 	height: 60px;
	// 	margin-left: 70px;
	// }

	// .avator {
	// 	width: 40px;
	// 	height: 40px;
	// 	background: #ffffff;
	// 	border-radius: 50%;
	// }

	// .title {
	// 	color: #ffffff;
	// 	font-size: 20px;
	// 	font-weight: bold;
	// 	margin-left: 20px;
	// }
</style>
