package org.gomgom.parkingplace.Service;

import org.gomgom.parkingplace.Dto.UserDto;
import org.gomgom.parkingplace.Entity.User;

public interface UserService {
    /*
    회원가입
     */
    public abstract UserDto.responseSignupDto join(User user);
}
