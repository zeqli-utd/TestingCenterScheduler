package core.service;

import core.user.Authorization;

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
     * @param password
     * @return the permission level of the user associated with the
     *         userId and password, returns null if password does not
     *         match the userId
     */
    Authorization login(String userId, String password);
}
