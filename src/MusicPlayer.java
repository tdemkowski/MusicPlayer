import java.util.*;

class musicNode {
    String track;  // The name of the track
    int played= 0; // The number of times played
    int shuffleTag= 0; // For shuffling
    musicNode next;
    
    public musicNode() {		// Here's how we construct an empty list.
        next = null;
    }
    public musicNode(String t) {
        track = t; next = null;
    }
    public musicNode(String t, musicNode ptr) {
        track = t; next = ptr;
    }
    
    // new method:
    public String getName() {
    	return track;
    }
    
    // new method:
    public int getPlayed() {
    	return played;
    }
    
     public boolean LTTrack(musicNode x) {   // Compares tracks according to alphabetical order on strings
    	 if (this.track.compareTo(x.track)<=0) return true;
    	 else return false;
     }
     
     public boolean LTPlayed(musicNode x) {   // Compares according to the played field.
    	 if (this.played <= x.played) return true;
    	 else return false;
     }
     
     public boolean LTTag(musicNode x) {   // Compares according to the shuffle tag.
    	 if (this.shuffleTag <= x.shuffleTag) return true;
    	 else return false;
     }
}; 

// This class represents a playlist;
// We assume that each track appears at most once in the playlist


//-----------------------------------------------------------------------

// NOTE: i have about 3 versions of mergeSort here

public class MusicPlayer {
	protected musicNode head = null; // Pointer to the top of the list.
	int length=0;   // the number of nodes in the list.
    boolean debug= false;
    
    //new method:
    public void removeTop() {
    	// so here I do not change the size of the list, I try to make the original top
    	// 'irrelevant' for the sake of O(1)
    	this.head = this.head.next;
    }
    
    public  MusicPlayer() {
    }
    public void setToNull() {
        head = null;
    }
    public boolean isEmpty() {
        return head == null;
    }
    public String firstTrack() {
        return head.track;
    }
    
    public int firstPlaycount() {
        return head.played;
    }
    
    public int firstTag() {
        return head.shuffleTag;
    }
       
    public musicNode head() {
        return head;
    }
    public void printAll() 
    {   for (musicNode tmp = head; tmp != null; tmp = tmp.next)
            System.out.print(tmp.track.toString());
            System.out.print('\n');
    }
    
    void playTrack(String name){  // Simulates playing a track: searches for the name and increments its played field
    	musicNode tmp = head;
    	for (; tmp != null && (tmp.track.compareTo(name)!= 0); tmp = tmp.next) {
    		
    	}
            if (tmp!= null) tmp.played= tmp.played+1;   	
    }
    
    void insertTrack(String name) { // Inserts a new track at the top of the list.
    	musicNode temp = new musicNode(name, head);
    	head = temp;
        length++;
   }
    
    void sortTrack() { // TODO
    	this.head = merge_sort(head);
    }
    //new
    public musicNode merge_sort(musicNode head) {
        if(head == null || head.next == null) { return head; }
        musicNode middle = getMid(head);
        musicNode sHalf = middle.next; 
        middle.next = null;   // partitions track

        return merge(merge_sort(head),merge_sort(sHalf));  //recursion
    }
    //new
    public musicNode merge(musicNode a, musicNode b) {
    	musicNode dummyHead, curr; 
    	dummyHead = new musicNode(); 
    	curr = dummyHead;
        while(a !=null && b!= null) {
            if(a.LTTrack(b)) { curr.next = a; a = a.next; }
            else { curr.next = b; b = b.next; }
            curr = curr.next;
        }
        curr.next = (a == null) ? b : a;
        return dummyHead.next;
    }
    //new
    public musicNode getMid(musicNode head) {
        if(head == null) { return head; }
        musicNode Q, P; 
        Q = P = head;
        while(P.next != null && P.next.next != null) {
            Q = Q.next; 
            P = P.next.next;
        }
        return Q;
    }
    
    void sortPlayed() {  // This is optional but might be useful for shuffling.
    	// Sorts (ascending) the list according to the number of times played
    	}
       
    int countItem(String item) {  // TODO
    	// Returns the number of times that item occurs in the current list
    	// PRE: that item belongs to the list
    	musicNode temp = head;
    	int count = 0;
    	while(temp != null) {
    		if(temp.track.compareTo(item) == 0) {
    			count++;
    		}
    		temp = temp.next;
    	}
        return count;
    }
    
    void shuffle() {  // TODO
    	// Randomly shuffles the list
    	Random randomGenerator = new Random();
    	musicNode temp = head;
    	
    	// Assigns shuffleTag for every musicNode
    	while(temp != null) {
    		temp.shuffleTag = randomGenerator.nextInt((length-1)*100);
    		temp = temp.next;
    	}
    	
    	// Assigns list to array and sorts by shuffle tag (ascending)
    	head = merge_sort_shuffle(head);
    	
    }
    
    // ----- MERGESORT BY SHUFFLE TAG BELOW ------------
    
