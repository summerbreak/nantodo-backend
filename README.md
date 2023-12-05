# nantodo-backend api文档

## 默认规则

1. 基址: http://localhost:8080 (考虑到后端代码可能会频繁变动，暂不部署到服务器上，大家手动git clone然后本地运行，如有更改git pull即可)，实际url = 基址 + 前缀

2. 发送POST, PUT请求时携带的数据体(body)格式请严格对应后端代码中document下的各个实体类的成员变量，且将请求头的content-type设置为application/json

3. 发送需要param的请求时，param的键最好和下列表格中"param"列的值保持一致，如果需要多个param在表格中每个param的键名会用逗号分隔。

## 公共接口

collection是枚举变量，可取值: user, course, group, homework, task

Collection为对应的实体对象类型

| 前缀          | method | param | body       | 功能         | 备注  |
| ----------- | ------ | ----- | ---------- | ---------- | --- |
| /collection | GET    | id    |            | 根据id获取一个对象 |     |
| /collection | POST   |       | Collection | 添加一个对象     |     |
| /collection | PUT    | id    | Collection | 修改一个已存在对象  |     |
| /collection | DELETE | id    |            | 根据id删除一个对象 |     |

## 特定接口

| 前缀                | method | param           | body | 功能                        | 备注                     |
| ----------------- | ------ | --------------- | ---- | ------------------------- | ---------------------- |
| /user/login       | GET    | phone, password |      | 根据手机号与密码获取对应user对象        | 若用户不存在返回404，若密码错误返回401 |
| /course/all       | GET    | userId          |      | 获取指定user拥有的所有course对象     | 若user不存在返回500          |
| /course/allCourse | GET    |                 |      | 获取所有课程                    |                        |
| /group/all        | GET    | userId          |      | 获取指定user拥有的所有group对象      | 若user不存在返回500          |
| /homework/all     | GET    | courseId        |      | 获取指定course拥有的所有homework对象 | 若course不存在返回500        |
| /task/all         | GET    | userId          |      | 获取指定user拥有的所有task对象       | 若user不存在返回500          |

## 示例

新用户注册(添加一个user):

```javascript
function postUser() {
    axios.post('http://localhost:8080/user', {
        name: "张维为",
        password: "123456",
        phone: "11451419198",
        email: "123456789@zww.com",
        studentNumber: "211250999",
        avatarUrl: "",
        grade: "大四"
    }).then(res => {
        console.log(res) // 请求成功的回调
    }, err => {
        console.log(err) // 请求失败的回调
    })
}
```

查询指定task:

```javascript
function getTask() {
    let id = '656f0fc5b7821e754548b245'
    axios.get(`http://localhost:8080/task?id=${id}`)
        .then(res => {
            console.log(res)
        }, err => {
            console.log(err)
        })
}
```


