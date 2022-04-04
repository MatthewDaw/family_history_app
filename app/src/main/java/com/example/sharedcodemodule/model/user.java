package com.example.sharedcodemodule.model;

/**
 * user for website
 */

public class user {

    String userID;
    String userName;
    String email;
    String password;
    String FirstName;
    String LastName;
    String Gender;
    String fatherID;
    String motherID;
    String spouceID;

    /**
     * user object constructor
     * @param userID
     * @param UserName
     * @param Password
     * @param Email
     * @param FirstName
     * @param LastName
     * @param Gender
     */

    public user(String userID, String UserName, String Password, String Email, String FirstName, String LastName, String Gender, String fatherID, String motherID, String spouceID){
        this.userID = userID;
        this.userName = UserName;
        this.password = Password;
        this.email = Email;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Gender = Gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouceID = spouceID;
    }

    public user(String userID, String UserName, String Password, String Email, String FirstName, String LastName, String Gender){
        this.userID = userID;
        this.userName = UserName;
        this.password = Password;
        this.email = Email;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Gender = Gender;
        this.fatherID = "Empty";
        this.motherID = "Empty";
        this.spouceID = "Empty";
    }

    public boolean checkIfValid(){
        if( this.userID != "" &&
                this.userID != "" &&
                this.userName != "" &&
                this.password != "" &&
                this.email != "" &&
                this.FirstName != "" &&
                this.LastName != "" &&
                this.Gender != "" &&
                (this.Gender == "m" || this.Gender == "f")
        ){
            return true;
        }
        return false;
    }


    public void printPerson(){
        System.out.println(this.userID);
        System.out.println(this.userName);
        System.out.println(this.email);
    }

    public void setID(String id){
        this.userID = id;
    }
    public void setUserName(String i){
        this.userName = i;
    }
    public void setPassword(String i){
        this.password = i;
    }
    public void setEmail(String i){
        this.email = i;
    }
    public void setFirstName(String i){
        this.FirstName = i;
    }
    public void setLastName(String i){
        this.LastName = i;
    }
    public void setGender(String i){
        this.Gender = i;
    }

    public String getID(){
        return this.userID;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public String getFirstName(){
        return this.FirstName;
    }
    public String getLastName(){
        return this.LastName;
    }
    public String getGender(){
        return this.Gender;
    }
    public String getFatherID() { return  this.fatherID; }
    public String getMotherID() { return this.motherID; }
    public String getSpouceID() { return this.spouceID; }


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
        if (o instanceof user){
            user oUser = (user) o;
            return oUser.getID().equals(getID()) &&
                    oUser.getUserName().equals(getUserName()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender());
        }
        return false;
    }

}
