
import java.util.*;

public class BlackjackGame 
{

    public static void main(String[] args) 
    {
       Random random = new Random();
       
       //Initilize variables
       int cardA, cardB, dealrA, dealrB, cardNext;
       int total = 0, totalDealr = 0;
       char playagain = 'y';
       		     
       Scanner input = new Scanner(System.in);              
            
       while (playagain == 'y')
       {
       		 
       		char repeat ='y';
       		char cont = 'c';
            
            //set card variables to display a 1 to 10
            cardA = random.nextInt(9) + 1;        
            cardB = random.nextInt(9) + 1;
            total= cardA + cardB;
            
            dealrA = random.nextInt(9) + 1;        
            dealrB = random.nextInt(9) + 1;
            totalDealr  = dealrA + dealrB;
            
     	    
            System.out.println("The dealer starts with a: " + dealrA);            
            System.out.print("Your first cards: " + cardA);
            System.out.println(", " + cardB);
            System.out.println("total =" + total );
            System.out.print("hit? (y/n): ");
            repeat = input.next().charAt(0);
            
           	while(repeat != 'n')
            {
               	cardNext = random.nextInt(10) +1;        
            	
               	System.out.println("Card:" + cardNext);
               	total += cardNext;
                
               	System.out.println("total =" + total);
               	
                if (total > 21)
                {
                	System.out.println("You busted!!  Game Over!");
                    System.out.println("Play again? (y/n)");
                    playagain = input.next().charAt(0);
              	    repeat = 'n';
              	    if (playagain == 'n')
                        System.exit(0);
                }
                else
                {
            	    System.out.print("hit again? (y/n): ");
            		repeat = input.next().charAt(0);
                }	
               	
                	
            }  
            
            while (total<22 && cont != 'n')
            {
            		
            	System.out.println("The dealer has a " + dealrA + "...");
           		System.out.print("(c to continue) ");
            	cont = input.next().charAt(0);
            	
           		while( cont == 'c' )
            	{
            		
           			System.out.println("The dealer gets a " + dealrB  );
           			System.out.println("The dealer's total =" + totalDealr );
              	
        	   		while(totalDealr<17 && cont == 'c')
           			{   
           				System.out.print("(c to continue) ");
    	       			cont = input.next().charAt(0);
              			cardNext = random.nextInt(10) +1;
	           			System.out.println("Dealer has less than 17 and hits...");
           				System.out.println("Dealer gets a " + cardNext);
           				totalDealr += cardNext;
           				System.out.println("Dealer's total =" + totalDealr);
            		}    
              		if (total> totalDealr )
               		{
           				System.out.println("You Win!");
           				cont = 'n';
                	}
                	else if (total == totalDealr)
           	    	{
           				System.out.println("Push");
           				cont = 'n';
           	    	}
           	    	else if (totalDealr > 21)
           	    	{
           	    		System.out.println("Dealer Busts. You Win!");
           	    		cont = 'n';
           	    	}
           	    	else
           	   		{
           	    		System.out.println("Dealer wins!");
           	    		cont = 'n';
           	   		}	    
                
                
            	System.out.println("Play Again? (y/n): ");
           		playagain = input.next().charAt(0);
            	}	      
            
            }	  
        }    
    }
} 
			                