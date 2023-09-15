// document.write("<script type='text/javascript' src='../vue.js'></script>")
// document.write("<script type='text/javascript' src='../jquery.js'></script>")

let username
let maxPage = 0
window.onload = function(){
    const u = "martin"
    getArray(u)
    //生成验证码
    createCode(4)
    // document.cookie = "username=suqiang"
    // 判断是否登录，改变页面状态
    changeState()

    //查看总页数
    maxPage = chargePage()
    page.setCount(chargePage())

    //加载商品第一页并展示
    boxes.askForShopAndReturn(1,10)
    // showShop(boxes.askForShopAndReturn(1,10))

    //“退出登录“按钮的显示与隐藏
    if(isLogin()){
        const outLogin = document.querySelector("#outLogin")
        outLogin.setAttribute("style","")
    }else{
        const outLogin = document.querySelector("#outLogin")
        outLogin.setAttribute("style","display: none")
    }
}

function changeState(){
    const cookieValue = getCookie('username')
    if(cookieValue===''||cookieValue==="undefined"){
        // 没有登录
        console.log("changeState未登录")
        console.log("cookieValue",cookieValue)
        const head = document.querySelector("#head")
        const head2 = document.querySelector("#head2")
        head.setAttribute("style","")
        head2.setAttribute("style","display:none")
    }else{
        // 登录成功了
        const head = document.querySelector("#head")
        const head2 = document.querySelector("#head2")
        console.log("changeState已登录")
        console.log("cookieValue",cookieValue)
        headTwo.username = cookieValue
        head.setAttribute("style","display:none")
        head2.setAttribute("style","")
    }
}

//查看页数
function chargePage(){
    let result = 0
    $.ajax({
        url:"http://47.120.37.156:8080/product/count",
        type:"get",
        async:false,
        dataType:"json",
        data:{

        },
        contentType:"application/json",
        success:function(res){
            if(res.code===200){
                console.log('查询商品总数成功',res.data)
                if(res.data%10===0){
                    result = parseInt(res.data/10)
                }else{
                    result = parseInt(res.data/10 + 1)
                }
            }else{
                console.log('查询商品总数失败',res)
            }
        },
        error:function(err){
            console.log("error 获取总页数失败",err)
        }
    })
    return result
}

//确认登录
function isLogin(){
    const cookieValue = getCookie('username')
    if(cookieValue===''){
        // 没有登录
        console.log("isLogin登录失败")
        return false

    }else{
        // 登录成功了
        // console.log("isLogin登录成功")
        username = cookieValue
        return true
    }
}

//返回查找的商品（对象数组）
function askF(page,count){
    let result
    $.ajax({
        url:"http://47.120.37.156:8080/product/page",
        type:"get",
        async:false,
        dataType:"json",
        data:{
            "page":page,
            "pageSize":count,
        },
        contentType:"application/json",
        success:function(res){
            if(res.code===200){
                console.log("@succeed查询商品成功",res)
                console.log("page = ",page)
                console.log("size = ",count)
                result = res
            }else{
                console.log("@err查询商品失败",res)
            }
        },
        error:function(err){
            console.log("error ",err)
            alert('加载失败')
        }
    })
    console.log('返回结果',result.data)
    return result.data
}

const boxes = new Vue({
    el:"#body",
    data:{
        array:[],
        size:0,
        wh:"https://tse1-mm.cn.bing.net/th/id/OIP-C.7KW5GT7NQ8yUGlBbCHEm0gHaNK?pid=ImgDet&rs=1"
    },
    methods:{
        //获取商品
        askForShopAndReturn(page,count){
            let result
            $.ajax({
                url:"http://47.120.37.156:8080/product/page",
                type:"get",
                async:false,
                dataType:"json",
                data:{
                    "page":page,
                    "pageSize":count,
                },
                contentType:"application/json",
                success:function(res){
                    if(res.code===200){
                        // console.log('size',this.size)
                        result = res.data
                        console.log('返回array数组结果',result)
                    }else{
                        console.log("@err查询商品失败",res)
                    }
                },
                error:function(err){
                    console.log("error ",err)
                },
            })
            this.array = result
        },

        //加入购物车
        intoShoppingCart(index){
            if(isLogin()===false){
                alert('请先登录！')
                return
            }
            index++
            // console.log("此时的页数",page.getPage())
            const id = (page.getPage()-1)*10+index
            // console.log("商品的id是",id)
            console.log("username和id",username,id)
            $.ajax({
                url:"http://47.120.37.156:8080/cart/add",
                type:"post",
                async:false,
                dataType:"json",
                data:JSON.stringify({
                    "username":username,
                    "product_id":id,
                    "num":1
                }),
                contentType:"application/json",
                success:function(res){
                    if(res.code===200){
                        console.log("intoSucceed",res)
                        alert('该商品已成功加入购物车！')
                    }else{
                        console.log("failInto",res)
                        alert('该商品在购物车存在！')
                    }
                },
                error:function(err){
                    console.log("error ",err)
                    alert('加入购物车失败！')
                }
            })
        }
    }
})

//展示
function showShop(arr){
    const size = arr.length
}

let out = document.querySelector("#outLogin")
out.onclick = function(){
    console.log("退出登录")
    alert("已退出")
    document.cookie = "username="
    const outLogin = document.querySelector("#outLogin")
    outLogin.setAttribute("style","display: none")
    const body = document.querySelector("#body")
    body.setAttribute("style","")
    const shopBody = document.querySelector("#shopBody")
    shopBody.setAttribute("style","display: none")
    changeState()
}

let vm = new Vue({
    el:"#head",
    data:{
        message:"hello world",
    }
})

