package com.automation.ui.base.common.core.helpers;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class FacebookUser {

    private String email;
    private String password;
    private String id;
}
