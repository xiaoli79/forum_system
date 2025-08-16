package com.forum_system.service.Impl;

import com.forum_system.common.AppResult;
import com.forum_system.common.ResultCode;
import com.forum_system.dao.MessageMapper;
import com.forum_system.dao.UserMapper;
import com.forum_system.exception.ApplicationException;
import com.forum_system.model.Message;
import com.forum_system.model.User;
import com.forum_system.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private MessageMapper messageMapper;


    @Autowired
    private UserServiceImpl userServiceImpl;


    @Override
    public void create(Message message) {

//      参数校验！！
        if(message == null || message.getPostuserid() == null
          || message.getReceiveuserid() == null|| message.getContent() == null) {
                log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
                throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
//       校验接收者是否存在
        User user = userServiceImpl.selectById(message.getReceiveuserid());
        if(user == null || user.getDeletestate() == 1) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }


//      设置默认值
        message.setState((byte) 0);
        message.setDeletestate((byte) 0);
        Date date = new Date();
        message.setCreatetime(date);
        message.setUpdatetime(date);

//      调用数据库！！
        int row = messageMapper.insertSelective(message);
//      校验是否插入成功！！
        if(row != 1){
            log.warn(ResultCode.FAILED_CREATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }


    }

    @Override
    public Integer selectUnReadCount(Long receiveUserId) {
        if(receiveUserId == null || receiveUserId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        int count = messageMapper.selectUnReadCount(receiveUserId);
        if(count <0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return count;
    }




    @Override
    public List<Message> selectByReceiveUserId(Long receiveUserId) {
        if(receiveUserId == null || receiveUserId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        List<Message> messages = messageMapper.selectByReceiveUserId(receiveUserId);
        return messages;

    }

    @Override
    public void selectStateById(Long id, Byte state) {
//      非空校验
        if(id == null || id <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        if(state < 0 || state >2) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Message updateMessage = new Message();

        updateMessage.setId(id);
        updateMessage.setState(state);
        Date date = new Date();
        updateMessage.setUpdatetime(date);
        int row = messageMapper.updateByPrimaryKeySelective(updateMessage);
        if(row != 1){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }



    }

    @Override
    public void reply(Long repliedId, Message message) {
//       参数校验
        if(repliedId == null || repliedId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
//        判断站内信是否存在，如果存在，抛出异常！！
        Message existMessage = messageMapper.selectByPrimaryKey(repliedId);
        if(existMessage == null || existMessage.getDeletestate() == 1) {
            log.warn(ResultCode.FAILED_MESSAGE_NOT_EXISTS.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_MESSAGE_NOT_EXISTS));
        }
//       更新状态为已回复
        selectStateById(repliedId, (byte) 2);
//        回复的内容写入数据库
        create(message);
    }
}
