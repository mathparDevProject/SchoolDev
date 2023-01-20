package mathpar.web.learning.school.utils.enums;


import mathpar.web.learning.school.utils.exceptions.MalformedDataException;

public enum Role {
    Director, HeadTeacher, Teacher, Student;
    public boolean hasTeacherRole(){
        return this.equals(Director) || this.equals(HeadTeacher) || this.equals(Teacher);
    }

    public boolean canAdministrate(){
        return this.equals(Director) || this.equals(HeadTeacher);
    }

    public static Role of(String userType){
        for(Role type: Role.values()){
            if(type.name().equals(userType)) return type;
        }
        throw new MalformedDataException(String.format("Invalid string  %s, can't find appropriate school user type", userType));
    }

    /**
     * This method checks if the position can be requested by user. For example, Director position can never be requested.
     * @return true if user can send request to apply for this role
     */
    public boolean canBeRequested(){
        return this.equals(Teacher) || this.equals(HeadTeacher);
    }
}
