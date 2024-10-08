public class CastXML {

    private final String actorId;
    private final String movieId;
    private String id;

    public CastXML(String actorId, String movieId) {
        if (actorId == null){
            //System.out.println("actor id is null");
            this.actorId = null;
        }else{
            this.actorId = actorId;

        }
        if (movieId == null){
            //System.out.println("movie id is null");
            this.movieId = null;
        }else{
            this.movieId = movieId;
        }

    }

    public String getActorId(){
        return this.actorId;
    }

    public String getMovieId(){
        return this.movieId;
    }




    public String toString() {

        return "ActorId:" + getActorId() + ", " +
                "MovieId:" + getMovieId() + ", ";
    }
}