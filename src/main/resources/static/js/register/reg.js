// document.write("<script type='text/javascript' src='vue.js'></script>")
// document.write("<script type='text/javascript' src='jquery.js'></script>")

window.onload=function(){
    createCode(4);
}

var code = ""

//生成验证码的方法
function createCode(length) {
    code = "";
    var codeLength = parseInt(length); //验证码的长度
    var checkCode = document.getElementById("checkCode");
    // 所有候选组成验证码的字符，当然也可以用中文的
    var codeChars = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    //循环组成验证码的字符串
    for (var i = 0; i < codeLength; i++){
        //获取随机验证码下标
        var charNum = Math.floor(Math.random() * 62);
        //组合成指定字符验证码
        code += codeChars[charNum];
    }
    if (checkCode){
        //为验证码区域添加样式名
        checkCode.className = "code";
        //将生成验证码赋值到显示区
        checkCode.innerHTML = code;
    }
}

// let btn = document.getElementById("btn")
// btn.onclick = function(){
//     $.ajax({
//         url:"http://47.120.37.156:8080/user/register",
//         type:"post",
//         async:false,
//         dataType:"json",
//         data:JSON.stringify({
//             "username":"suqiang",
//             "password":"Suqiang0122",
//             "name":"苏强",
//             "sex":"male",
//             "email":"1029642530@qq.com",
//             "phone":"13574009157"
//         }),
//         contentType:"application/json",
//         success:function(res){
//             console.log("succeed ",res)
//         },
//         error:function(err){
//             console.log("error ",err)
//         }
//     })
// }

let vmRegister = new Vue({
    el:"#registerForm",
    data:{
        username:"",
        password:"",
        password2:"",
        chargeCode:"",
        name:"",
        phone:"",
        sex:"",
        email:""
    },
    methods:{
        isChinese(str){
            // 匹配任意不是中文字符的字符
            var reg = /[^\u4E00-\u9FA5]/g;
            return !reg.test(str);
        },
        isNumber(str){
            // 匹配任意不是数字的字符
            var reg = /[^0-9]/g;
            return !reg.test(str);
        },
        checkLength(str){
            var len = str.length;
            return len >= 8 && len <= 16;
        },
        containsCharacters(str){
            // 匹配任意不是英文字符大小写或数字的字符
            var reg = /[^a-zA-Z0-9]/g;
            return !reg.test(str);
        },
        submit(){
            if(this.username===''||this.password===''||this.password2===''||this.chargeCode===''||this.name===''||this.sex===''||this.phone===''||this.email===''){
                alert('输入值不能为空！')
            }else if(this.password!==this.password2){
                alert('两次密码不一致！')
            }else if(this.chargeCode!==code){
                alert('验证码错误！')
            }else if(this.isChinese(this.name)===false||this.isNumber(this.phone)===false||this.checkLength(this.username)===false||this.containsCharacters(this.password)===false){
                console.log("name",this.isChinese(this.name))
                console.log("phone",this.isNumber(this.phone))
                console.log("username",this.checkLength(this.username))
                console.log("password",this.containsCharacters(this.password))
                //  要求:
                //  1.姓名是中文
                //  2.电话号码是纯数字
                //  3.用户名长度为8~16个字符
                //  4.密码包含大小写和数字
                alert('输入格式不符合！')
            }else{
                $.ajax({
                    url:"http://47.120.37.156:8080/user/register",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:JSON.stringify({
                        "username":this.username,
                        "password":this.password,
                        "name":this.name,
                        "sex":this.sex,
                        "email":this.email,
                        "phone":this.phone
                    }),
                    contentType:"application/json",
                    success:function(res){
                        console.log("succeed ",res)
                        if(res.code===200){
                            alert('注册成功！')
                            location.href = '../index.html'
                        }else{
                            alert('注册失败！')
                        }
                    },
                    error:function(err){
                        console.log("error ",err)
                        alert('注册失败！')
                    }
                })
            }
        }
    }
})