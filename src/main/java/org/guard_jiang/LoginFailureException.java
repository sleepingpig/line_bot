package org.guard_jiang;

import javax.annotation.Nonnull;

/**
 * Created by cahsieh on 1/26/17.
 */
public class LoginFailureException extends Exception {
    public LoginFailureException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }
}
