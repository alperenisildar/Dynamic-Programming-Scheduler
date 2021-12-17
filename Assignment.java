public class Assignment implements Comparable<Assignment> {
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;

    public Assignment(String name, String start, int duration, int importance, boolean maellard) throws IllegalArgumentException{ 		//constructor for Assignment class

    		this.name = name;
        	this.start = start;
        	this.duration = duration;
        	this.importance = importance;
        	this.maellard = maellard;

    	
    }
    /*
        Getter methods
     */
    public String getName() {																		//getter methods
        return name;
    }

    public String getStartTime() {
        return start;
    }

    public int getDuration() {
        return duration;
    }

    public int getImportance() {
        return importance;
    }

    public boolean isMaellard() {
        return maellard;
    }

    public String getFinishTime() {																	//duration is int so it should be calculated with another int
        int hours = Integer.parseInt(start.split(":")[0]);											//hours is splitted by ':' and first one is hours of the given assignment and duration is added
        hours += duration;
        String stringCheck = hours + ":" + start.split(":")[1];										//error handling if time type is not like "00:00"
        if(stringCheck.length() < 5) stringCheck = "0" + stringCheck;
        if(hours >= 24) return (hours-24) + ":" + start.split(":")[1];								//error handling for 24 hours clock system
        else return stringCheck;

    }

    public double getWeight() {																		//method for calculating weight of the given assignment
        double weight = 0.0;																		//it's equation is given in the assignment PDF
    	if(maellard == true) {
        	weight = (double)(importance * 1001) / (double)duration;
        }
    	else if(maellard == false) {
    		weight = (double)importance / (double)duration;
    	}
    	
    	return weight;
    }


    @Override
    public int compareTo(Assignment asg) {															//compareTo override method for comparing finish times for Arrays.sort method
        if(this.getFinishTime().equals(asg.getFinishTime())) return 0;
        else if(this.getFinishTime().compareTo(asg.getFinishTime()) < 0) return -1;
        else return 1;
    }

    @Override
    public String toString() {
        return "Assignment{name='" + name + "', start='" + start + "', duration=" + duration + ", importance=" + importance + ", maellard=" + maellard + ", finish='" + getFinishTime() + "', weight=" + getWeight() + "}";
    }
}
