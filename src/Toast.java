public class Toast { // class for item toasts
    int id, amount;
    long life;
    
    public Toast(int id, long l, int a){
        this.id = id;
        life = l;
        amount = a;
    }

    public void update(long elapsedTime){
        life -= elapsedTime;
    }
}
