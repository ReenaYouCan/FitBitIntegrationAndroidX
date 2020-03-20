package com.fitbit.api.exceptions;

import com.fitbit.authentication.Scope;

import java.util.Collection;


public class MissingScopesException extends FitbitAPIException {

    private Collection<Scope> scopes;

    public MissingScopesException(Collection<Scope> scopes) {
        this.scopes = scopes;
    }

    public Collection<Scope> getScopes() {
        return scopes;
    }
}
