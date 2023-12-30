<template>
  <el-form status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm" style="width: 50%;margin-left: 20%">
    <el-form-item label="账号" prop="pass">
      <el-input v-model="formData.username" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="pass">
      <el-input type="password" v-model="formData.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="login">登陆</el-button>
      <el-button @click="sign">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
// 假设 login 方法是从外部导入的，用于处理登录逻辑
// 你需要根据你的实际情况编写 login 方法
import {loginService, signService} from '@/service/UserApi'; // 假设这是登录认证的服务

export default {
  data() {
    return {
      formData: {
        username: '',
        password: ''
      },
    };
  },
  methods: {
    async login() {
      // 调用登录服务，处理登录逻辑
      const response = await loginService(this.formData.username, this.formData.password);
      console.log('登陆',response)
      // 登录成功后重定向到其他页面
      if(response.code == 1){
        console.log('token',this.$store.state.token)
        console.log('userId',this.$store.state.userId)
        await this.$router.push('/dashboard'); // 假设 '/dashboard' 是登录成功后的页面
      }else{
        this.$message.error(response.msg);
      }
      console.log('登陆完成',response)
    },
    async sign() {
      // 调用注册服务，处理注册逻辑
      const response = await signService(this.formData.username, this.formData.password);

      // 登录成功后重定向到其他页面
      if(response.code == 1){
        await this.$router.push('/dashboard'); // 假设 '/dashboard' 是登录成功后的页面
      }else{
        this.$message.error(response.msg);
      }
    }
  }
};
</script>

<style>
/* 样式部分 */
.login-container {
  width: 300px;
  margin: 100px auto;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

input[type="text"],
input[type="password"],
button {
  padding: 8px;
  font-size: 16px;
}

button {
  cursor: pointer;
}

.error-message {
  color: red;
}
</style>
