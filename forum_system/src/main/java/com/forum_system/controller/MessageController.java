package com.forum_system.controller;
import com.forum_system.common.AppConfig;
import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.MessageMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.Message;
import com.forum_system.model.User;
import com.forum_system.service.Impl.MessageServiceImpl;
import com.forum_system.service.Impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequestMapping("/message")
@RestController
public class MessageController {


    @Autowired
    private MessageServiceImpl messageService;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    private MessageMapper messageMapper;

    @RequestMapping("/send")
    public AppResult send(HttpServletRequest request,
                         @RequestParam("receiveUserId") @NonNull Long receiveUserId,
                         @RequestParam("content") @NonNull String content) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
//       判断用户状态
        if (user == null || user.getDeletestate() == 1) {
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_BANNED));
        }
//        不能给自己发送站内信
        if(user.getId() == receiveUserId){
            return AppResult.failed("不能给自己发站内信");
        }

//        校验接收者是否存在
        User receiveUser = userService.selectById(receiveUserId);
        if(receiveUser == null || receiveUser.getDeletestate() == 1){
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_BANNED));
        }

//       疯转对象，然后调用service
        Message message = new Message();
        message.setContent(content);
        message.setPostuserid(user.getId());
        message.setReceiveuserid(receiveUserId);


        messageService.create(message);
        return AppResult.success("发送成功！！");

    }



    @RequestMapping("/getUnreadCount")
    public AppResult<Integer> getUnreadCount(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if(user == null || user.getDeletestate() == 1){
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_BANNED));
        }
        int count = messageService.selectUnReadCount(user.getId());

        return AppResult.success(count);
    }


    @RequestMapping("/getAll")
    public AppResult<List<Message>> getAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if(user == null || user.getDeletestate() == 1){
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_BANNED));
        }
        List<Message> messages = messageService.selectByReceiveUserId(user.getId());
        return AppResult.success(messages);
    }


    /**
     * 更新站内信的状态
     * @param id
     * @return
     */

    @RequestMapping("/markRead")
//  这个ID是站内信的ID
    public AppResult markRead(HttpServletRequest request , @RequestParam("id") @NonNull Long id){
//      根据Id查询站内信
        Message message = messageMapper.selectByPrimaryKey(id);

//       判断站内信是否存在
        if(message == null || message.getDeletestate() == 1){
            log.warn(ResultCode.FAILED_MESSAGE_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_MESSAGE_NOT_EXISTS));
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if(user.getId() != message.getReceiveuserid()){
            log.warn(ResultCode.FAILED_FORBIDDEN.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_FORBIDDEN));
        }

//      把站内信的状态更新成已读
        messageService.selectStateById(id, (byte) 1);
        return AppResult.success();

    }


    @RequestMapping("/reply")
    public AppResult reoly(HttpServletRequest request,
                           @RequestParam("repliedId") @NonNull Long repliedId ,
                           @RequestParam("content") @NonNull String content){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConfig.USER_SESSION);
        if(user.getState() == 1){
            log.warn(ResultCode.FAILED_USER_BANNED.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_BANNED));
        }


        Message existsMessage = messageMapper.selectByPrimaryKey(repliedId);
        if(existsMessage == null || existsMessage.getDeletestate() == 1){
            log.warn(ResultCode.FAILED_MESSAGE_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_MESSAGE_NOT_EXISTS));
        }
        if(user.getId() == existsMessage.getPostuserid()){
            throw new ApplicationException(AppResult.failed("不能给自己发消息"));
        }
        Message message = new Message();
        message.setContent(content);
        message.setPostuserid(user.getId());
        message.setReceiveuserid(existsMessage.getPostuserid());

        messageService.reply(repliedId, message);
        return AppResult.success();

    }

}
