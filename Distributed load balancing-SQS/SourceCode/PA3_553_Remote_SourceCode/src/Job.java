


public class Job {

	private int id;//each job has id, name, and value
	private String name;
	private int a1;
	
	public Job(){//default constructor
		id=0;
		name="sleep";
		a1=0;
	}
	
	public Job(int i, String s, int a){//constructor used for assigning values
		id=i;
		name=s;
		a1=a;
	}
	
	//setter methods
	public void setID(int i){
		id=i;
	}
	public void setName(String s){
		name=s;
	}
	public void setA1(int a){
		a1=a;
	}
	//getter methods
	public int getID(){
		return id;
	}
	public String getName(){
		return name;
	}
	public int getA1(){
		return a1;
	}
	//override equals method, only ids count, since each task will get unique id
	public boolean equals(Job j1){
		if (j1.getID()==id){
			return true;
		}else{
			return false;
		}
	}
	//override toString method
	public String toString(){
		return id + " " + name + " " + a1;
	}
	
	
	
}
