import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Scheduler {

    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;

    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {
        																				// Should be instantiated with an Assignment array
        if(assignmentArray.length != 0) {												// All the properties of this class should be initialized here
        	this.assignmentArray = assignmentArray; 									//initialization for declared arrays and filling max array with -1's for not calculated values
        	C = new Integer[assignmentArray.length];
        	max = new Double[assignmentArray.length];
        	solutionDynamic = new ArrayList<Assignment>();
			solutionGreedy = new ArrayList<Assignment>();
			Arrays.fill(max, -1.0);
        }
        else throw new IllegalArgumentException();
    }

    private int binarySearch(int index) {

    	int low = 0;										//declaring low(first index of wanted part), high(last index of wanted part) and mid(middle of them)															
        int high = index - 1;
        if(index == -1) return -1;
        while(low <= high) {								//binary search operation
        	int mid = (low + high) / 2;
        	if((assignmentArray[mid].getFinishTime().compareTo(assignmentArray[index].getStartTime())) < 1) {
        		if((assignmentArray[mid+1].getFinishTime().compareTo(assignmentArray[index].getStartTime())) < 1) {
        			low = mid + 1;
        		}
        		else
        			return mid;
        	}
        	else
        		high = mid - 1;
        }
    	return -1;
    }

    public void calculateC() {
    	for(int j = 0; j < assignmentArray.length; j++) {		//for loop for filling the C array with binary searches at 0 to index-1(elements before the given index)
    		int lastIndex = binarySearch(j);					//if no compatible assignments before the given assignment, it fills C[j] with -1(null)
    		C[j] = lastIndex;
    	}
    }

    public ArrayList<Assignment> scheduleDynamic() {			//just carried out the methods 
    															//CalculateMax and findSolutionDynamic are recursive, then carried out with just last index
    	calculateC();											//solutionDynamic array filled with recursion so it should be reversed
    	calculateMax(assignmentArray.length-1);
    	
        findSolutionDynamic(assignmentArray.length-1);
        Collections.reverse(solutionDynamic);
       
    	
    	return solutionDynamic;
    }

    private void findSolutionDynamic(int i) {																//method for finding solution by dynamic way and recursively
    																										//maxWeight declared because of if no last compatible assignment before the index, it will be 0. 
    	Double maxWeight;																					//Otherwise it will be weight of after the objects
    	if(assignmentArray.length == 1) {																//if Assignment array's length is 1, add that to the solution directly
    		System.out.println(String.format("findSolutionDynamic(%d)",i));
    		System.out.println("Adding " + assignmentArray[i].toString() + " to the dynamic schedule");
    		solutionDynamic.add(assignmentArray[0]);
    		return;
    	}
    	if(i == 0) {																					//base case
    		solutionDynamic.add(assignmentArray[i]);													
    		System.out.println(String.format("findSolutionDynamic(%d)",i));
    		System.out.println("Adding " + assignmentArray[i].toString() + " to the dynamic schedule");
    		
    		return;																				
    	}
    	
    	if(C[i] == -1) maxWeight = 0.0;
    	else maxWeight = max[C[i]];
    	
    	if(assignmentArray[i].getWeight() + maxWeight > max[i-1]) {										//checking whether solution is included or not by comparison between last max value 
    		solutionDynamic.add(assignmentArray[i]);													//and weight of the assignment + maxWeight
    		System.out.println(String.format("findSolutionDynamic(%d)",i));
    		System.out.println("Adding " + assignmentArray[i].toString() + " to the dynamic schedule");
    		if(C[i] != -1) {																			//if else statement for recursive algorithm that if there is compatible assignment to i+1th, carry out for that assignment
    			findSolutionDynamic(C[i]);															//else carry out with the last assignment before the assignment
    		}	
    	}
    	else {
    		System.out.println(String.format("findSolutionDynamic(%d)",i));
			findSolutionDynamic(i-1);
		}
    }

    private Double calculateMax(int i) {																//method for filling the max array recursively, some error handlings and writing out the console
    	if(i == 0 && max[0] != 0) {																		//if index is 0 and max is never calculated for 0
    		System.out.println(String.format("calculateMax(%d): Zero",i));
    		max[0] = assignmentArray[0].getWeight();
    		return max[0];
    	}
    	else if(i == 0 && max[0] == 0) {																//if index is zero and its max is also zero
    		System.out.println(String.format("calculateMax(%d): Zero",i));
    		return max[0];
    	}
    	
    	else if(i > 0 && max[i] != -1.0) {																//if index is legal and max is never calculated for index i
    		System.out.println(String.format("calculateMax(%d): Present",i));
    		return max[i];
    	}
    	
    	else if (i > 0 && max[i] == -1.0) {																//if it is never calculated, get maximum of the last compatible assignment + weight 
    		System.out.println(String.format("calculateMax(%d): Prepare",i));							//or max of the last assignment before the current assignment
    		max[i] = Math.max(calculateMax(C[i]) + assignmentArray[i].getWeight(), calculateMax(i-1));
    		return max[i];
    	}
    	else {
    		return 0.0;
    	}
    }

    public ArrayList<Assignment> scheduleGreedy() {																	//method for finding the solution by greedy algorithm
    	if(assignmentArray.length == 1) {																			//error handling for just 1 element in the given array
    		solutionGreedy.add(assignmentArray[0]);
    		System.out.println("Adding " + assignmentArray[0].toString() + " to the greedy schedule");
    	}
    	else {
    		int lastCompInd = 0;																						//finding last compatible index(initialized for 0 for the first time) and for loop inside the assignment array
    		for(int i = 1; i < assignmentArray.length; i++) {
    			if(assignmentArray[lastCompInd].getFinishTime().compareTo(assignmentArray[i].getStartTime()) < 1) {		//if there is a compatible job for assignment i, add them to the solution array and set 
    				solutionGreedy.add(assignmentArray[lastCompInd]);													//the last compatible index to that assignment's index
    				System.out.println("Adding " + assignmentArray[lastCompInd].toString() + " to the greedy schedule");
    				lastCompInd = i;	
    			}
    		}
    		for(int i = assignmentArray.length; i > 0; i--) {
    			if(solutionGreedy.get(solutionGreedy.size()-1).getFinishTime().compareTo(assignmentArray[assignmentArray.length-i].getStartTime()) < 1) {	//checking the solutionGreedy reversely for dont miss anything
        			solutionGreedy.add(assignmentArray[assignmentArray.length-i]);
        			System.out.println("Adding " + assignmentArray[assignmentArray.length-i].toString() + " to the greedy schedule");
        		}
    		}
    	}
        return solutionGreedy;
    }
}
