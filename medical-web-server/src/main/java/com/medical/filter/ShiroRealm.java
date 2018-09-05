package com.medical.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.medical.dao.UserDao;
import com.medical.domain.User;

/**
 * @author apple
 */
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    UserDao userDao;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (token.getPrincipal() == null)
            return null;
        String username = token.getPrincipal().toString();
        logger.info("用户:" + username + " 正在登录...");
        User user = userDao.findAdminByName(username);
        if (user == null)
            return null;
        else {
            logger.info("=== getName() ===" + getName());
            return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        }

    }

}