let headTwo = new Vue({
    el:"#head2",
    data:{
        username:"",
    }
})

//获取浏览器中cookie名为xxx的值
function getCookie(cookieName) {
    const strCookie = document.cookie
    const cookieList = strCookie.split(';')
    for(let i = 0; i < cookieList.length; i++) {
        const arr = cookieList[i].split('=')
        if (cookieName === arr[0].trim()) {
            return arr[1]
        }
    }
    return ''
}

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
    for (var i = 0; i < codeLength; i++)
    {
        //获取随机验证码下标
        var charNum = Math.floor(Math.random() * 62);
        //组合成指定字符验证码
        code += codeChars[charNum];
    }
    if (checkCode)
    {
        //为验证码区域添加样式名
        checkCode.className = "code";
        //将生成验证码赋值到显示区
        checkCode.innerHTML = code;
    }
}

let vmLogin = new Vue({
    el:"#loginForm",
    data:{
        username:'',
        password:'',
        chargeCode:'',
    },
    methods:{
        //登录框的返回按钮
        back(){
            const login = document.querySelector("#login")
            login.setAttribute("style","display:none")
        },
        //  提交
        submit(){
            if(this.username==='manager'&&this.password==='123456'){
                alert('进入管理员界面！')
                location.href = './html/manager.html'
                return
            }
            if(this.username===''||this.password===''||this.chargeCode===''){
                alert('输入值不能为空！')
            }else if(this.chargeCode!==code){
                alert('验证码错误！')
            }else{
                console.log(this.username,this.password,this.chargeCode)
                const data = this.username
                $.ajax({
                    url:"http://47.120.37.156:8080/user/login",
                    type:"post",
                    async:false,
                    dataType:"json",
                    data:JSON.stringify({
                        "username":this.username,
                        "password":this.password,
                    }),
                    contentType:"application/json",
                    success:function(res){
                        if(res.code===200){
                            alert('登录成功！')
                            console.log("@",this.username)
                            document.cookie = `username=`+data
                            changeState()
                            const login = document.querySelector("#login")
                            login.setAttribute("style","display:none")
                            //显示退出登录按钮
                            const outLogin = document.querySelector("#outLogin")
                            outLogin.setAttribute("style","")
                        }else{
                            alert('用户名或密码错误！')
                        }
                    },
                    error:function(err){
                        console.log("error ",err)
                        alert('登录失败')
                    }
                })
            }
        },
    }
})

//点击登录按钮
const userLogin = document.querySelector("#userLogin")
userLogin.onclick = function(){
    let lo = document.querySelector("#login")
    lo.setAttribute("style","")
}


// const btn = document.querySelector("#btn")
// btn.onclick = function(){
//     //一键登录
//     // $.ajax({
//     //     url:"http://47.120.37.156:8080/user/login",
//     //     type:"post",
//     //     async:false,
//     //     dataType:"json",
//     //     data:JSON.stringify({
//     //         "username":"1029642530",
//     //         "password":"SUqiang0122",
//     //     }),
//     //     contentType:"application/json",
//     //     success:function(res){
//     //         console.log("succeed ",res)
//     //         alert('登录成功！')
//     //         changeState()
//     //         document.cookie = `username=${this.username}`
//     //         // location.href = "index.html"
//     //     },
//     //     error:function(err){
//     //         console.log("error ",err)
//     //         alert('登录失败')
//     //     }
//     // })
//
//     //查询商品数据
//     askF(1,4)
//
//     //查询商品总数
//     // chargePage()
// }

//打开购物车
const shoppingCart = document.querySelector("#shoppingCart")
shoppingCart.onclick = function(){
    if(isLogin()){
        console.log("进入购物车",username)
        shopBody.setArray()
        let shopCart = document.querySelector("#shopBody")
        shopCart.setAttribute("style","")
        let body = document.querySelector("#body")
        body.setAttribute("style","display: none")
        let page = document.querySelector("#page")
        page.setAttribute("style","display: none")
    }else{
        alert('请先登录！')
    }
}

const notIn = document.querySelector("#notIn")
notIn.onclick = function(){
    alert('请先登录！')
}

const shopBody = new Vue({
    el:"#shopBody",
    data:{
        array:[]
    },
    methods:{
        setArray(){
            this.array = getArray(username)
        },
        back(){
            console.log("back")
            let shopCart = document.querySelector("#shopBody")
            shopCart.setAttribute("style","display: none")
            let body = document.querySelector("#body")
            body.setAttribute("style","")
            let page = document.querySelector("#page")
            page.setAttribute("style","")
        }
    }
})

//获取username的购物车
function getArray(username){
    let arr
    $.ajax({
        url:"http://47.120.37.156:8080/cart/query",
        type:"get",
        async:false,
        dataType:"json",
        data:{
            "username":username
        },
        contentType:"application/json",
        success:function(res){
            if(res.code===200){
                console.log("@arr",res)
                arr = res.data
            }else{
                console.log("@err",res)
            }
        },
        error:function(err){
            console.log("@error ",err)
        }
    })
    return arr
}

const page = new Vue({
    el:"#page",
    data:{
        count:0,
        p:1
    },
    methods:{
        setCount(n){
            this.count = n
        },
        getPage(){
            return this.p
        },
        last(){
            if(this.p===1){
                alert('到第一页了')
            }else{
                this.p--
                boxes.askForShopAndReturn(this.p,10)
            }
        },
        next(){
            if(this.p===maxPage){
                alert('到最后一页了')
            }else{
                this.p++
                boxes.askForShopAndReturn(this.p,10)
            }
        }
    }
})

