public class GenreObjects{
    private  int id;
    private  String genreName;

    public GenreObjects(int id, String genreName){
        this.id = id;
        this.genreName = genreName;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.genreName;
    }
}