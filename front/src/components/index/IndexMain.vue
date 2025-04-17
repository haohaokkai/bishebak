<template>
			<el-main style="padding: 10px;
						background: #fff;
				boxShadow: 0 0 6px rgba(0,0,0,0);
                height:calc(100% - ${template2.back.menulist.menulistHeight});
        		">
    		<bread-crumbs :title="title" class="bread-crumbs"></bread-crumbs>
		<router-view class="router-view" style="height:auto;background: transparent;"></router-view>
	</el-main>
</template>

<script>
	import menu from "@/utils/menu";
	export default {
		data() {
			return {
				menuList: [],
				role: "",
				currentIndex: -2,
				itemMenu: [],
				title: '',
			};
		},
		mounted() {
			let menus = menu.list();
			this.menuList = menus;
			this.role = this.$storage.get("role");
		},
		created() {
			this.init();
		},
		methods: {
			init(){
				this.$nextTick(()=>{
					// let h = document.getElementsByClassName('el-aside')[0].clientHeight
					// document.getElementsByClassName('el-main')[0].style.minHeight = "calc(100vh - 80px - " + h+'px)'
				})
			},
			menuHandler(menu) {
				this.$router.push({
					name: menu.tableName
				});
				this.title = menu.menu;
			},
			titleChange(index, menus) {
				this.currentIndex = index
				this.itemMenu = menus;
				console.log(menus);
			},
			homeChange(index) {
				this.itemMenu = [];
				this.title = ""
				this.currentIndex = index
				this.$router.push({
					name: 'home'
				});
			},
			centerChange(index) {
				this.itemMenu = [{
					"buttons": ["新增", "查看", "修改", "删除"],
					"menu": "修改密码",
					"tableName": "updatePassword"
				}, {
					"buttons": ["新增", "查看", "修改", "删除"],
					"menu": "个人信息",
					"tableName": "center"
				}];
				this.title = ""
				this.currentIndex = index
				this.$router.push({
					name: 'home'
				});
				
			}
		}
	};
</script>
<style lang="scss" scoped>
	a {
		text-decoration: none;
		color: #555;
	}

	a:hover {
		background: #00c292;
	}

	.nav-list {
		width: 100%;
		margin: 0 auto;
		text-align: left;
		margin-top: 20px;

		.nav-title {
			display: inline-block;
			font-size: 15px;
			color: #333;
			padding: 15px 25px;
			border: none;
		}

		.nav-title.active {
			color: #555;
			cursor: default;
			background-color: #fff;
		}
	}

	.nav-item {
		margin-top: 20px;
		background: #FFFFFF;
		padding: 15px 0;

		.menu {
			padding: 15px 25px;
		}
	}

	.el-main {
		// background-color: #F6F8FA;
		padding: 0 24px;
		min-height: 100vh;
		// padding-top: 60px;
	}

	.router-view {
		padding: 10px;
		margin-top: 10px;
		background: #FFFFFF;
		box-sizing: border-box;
	}

	.bread-crumbs {
		width: 100%;
		// border-bottom: 1px solid #e9eef3;
		// border-top: 1px solid #e9eef3;
		margin-top: 10px;
		box-sizing: border-box;
	}
	
	.detail-form-content {
	    background: transparent;
	}

	.index-main {
		position: relative;
		overflow: hidden;
		display: flex;
		flex-wrap: wrap;
		background: rgba(255, 255, 255, 0.9);
		box-shadow: -2px 0 10px rgba(30, 144, 255, 0.1);

		.index-main-inner {
			position: relative;
			overflow: hidden;
			display: flex;
			flex-wrap: wrap;
			width: 100%;
			height: 100%;
			padding: 20px;
			box-sizing: border-box;

			.el-breadcrumb {
				width: 100%;
				height: auto;
				line-height: 40px;
				padding: 0 20px;
				margin: 0 0 20px;
				color: #1E90FF;
				font-size: 14px;
				border-radius: 4px;
				border-width: 1px;
				border-style: solid;
				border-color: rgba(30, 144, 255, 0.2);
				background-color: rgba(255, 255, 255, 0.9);
				box-shadow: 0 2px 6px rgba(30, 144, 255, 0.1);
				box-sizing: border-box;

				.el-breadcrumb__item {
					.el-breadcrumb__inner {
						color: #1E90FF;
						font-size: 14px;
						font-weight: normal;
						transition: all 0.3s;

						&:hover {
							color: rgba(138, 174, 220, 0.8);
						}
					}

					&:last-child {
						.el-breadcrumb__inner {
							color: rgba(138, 174, 220, 0.8);
							font-weight: bold;
						}
					}
				}
			}

			.router-view {
				width: 100%;
				height: calc(100% - 80px);
				padding: 20px;
				margin: 0;
				border-radius: 4px;
				border-width: 1px;
				border-style: solid;
				border-color: rgba(30, 144, 255, 0.2);
				background-color: rgba(255, 255, 255, 0.9);
				box-shadow: 0 2px 6px rgba(30, 144, 255, 0.1);
				box-sizing: border-box;
				overflow: auto;
			}
		}
	}
</style>
