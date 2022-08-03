package com.itheima.reggie.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.itheima.reggie.util.constant.SessionConstant.USER_ID;

/**
 * @author HeYunjia
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public R<String> login(User user, HttpServletRequest request) {

        if (ObjectUtil.isNull(user.getPhone())) {
            return R.error("手机号为空");
        }

        User query = lambdaQuery()
                .eq(User::getPhone, user.getPhone())
                .one();

        if (ObjectUtil.isNotNull(query) && query.getStatus() == 0) {
            return R.error("账号被禁用");
        }

        if (ObjectUtil.isNull(query)) {
            user.setStatus(1);
            save(user);
        } else {
            user = query;
        }

        request.getSession().setAttribute(USER_ID, user.getId());

        return R.success("登录成功");
    }
}
