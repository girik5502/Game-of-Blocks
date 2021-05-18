import java.util.*;
//public class which contains the main method.
public class Board {
    static int tiles[]=new int[100],winner=0,flag=0;
    public static void main(String args[]) {
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the number of players playing.");
        int n= sc.nextInt();
        int P[]=new int[n];
        int Q[]=new int[n];
        int count=n;
        Board ob = new Board();
        for(int i=0;i<n;i++) {
            P[i]=1;
            Q[i]=0;
        }
        ob.create_tiles();
        ob.create_snakes();
        ob.create_ladders();

        int per=0;
        if(n==1) {
            flag=1;
            winner=1;
        }

        while(flag==0) {
            if(per==n) {
                per=0;
            }
            if(Q[per]==0) {
                int roll= ob.roll(per+1);
                ob.curr_chance(roll,per);
                if(roll>0) {
                    ob.turn(roll,P[per],per);
                }
                if(roll==0) {
                    Q[per]=1;
                    count--;
                }
            }
            if(count==1) {
                for(int i=0;i<n;i++) {
                    if(Q[i]==0) {
                        winner= i+1;
                        flag=1;
                        break;
                    }
                }
            }

            per++;

        }
        System.out.println("Winner is Player "+winner);
    }
//asks the user to roll the dice or quit the game.
    int roll(int player) {
        System.out.println("It's Player "+player+"'s chance to roll.");
        System.out.println("Choose 'R' to Roll or 'Q' to Quit.");
        Scanner ab=new Scanner(System.in);
        char ch= ab.next().charAt(0);
        if(ch=='R') {
            double x=Math.floor(Math.random() * 6 + 1);
            return (int)x ;
        }
        else if(ch=='Q') {
            return 0;
        }
        return -1;   
    }
//finds out the tile of the player after rolling the dice.
    int tile(int player,int chance) {
        int x=tiles[player+chance-1];
        return x;
    }

    void create_tiles() {
        for(int i=0;i<100;i++) {
            tiles[i]=i+1;
        }
    }
//creates snakes.
    void create_snakes() {
        tiles[98]=26;
        tiles[96]=85;
        tiles[88]=66;
        tiles[75]=62;
        tiles[72]=11;
        tiles[65]=23;
        tiles[58]=45;
        tiles[49]=33;
        tiles[38]=2;
        tiles[34]=4;
        tiles[26]=6;
    }
//creates ladders.
    void create_ladders() {
        tiles[1]=22;
        tiles[6]=28;
        tiles[21]=40;
        tiles[27]=76;
        tiles[29]=31;
        tiles[43]=57;
        tiles[53]=68;
        tiles[69]=89;
        tiles[79]=82;
        tiles[86]=92;
    }
//tells the player what happened to them in the current move.
    void turn(int roll,int pl,int per) {

        if(roll+pl<100) {
            int curr=pl;
            pl= tile(curr,roll);
            if(pl> curr+roll) {
                System.out.println("Player"+(per+1)+" stepped on a ladder and moved up.");
            }
            else if(pl< curr+roll) {
                System.out.println("Player"+(per+1)+" stepped on a snake and fell down.");
            }
            System.out.println("Player "+(per+1)+"'s current position is "+pl);
        }
        else if(roll + pl==100) {
            winner=per+1;
            flag=1;
        }
        else {
            System.out.println("Player "+(per+1)+" position after roll becomes "+ (roll+pl)+ " which is not feasible,so position goes back to "+pl);
        }

    }
//checks if the player rolled a dice,made an invalid choice or quit the game.
    void curr_chance(int roll,int per) {
        if(roll==0) {
            System.out.println("Player "+(per+1)+" has quit the game.");
        }
        else if(roll== -1) {
            System.out.println("Player "+(per+1)+" made an invalid choice,hence chance skipped.");
        }
        else {
            System.out.println("Player "+(per+1)+" rolled a "+roll);
        }

    }  
}



