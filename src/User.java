public class User {
    int userId;
    String userName;
    String password;
    String role;
    //to make new User
    public User(String Name, String pw, String r){
        this.userName = Name;
        this.password = pw;
        this.role = r;
    }
    public User(int id,String n, String p,String r ){
        this.userId = id;
        this.userName = n;
        this.password = p;
        this.role = r;
    }
    public int getUserId(){
        return userId;
    }
    public String getName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    public String setPassword(String password){
        this.password = password;
    }
    public String setRole(String role){
        this.role = role;
    }
    public String toString(){
        return "User ID : " + "\n"+
                "Name : " + "\n"+
                "Role : " + "\n";
    }
}

