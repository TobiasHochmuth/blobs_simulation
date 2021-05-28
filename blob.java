import java.util.Random;

public class blob {
   
    public enum status{
        NORMAL,
        TOUCHING,
        TOUCHED
    }

    int partnerCount;
    //position
    int posX;
    int posY;
    //velocity
    int dX;
    int dY;
    //blob dimension and type
    int radius;
    boolean type; //true = thick; false = skinny;
    //blob masterID
    int masterID;
    //Status
    status blobStatus = status.NORMAL;

 

    public blob(int _i, int _partnerCount, boolean _type) //init blob
    {
        partnerCount = _partnerCount;
        if (_type)
        {
            radius = 30;
        }
        else {
            radius = 12;
        }
    }
    
    double[] distance_array = new double[partnerCount];

    //get ranom positions
    public int[] randomPosition(int[] _canvas_res)
    {
        Random rand = new Random(); //random number genertor
        int[] position = new int[2]; //init position vector

        position[0] = rand.nextInt(_canvas_res[0] + 1); //asign x position
        position[1] = rand.nextInt(_canvas_res[1] + 1); // asign y position

        return position; //return
    }

    //get random set of speeds
    public int[] randomSpeed(int[] _max_a)
    {
        Random rand = new Random(); //random number genertor
        int[] speed = new int[2]; //init speed vector

        speed[0] = rand.nextInt(_max_a[0]*2+1) - _max_a[0]; //asign x speed
        speed[1] = rand.nextInt(_max_a[1]*2+1) - _max_a[1]; // asign y speed

        return speed; //return
    }

}
