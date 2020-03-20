package com.fitbit.authentication;

import com.fitbit.fitbitcommon.network.BasicHttpRequestBuilder;

public interface RequestSigner {

    void signRequest(BasicHttpRequestBuilder builder);

}
