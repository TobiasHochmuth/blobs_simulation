import java.util.Random;

public class blob {
    
    public blob(int _i, int _partnerCount, boolean _type, int[] _radii) //init blob
    {        
        if (_type)
        {
            radius = _radii[0];
        }
        else {
            radius = _radii[1];
        }

        blobStatus = status.NORMAL;

        distance_array = new double[_partnerCount];

    } 

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
    status blobStatus;
    public enum status{
        NORMAL,
        TOUCHING,
        TOUCHED
    }

    int touchingCounter = 0;
    int touchedCounter = 0;
    
    double[] distance_array;

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

/*
public double _distance(int _pos1X, int  _pos1Y, int _pos2X, int _pos2Y)
{
    return Math.sqrt(((_pos1X-_pos2X)*(_pos1X-_pos2X))+((_pos1Y - _pos2Y)*(_pos1Y-_pos2Y)));
}
*/