    //new
    public musicNode merge_sort_shuffle(musicNode head) {
        if(head == null || head.next == null) { return head; }
        musicNode middle = getMid(head);
        musicNode sHalf = middle.next; middle.next = null;   // partitions track

        return merge_shuffle(merge_sort_shuffle(head),merge_sort_shuffle(sHalf));  //recursion
    }
    //new
    // If a's shuffle tag is smaller or equal to b's, it will assign a as the first
    // Note: two equal shuffle tags can switch with one another more than once, but it's trivial, anyway
    public musicNode merge_shuffle(musicNode a, musicNode b) {
    	musicNode dummyHead, curr; dummyHead = new musicNode(); curr = dummyHead;
        while(a !=null && b!= null) {
            if(a.shuffleTag <= b.shuffleTag) { curr.next = a; a = a.next; }
            else { curr.next = b; b = b.next; }
            curr = curr.next;
        }
        curr.next = (a == null) ? b : a;
        return dummyHead.next;
    }
    
    // ----- MERGESORT BY SHUFFLE TAG  ABOVE ------------
    
    // ----- MERGESORT BY PLAYED BLOW ------------
    //new
    public musicNode merge_sort_played(musicNode head) {
        if(head == null || head.next == null) { return head; }
        musicNode middle = getMid(head);
        musicNode sHalf = middle.next; middle.next = null;   // partitions track

        return merge_played(merge_sort_played(head),merge_sort_played(sHalf));  //recursion
    }
    //new
    // If a's shuffle tag is smaller or equal to b's, it will assign a as the first
    // Note: two equal shuffle tags can switch with one another more than once, but it's trivial, anyway
    public musicNode merge_played(musicNode a, musicNode b) {
    	musicNode dummyHead, curr; dummyHead = new musicNode(); curr = dummyHead;
        while(a !=null && b!= null) {
            if(a.played <= b.played) { curr.next = a; a = a.next; }
            else { curr.next = b; b = b.next; }
            curr = curr.next;
        }
        curr.next = (a == null) ? b : a;
        return dummyHead.next;
    }
    
    // ----- MERGESORT BY PLAYED  ABOVE ------------
    
    // miscellaneous, probably won't need this at all
    public int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
    
    
    
    void smartShuffle () {  // TODO
    	// Shuffles the list, whilst respecting the special condition on played
    	// It's pretty much radix sorting. We'll use buckets and shuffle()


    	// shuffles everything, and then orders by played (ascending)
    	shuffle();
    	head = merge_sort_played(head);
    	
    }
    
    void recommended(String[] historyList) { // TODO
    	// Step one: put entire array into a list
    	// PRE: input array non-empty, will have to make precaution for this later
    	if(historyList == null) {
    		// what to put here?
    	}
    	else {
        	musicNode temp = head;
        	// (1) Count played
        	
        	// VITAL: clear the played!! WORKS NOW!
        	while(temp != null) {
        		temp.played = 0;
        		temp = temp.next;
        	}
        	
        	while(temp != null) {
        		for(int i = 0; i < historyList.length; i++) {
        			if(temp.track.compareTo(historyList[i]) == 0)
        				temp.played++;
        		}
        		temp = temp.next;
        	}
        	
        	// (2) Remove those with played = 0
        	
        	temp = head;
        	while(temp != null) {
        		if(temp.played == 0)
        			remove(temp);
        		temp = temp.next;
        	}
        	
        	// Now (at least I think so), temp is our new playlist
        	// that contains all tracks that belong to the current track
        	// So now, we shall order it by times played, yes? Yes
        	// Okay, then we'll use smartShuffle and reverse it
        	// BRB, creating reverse method
        	// K, done
        	// Need musicTrack
        	head = merge_sort_played(head);
        	reverse(head);
    	}
    }
    
    public void remove(musicNode tmp) {
    	if(tmp == head) {
    		removeTop();
    	}
    	else {
    		musicNode J = head;
    		while(J.next != tmp) // PRE: tmp is included in current MusicPlayer
    			J = J.next;
    		J.next = tmp.next;
    		tmp.next = null;
    		
    	}
    }
    
    public musicNode reverse(musicNode head) {
    	musicNode previous = null;
    	musicNode current = head;
    	musicNode forward;

        while (current != null) {
            forward = current.next;
            current.next = previous;
            previous = current;
            current = forward;
        }

        return previous;
    }
    
    musicNode checkMembership(String _track) { // TODO
    	// If the given _track is present in the current list (i.e. the node whose "track" field
    	// is equal to _track), returns the address of that node;
    	// otherwise returns null. 
    	
    	musicNode temp = head;
    	while(temp != null) {
    		if(temp.track.compareTo(_track) == 0) {
    			return temp;
    		}
    		temp = temp.next;
    	}
        return null;
    }
    
    void moveFirstNode(MusicPlayer fromList, MusicPlayer toList) { // TODO  
    	if(fromList.isEmpty()) {
    	}
    	else {
    		toList.insertTrack(fromList.head.getName());
    		fromList.removeTop();
    	}	
    }
}