package com.coin.cryptocurrency_trading_management.Service;

import com.coin.cryptocurrency_trading_management.Entity.Enum.Role;
import com.coin.cryptocurrency_trading_management.Entity.Session;
import com.coin.cryptocurrency_trading_management.Entity.User;
import com.coin.cryptocurrency_trading_management.Repository.Interface.SessionRepository;
import com.coin.cryptocurrency_trading_management.Repository.Interface.UserRepository;
import com.coin.cryptocurrency_trading_management.Service.Interface.AuthService;
import com.coin.cryptocurrency_trading_management.Service.Interface.WalletService;
import com.coin.cryptocurrency_trading_management.Util.Exception.BusinessException;
import com.coin.cryptocurrency_trading_management.Util.Exception.DuplicateResourceException;
import com.coin.cryptocurrency_trading_management.Util.Exception.ResourceNotFoundException;
import com.coin.cryptocurrency_trading_management.Util.PasswordValidator;
import com.coin.cryptocurrency_trading_management.Util.UsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final WalletService walletService;


    @Override
    public void register(String username, String password, String againPassword) throws IOException {
        if (!UsernameValidator.isValid(username)) {
            throw new BusinessException("Username is invalid");
        }
        if (userRepository.getUserByUsername(username) != null) {
            throw new DuplicateResourceException("Username is already in use");
        }
        if (!password.equals(againPassword)) {
            throw new BusinessException("Passwords don't match");
        }

        if (!PasswordValidator.isValid(password)) {
            throw new BusinessException("The password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.User);
        userRepository.save(user);
        var registerUser = userRepository.getUserByUsername(username);
        walletService.createWallet(registerUser.getId());
    }

    @Override
    public Session login(String username, String password) throws IOException {
        if (!UsernameValidator.isValid(username)) {
            throw new BusinessException("Username is invalid");
        }
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("Username not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("Wrong password");
        }

        // Nếu đã có session active còn hạn → trả luôn, không tạo thêm
        Session current = sessionRepository.getSessionActiveByUserId(user.getId());
        if (current != null) {
            sessionRepository.saveCurrentSession(current.getId());
            return current;
        }

        // Tạo session mới
        Session session = new Session(user.getId());
        sessionRepository.save(session);
        sessionRepository.saveCurrentSession(session.getId());
        return session;
    }

    @Override
    public void logout() throws IOException {
        Session current = sessionRepository.getCurrentSession();
        if (current == null) {
            sessionRepository.deleteCurrentSession();
            return;
        }

        Session session = sessionRepository.getById(current.getId());
        if (session != null) {
            session.setActive(false);
            sessionRepository.updateById(session.getId(), session);
        }

        sessionRepository.deleteCurrentSession();
    }

    @Override
    public Session getCurrentSession() throws IOException {
        return sessionRepository.getCurrentSession();
    }

    @Override
    public Session getActiveCurrentSession() throws IOException {
        Session current = sessionRepository.getCurrentSession();
        if (current == null) {
            return null;
        }

        Session sessionInDb = sessionRepository.getById(current.getId());
        if (sessionInDb == null) {
            sessionRepository.deleteCurrentSession();
            return null;
        }

        if (!sessionInDb.isActive() || LocalDateTime.now().isAfter(sessionInDb.getExpireTime())) {
            if (sessionInDb.isActive()) {
                sessionInDb.setActive(false);
                sessionRepository.updateById(sessionInDb.getId(), sessionInDb);
            }
            sessionRepository.deleteCurrentSession();
            return null;
        }

        return sessionInDb;
    }

    @Override
    public User getCurrentUser() throws IOException {
        Session session = getActiveCurrentSession();
        if (session != null) {
            return userRepository.getById(session.getUserId());
        }
        return null;
    }

    @Override
    public Role getCurrentRole() throws IOException {
        var user = getCurrentUser();
        if (user != null) {
            return user.getRole();
        }
        return null;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) throws IOException {
        Session current = sessionRepository.getCurrentSession();
        if (current == null) {
            throw new BusinessException("You are not logged in");
        }
        Session sessionInDb = sessionRepository.getById(current.getId());
        if (sessionInDb == null) {
            sessionRepository.deleteCurrentSession();
            return;
        }
        if (!sessionInDb.isActive() || LocalDateTime.now().isAfter(sessionInDb.getExpireTime())) {
            throw new BusinessException("Session is expired or inactive, please login again");
        }

        User currentUser = userRepository.getById(sessionInDb.getUserId());
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found");
        }
        if (!PasswordValidator.isValid(oldPassword)) {
            throw new BusinessException("Old password is invalid");
        }
        if (!currentUser.getPassword().equals(oldPassword)) {
            throw new BusinessException("Old password is incorrect");
        }
        if (!PasswordValidator.isValid(newPassword)) {
            throw new BusinessException("New password is invalid");
        }
        if (newPassword.equals(currentUser.getPassword())) {
            throw new BusinessException("New password must be different from old password");
        }

        currentUser.setPassword(newPassword);
        userRepository.updateById(currentUser.getId(), currentUser);
    }

}
