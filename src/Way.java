public class Way {

    private  long start_id, finish_id;

    public Way(long _st, long _fin){
        start_id = _st;
        finish_id = _fin;
    }

    public long getStart_id(){
        return start_id;
    }

    public long getFinish_id(){
        return finish_id;
    }
}
