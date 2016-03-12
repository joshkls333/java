/**
 * PokerHands.java
 *
 *
 * Josh Klaus
 * 2015/02/06
 */
import java.util.*;

public class PokerHands {

    public static void main(String [] args) 
    {
 	   
 	   final int CARD = 5;
 	   int[] hand = new int [CARD];
 	   
 	   Scanner input = new Scanner(System.in);

       System.out.println("Enter five numeric cards,no face cards. Use 2-9");
       for(int i=0;i<hand.length;i++)
       {
       		System.out.print("Card" +(i+1)+": ");
       		hand[i] = input.nextInt();
       
       }

 	   if (containsFourOfaKind(hand)) 
       {
        	System.out.println("Four of a Kind!");
       } 
 	   else if (containsFullHouse(hand)) 
       {
        	System.out.println("Full House!");
       } 
       else if (containsThreeOfaKind(hand))
       {
       	    System.out.println("Three of a Kind!");
       }
       else if (containsTwoPair(hand))
       {
       	    System.out.println("Two Pair!");
       }
       else if (containsAPair(hand))
       {
       	    System.out.println("A Pair!");
       }
       else if (containsStraight(hand))
       {
       	    System.out.println("A Straight!");
       }
       else
       	    System.out.println("High Card!");
    }
    public static boolean containsAPair(int hand[]) 
    {
    	
    	boolean found = false;
    	int card = 0;
    	
    	for(int i=0;i<hand.length;i++)
    	{	
    		card = hand[i];
    	            
    		for(int j = i;j<hand.length-1;j++)
    		{
    	        if(card == hand[j+1])
    		    {
    		       found = true;
    		    }
    		}    		    	    
    	}
    	return found;
     }
     public static boolean containsTwoPair(int hand[]) 
       {
    	boolean found = false;
    	int cardA = 0;
    	int cardB = 0;
    	
    	for(int i=0;i<hand.length;i++)
    	{	
    		cardA = hand[i];
    	            
    		for(int j = i;j<hand.length-1;j++)
    		{
       		    if(cardA == hand[j+1])
    		    {
    		         for(int l=1;l<hand.length;l++)
    	             {	
    		            cardB = hand[l];
    	            
    		            for(int m = l;m<hand.length-1;m++)
    		            {
    	                   if(cardB == hand[m+1] && cardB!= cardA)
    		               {
    		                found = true;
    		               }
    		            }    		    	    
    	             }
    		    }    		   
    		}    		    	    
    	}
        return found;
    }
    public static boolean containsFullHouse(int hand[]) 
    {
    	boolean found = false;
    	int cardA = 0;
    	int cardB = 0;
    	
//    	Arrays.sort(hand);
    	
    	for(int i=0;i<hand.length;i++)
    	{	
    		cardA = hand[i];
    	            
    		for(int j = i;j<hand.length-1;j++)
    		{
       		    if(cardA == hand[j+1])
    		    {
    		       for(int k = j+1;k<hand.length-1;k++)
    		       {
    		    
    		          if(cardA == hand[k+1])
    		          {
    		              for(int l=0;l<hand.length;l++)
    	                  {	
    		                cardB = hand[l];
    	            
    		                for(int m = l;m<hand.length-1;m++)
    		                {
    	                    if(cardB == hand[m+1] && cardB!= cardA)
    		                {
    		                found = true;
    		                }
    		              }    		    	    
    	                }
    	
    		          }
    		       }    		
    		    }    		   
    		}    		    	    
    	}
        return found;
    }
    public static boolean containsThreeOfaKind(int hand[]) 
    {
    	
    	boolean found = false;
    	int card = 0;
    	
    	for(int i=0;i<hand.length;i++)
    	{	
    		card = hand[i];
    	            
    		for(int j = i;j<hand.length-1;j++)
    		{
       		    if(card == hand[j+1])
    		    {
    		       for(int k = j+1;k<hand.length-1;k++)
    		       {
    		    
    		          if(card == hand[k+1])
    		          {
    		                found = true;
    		          }
    		       }    		
    		    }    		   
    		}    		    	    
    	}
    	return found;
    }
    public static boolean containsFourOfaKind(int hand[]) 
    {
    	
    	boolean found = false;
    	int card = 0;
    	
    	for(int i=0;i<hand.length;i++)
    	{	
    		card = hand[i];
            
    		for(int j = i;j<hand.length-1;j++)
    		{
    		    
    		    if(card == hand[j+1])
    		    {
    		       for(int k = j+1;k<hand.length-1;k++)
    		       {
    		    
    		          if(card == hand[k+1])
    		          {
    		             for(int l = k+1;l<hand.length-1;l++)
    		             {
    		             	 if(card == hand[l+1])
    		             	 {	  
    		             	 	found = true;
    		             	 }
    		             }
    		          }    		        
    		       }    		
    		    }    		   
    		}    	    
    	}
    	return found;
    }
    public static boolean containsStraight(int hand[])
    {
    	
    	int high = hand[0];
    	int low = hand[0];
    	
    	for(int i = 1;i<hand.length;i++)
    	{
    		if(hand[i]>high)
    			high = hand[i];
    	}
    	for(int i = 1;i<hand.length;i++)
    	{
    		if(hand[i]<low)
    			low = hand[i];
    	}
    	if( (high - low) ==4)
    		return true;
    	else 
    		return false;
    }    
}