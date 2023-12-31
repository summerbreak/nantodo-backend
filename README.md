# nantodo-backend api文档

## 默认规则

1. 基址: http://localhost:8080 (考虑到后端代码可能会频繁变动，暂不部署到服务器上，大家手动git clone然后本地运行，如有更改git pull即可)，实际url = 基址 + 前缀

2. 发送POST, PUT请求时携带的数据体(body)格式请严格对应后端代码中document或pojo下的各个实体类的成员变量，且将请求头的content-type设置为application/json

3. 发送需要param的请求时，param的键最好和下列表格中"param"列的值保持一致，如果需要多个param在表格中每个param的键名会用逗号分隔。

## 公共接口

collection是枚举变量，可取值: user, course, group, homework, task

Collection为对应的实体对象类型

| 前缀          | method | param | body           | 功能           | 备注                                                                         |
| ----------- | ------ | ----- | -------------- | ------------ | -------------------------------------------------------------------------- |
| /collection | GET    | id    |                | 根据id获取一个对象   |                                                                            |
| /collection | POST   |       | {}: Collection | 添加一个对象，返回其id | 添加task和group时会顺带将其加入user/group/course的对应列表                                 |
| /collection | PUT    | id    | {}: Collection | 修改一个已存在对象    | body请传完整对象，不要只传被修改的部分                                                      |
| /collection | DELETE | id    |                | 根据id删除一个对象   | 原则上不允许调用user的DELETE方法。<br/>删除task和group时会顺带将与其关联的user/group/course中的数据一并删除 |

## 特定接口

| 前缀                | method | param           | body            | 功能                                       | 备注                              |
| ----------------- | ------ | --------------- | --------------- | ---------------------------------------- | ------------------------------- |
| /user/login       | GET    | phone, password |                 | 根据手机号与密码获取对应user对象                       | 若用户不存在返回404，若密码错误返回401          |
| /user/message     | POST   | id              | {}: Message     | 向指定user发送一条消息                            | 若user不存在返回500                   |
| /course/all       | GET    | userId          |                 | 获取指定user拥有的所有course对象                    | 若user不存在返回500                   |
| /course/allCourse | GET    |                 |                 | 获取所有课程                                   |                                 |
| /course/allGroup  | GET    | courseId        |                 | 获取指定course的所有group对象                     | 若course不存在返回500                 |
| /group/all        | GET    | userId          |                 | 获取指定user拥有的所有group对象                     | 若user不存在返回500                   |
| /group/allMember  | GET    | groupId         |                 | 获取指定group拥有的所有member(user)对象             | 若group不存在返回500                  |
| /group/free       | GET    |                 |                 | 获取所有自由组队（非课程组队）的group对象                  |                                 |
| /group/app        | POST   | id              | {}: Application | 向指定group发送申请加入请求                         | 若group不存在返回500                  |
| /group/tool       | POST   | id              | {}: Tool        | 给指定group添加tool                           | 若group不存在返回500                  |
| /group/invite     | PUT    | code, userId    |                 | 通过邀请码将userId添加至对应group的成员列表              | 若找不到对应group返回500，若group已满员返回400 |
| /group/app        | PUT    | id              | {}: Application | 更新指定group中的指定application                 | 若找不到对应group则返回500               |
| /group/member     | DELETE | userId, groupId |                 | 删除指定group中的对应member，且删除指定user中的对应group   | 若找不到对应group返回500                |
| /group/tool       | DELETE | groupId, name   |                 | 删除指定group中的指定tool                        |                                 |
| /homework/all     | GET    | courseId        |                 | 获取指定course拥有的所有homework对象                | 若course不存在返回500                 |
| /task/all         | GET    | userId          |                 | 获取指定user拥有的所有task对象                      | 若user不存在返回500                   |
| /task/ofGroup     | GET    | groupId         |                 | 获取指定group拥有的所有task对象                     | 若group不存在返回500                  |
| /task/ofGroup     | DELETE | groupId         |                 | 删除指定group拥有的所有task对象，并连带清理成员user中保存的相关数据 | 若group不存在返回500                  |

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
    axios.get(`http://localhost:8080/task?userId=${id}`)
        .then(res => {
            console.log(res)
        }, err => {
            console.log(err)
        })
}
```
