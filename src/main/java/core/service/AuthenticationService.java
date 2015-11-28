package core.service;

import core.user.Authorization;
import org.springframework.security.access.annotation.Secured;

public interface AuthenticationService {
    /**
     * determine whether the user associated with the userId is
     * registered in the db
     * @param userId
     * @return
     */
    boolean registeredUserId(String userId);

    /**
     * check whether userId match pass
     * @param userId
     * @param password
     * @return
     */
    boolean userMatchPassword(String userId, String password);

    /**
     * determine the user's permission level
     * @param userId
     * @return the permission level of the user associated with the
     *         userId and password, returns null if password does not
     *         match the userId
     */
    @Secured("authorized")
    Authorization login(String userId);
}
