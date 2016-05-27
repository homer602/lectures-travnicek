/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples.predicate;
/**
 *
 * @author evo
 */
public class Employee {
    
   public Employee(Integer id, Integer age, String gender, String fName, String lName){
       this.id = id;
       this.age = age;
       this.gender = gender;
       this.firstName = fName;
       this.lastName = lName;
   } 
     
   private Integer id;
   private Integer age;
   private String gender;
   private String firstName;
   private String lastName;
 
   //Please generate Getter and Setters
 
    @Override
    public String toString() {
        return this.id.toString()+" - "+this.getAge().toString(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}