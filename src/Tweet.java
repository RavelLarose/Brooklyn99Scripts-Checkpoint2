public class Tweet {
    private String content;
    private String username;
    private String date;
    private String time;

    public Tweet(String content, String username, String date, String time){
        this.content = content;
        this.username = username;
        this.date = date;
        this.time = time;
    }

    //getters and setters
    public String getContent(){return content;}
    public String getUser(){return username;}
    public String getDate(){return date;}
    public String getTime(){return time;}
}
