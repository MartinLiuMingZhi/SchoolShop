const back = document.querySelector('#back')
back.onclick = function(){
    location.href = '../index.html'
}

let count
window.onload = function(){
    //1.查询一共多少商品数据，赋值给count
    count = chargeCount()

    //2.查询所有商品信息并显示
    body.setArray()
}

const body = new Vue({
    el:"#body",
    data:{
        array:[],
        option:{},
        option2:{},
        name:"",
        description:"",
        price:0,
        image:"",
        type:"",
        stock:0,
    },
    methods:{
        //打开修改信息
        changeList(index){
            //获取该商品的id
            // const id = index+1
            openChangeList()
            this.option = this.array[index]
            console.log('该商品id',this.option)
            hideAddList()
        },
        //提交修改
        submit(){
            if(submitChange(this.option)){
                hideChangeList()
            }
        },
        //删除信息
        deleteInf(index){
            const id = index+1
            console.log("id",id)
            if(deleteInfomation(id)){
                this.setArray()
                // this.array.splice(index,1)
            }
        },
        //设置样式
        setArray(){
            this.array = getAllShopping()
            // this.array = [1,2,3,4,5]
        },
        //隐藏修改框
        hideChangelist(){
            hideChangeList()
        },
        //隐藏添加框
        hideAddlist(){
            hideAddList()
        },
        //添加商品
        addPro(){
            if(this.name===""||this.description===""||this.price===0||this.image===""||this.type===""||this.stock===0){
                alert('填写不能为空，价格和库存不能为0！')
            }
            if(addProduct(this.name,this.description,this.price,this.image,this.type,this.stock)){
                this.name = ""
                this.description = ""
                this.price = 0
                this.image = ""
                this.type = ""
                this.stock = 0
                hideAddList()
                this.setArray()
            }
        }
    }
})

//添加数据按钮
const btn = document.querySelector("#addData")
btn.onclick = function (){
    openAddList()
    hideChangeList()
}

//提交修改
function submitChange(item){
    const option = confirm('请确认修改')
    let result = false
    if(option){
        if(option){
            $.ajax({
                url:"http://47.120.37.156:8080/product/update",
                type:"put",
                async:false,
                dataType:"json",
                data:JSON.stringify({
                    "id":item.id,
                    "name":item.name,
                    "description":item.description,
                    "price":item.price,
                    "image":item.image,
                    "type":item.type,
                    "stock":item.stock
                }),
                contentType:"application/json",
                success:function(res){
                    if(res.code===200){
                        alert('修改成功')
                        result = true
                    }else{
                        alert("修改失败")
                        console.log(res)
                    }
                },
                error:function(err){
                    console.log("error",err)
                    alert('网络错误')
                }
            })
        }
    }
    return result
}

//弹出添加数据框
function openAddList(){
    const list = document.querySelector("#addList")
    list.setAttribute("style","")
}

//隐藏添加数据框
function hideAddList(){
    const list = document.querySelector("#addList")
    list.setAttribute("style","display: none")
}

//弹出修改框
function openChangeList(){
    const list = document.querySelector("#changeList")
    list.setAttribute("style","")
}

//隐藏修改框
function hideChangeList(){
    const list = document.querySelector("#changeList")
    list.setAttribute("style","display: none")
}

//查询商品总数量
function chargeCount(){
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
                // if(res.data%10===0){
                //     result = parseInt(res.data/10)
                // }else{
                //     result = parseInt(res.data/10 + 1)
                // }
                result = res.data
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

//查询商品信息(数组)
function getAllShopping(){
    let result
    $.ajax({
        url:"http://47.120.37.156:8080/product/queryAll",
        type:"get",
        async:false,
        dataType:"json",
        data:{},
        contentType:"application/json",
        success:function(res){
            if(res.code===200){
                console.log("管理员查询商品成功",res)
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

//删除商品
function deleteInfomation(id){
    const option = confirm('请确认删除')
    let result
    if(option){
        $.ajax({
            url:"http://47.120.37.156:8080/product/deleteById",
            type:"delete",
            async:false,
            dataType:"json",
            data:{
                "id":id
            },
            contentType:"application/json",
            success:function(res){
                if(res.code===200){
                    console.log("delete succeed",res)
                    alert('删除成功')
                    result = true
                }else{
                    alert("删除失败")
                    result = false
                }
            },
            error:function(err){
                console.log("error",err)
                alert('网络错误')
                result = false
            }
        })
    }
    return result
}

//添加商品
function addProduct(name,description,price,image,type,stock){
    let result = false
    $.ajax({
        url:"http://47.120.37.156:8080/product/add",
        type:"post",
        async:false,
        dataType:"json",
        data:JSON.stringify({
            "name":name,
            "description":description,
            "price":price,
            "image":image,
            "type":type,
            "stock":stock
        }),
        contentType:"application/json",
        success:function(res){
            if(res.code===200){
                console.log(res)
                alert('添加成功')
                result = true
            }else{
                console.log(res)
                alert('存在与该商品同名的商品')
                result = false
            }
        },
        error:function(err){
            console.log("error",err)
            alert('网络错误')
            result = false
        }
    })
    return result
}