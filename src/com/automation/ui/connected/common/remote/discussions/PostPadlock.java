package com.automation.ui.connected.common.remote.discussions;

import com.automation.ui.base.common.remote.operations.http.RemoteOperation;
import com.automation.ui.connected.common.remote.discussions.context.ThreadContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PostPadlock {

    static final String LOCK_POST_URL_SUFFIX = "%s/threads/%s/lock";

    private final RemoteOperation remoteOperation;

    public void execute(final ThreadContext context) {
        final String url = buildUrl(context);
        remoteOperation.execute(url);
    }

    private String buildUrl(final ThreadContext context) {
        return DiscussionsClient
                .service(String.format(LOCK_POST_URL_SUFFIX, context.getSiteId(), context.getThreadId()));
    }
}
