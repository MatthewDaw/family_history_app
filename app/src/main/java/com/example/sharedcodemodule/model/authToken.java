package com.example.sharedcodemodule.model;

/**
 * authorization token
 */
public class authToken {

    /**
     * unique id of token
     */
    String tokenID;
    /**
     * id of user associated with auth token
     */
    String assocaitedUserID;

    /**
     * authorization token constructor
     * @param tokenID token id
     * @param assocaitedUserID associated it
     */

    public authToken(String tokenID, String assocaitedUserID){
        this.tokenID = tokenID;
        this.assocaitedUserID = assocaitedUserID;
    }

    public String getTokenID(){ return this.tokenID; }
    public String getAssocaitedUserID() { return this.assocaitedUserID; }

    public void setTokenID(String tokenID){
        this.tokenID = tokenID;
    }
    public void setAssocaitedUserID(String assocaitedUserID){
        this.assocaitedUserID = assocaitedUserID;
    }

    /**
     * check if two objects are equal to each other
     * @param o object to compare against
     * @return true if objcts are the same, false if otherwise
     */

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o instanceof authToken){
            authToken oauthToken = (authToken) o;
            return oauthToken.getTokenID().equals(getTokenID()) &&
                    oauthToken.getAssocaitedUserID().equals(getAssocaitedUserID());
        } else {
            return false;
        }
    }

}
