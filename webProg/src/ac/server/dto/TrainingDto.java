package ac.server.dto;

public class TrainingDto {
	int training_num;
	String training_name;
	

	public TrainingDto() {
		
	}
	
	

	public int getTraining_num() {
		return training_num;
	}
	public void setTraining_num(int training_num) {
		this.training_num = training_num;
	}
	public String getTraining_name() {
		return training_name;
	}
	public void setTraining_name(String training_name) {
		this.training_name = training_name;
	}
	@Override
	public String toString() {
		return "TrainingDto [training_num=" + training_num + ", training_name=" + training_name + "]";
	}
	
			
}
