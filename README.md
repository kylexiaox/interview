interview
=========

微访谈


结构：

分两部分：1. websocket 实现的聊天类接口
          2. restful api实现的数据库交互型接口
          
          打个比方的吧，微访谈好比是大家坐在会议室开会，这就是websocket实现的聊天功能
          然后数据库和后台好比是会议的书记员，把大家的内容记下来，每次新收到message则会把信息内容记录到数据库里
          restful api的功能主要是提供用户查询历史messages的能力，就好比大家把会议纪要翻开来看看。
          
          restful api 和websocket 提供的消息内容都是全量的，对于全量阅读模式直接展现足矣
          而对于展现对话的内容，需要花花你在前天封装消息树了。加油~
          
          
API: （YY着写出来的）

RESTFUL API:

-- register 对于普通的visitor 首选需要取个昵称，然后数据库里写入这个用户的信息并返回token。前端抓到userId 和 token 就可以开始启动websocket 会话了

          request: http POST  http://host:8080/OnlineInterview/rest/register/{昵称}
          
          response: JSON EXAMPLE
          {"token":"ewpay3frv4","nickName":"kyle","userId":15,"type":"visitor"}


          (ps: 按规范是POST, 浏览器测试的时候可以在源代码里面改成GET)

/-----------------------------------------------------------------------------------------------------------/

-- retrive latest chat list 获取最近会话列表

          request http GET http://http://host:8080/OnlineInterview/rest/retriveLatest
            (默认30条记录)
          
          response: JSON EXAMPLE
          
          [{"timeStamp":"2014-10-06 00:14:22.12296","nickName":"ACC","replyMessageId":-1,"userId":1,"messageContent":"hello","messageId":3},{"timeStamp":"2014-10-06 00:14:10.856673","nickName":"ACC","replyMessageId":-1,"userId":1,"messageContent":"hello!","messageId":2},{"timeStamp":"2014-10-06 00:14:08.425154","nickName":"ACC","replyMessageId":-1,"userId":1,"messageContent":"hello!","messageId":1}]


/------------------------------------------------------------------------------------------------------------/


-- retirve chat list  获取历史会话列表

          request http GET http://host:8080/OnlineInterview/rest/retriveChatList/{index}
            (默认20条记录)
            
          response: JSON EXAMPLE
          
          [{"timeStamp":"2014-10-06 00:14:22.12296","nickName":"ACC","replyMessageId":-1,"userId":1,"messageContent":"hello","messageId":3},{"timeStamp":"2014-10-06 00:14:10.856673","nickName":"ACC","replyMessageId":-1,"userId":1,"messageContent":"hello!","messageId":2},{"timeStamp":"2014-10-06 00:14:08.425154","nickName":"ACC","replyMessageId":-1,"userId":1,"messageContent":"hello!","messageId":1}]

/---------------------------------------下面是WEBSOCKET 的接口-----------------------------------------------/

WEBSOCKET HANDSAKE:

          ws://host:8080/OnlineInterview/websocket?userId={userId}&token={token}
          
          (后面两个参数都是认定用户身份的)





