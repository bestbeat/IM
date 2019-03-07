var  vm = new Vue({
    el:"#register",
    data:{
        formData:{
            account:{
                value:"",
                isValid:false
            },
            username:{
                value:"",
                isValid:false
            },
            mobilePhone:{
                value:"",
                isValid:false
            },
            password:{
                value:"",
                isValid:false
            },
            rePassword:{
                value:"",
                isValid:false
            }
        }
    },
    computed:{
        checkAccount:function () {
            this.formData.account.isValid=false;
            if(this.formData.account.value.trim().length>0){
                this.formData.account.isValid=true;
                return "<span class='alert alert-success' >验证通过</span>";
            }
            return "<span class='alert alert-danger' ><strong>错误</strong> 账户不能为空。</span>";
        },
        checkUsername:function () {
            this.formData.username.isValid = false;
            if(this.formData.username.value.trim().length>0){
                this.formData.username.isValid=true;
                return "<span class='alert alert-success' >验证通过</span>";
            }
            return "<span class='alert alert-danger' ><strong>错误</strong> 用户姓名名不能为空。</span>";
        },
        checkMobilePhone:function () {
            this.formData.mobilePhone.isValid = false;
            if(this.formData.mobilePhone.value.trim().length>0){
                var regExp =/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/;
                if(regExp.test(this.formData.mobilePhone.value)){
                    this.formData.mobilePhone.isValid = true;
                    return "<span class='alert alert-success' >验证通过</span>";
                }
                return "<span class='alert alert-danger' ><strong>错误</strong> 不是手机号码</span>";
            }
            return "<span class='alert alert-danger' ><strong>错误</strong> 手机号码不能为空。</span>";
        },
        checkPassword:function () {
            this.formData.password.isValid = false;
            var regExp = /\s/;

            if(this.formData.password.value == undefined || this.formData.password.value ==  null || this.formData.password.value.length==0){
                return "<span class='alert alert-danger' ><strong>错误</strong> 密码不能为空。</span>";
            }

            if(regExp.test(this.formData.password.value)){
                return "<span class='alert alert-danger' ><strong>错误</strong> 不能使用空白字符</span>";
            }

            if(this.formData.password.value.trim().length < 6){
                return "<span class='alert alert-danger' ><strong>错误</strong> 密码至少需要6位</span>";
            }

            if(this.formData.password.value == this.formData.rePassword.value){
                this.formData.password.isValid = true;
                return "<span class='alert alert-success' >验证通过</span>";
            }else{
                return "<span class='alert alert-danger' ><strong>错误</strong> 两次密码不一致</span>";
            }


        },
        checkRePassword:function () {
            this.formData.rePassword.isValid=false;
            var regExp = /\s/;

            if(this.formData.rePassword.value == undefined || this.formData.rePassword.value ==  null || this.formData.rePassword.value.length==0){
                return "<span class='alert alert-danger' ><strong>错误</strong> 密码不能为空。</span>";
            }

            if(regExp.test(this.formData.rePassword.value)){
                return "<span class='alert alert-danger' ><strong>错误</strong> 不能使用空白字符</span>";
            }

            if(this.formData.rePassword.value.trim().length < 6){
                return "<span class='alert alert-danger' ><strong>错误</strong> 密码至少需要6位</span>";
            }

            if(this.formData.password.value == this.formData.rePassword.value){
                this.formData.rePassword.isValid=true;
                return "<span class='alert alert-success' >验证通过</span>";
            }else{
                return "<span class='alert alert-danger' ><strong>错误</strong> 两次密码不一致</span>";
            }
        }
    },
    methods:{
        submitRegister:function () {
            for(var p in this.formData){
                if(!this.formData[p].isValid){
                    return false;
                }
            }

            var form = document.getElementById("register").submit();
            return true;

        }
    }

});