package ch.saunah.saunahbackend.dto;
/**
 * This class is used as the response DTO object, when the reset request arrived.
 */
public class ResetPasswordBody {
    private String newPassword;

    public String getNewPassword (){return newPassword;}

    /**
     * This constructor sets all the fields of this object.
     *
     * @param newPassword New password for the user
     */
    public ResetPasswordBody (String newPassword){
        this.newPassword = newPassword;
    }
    public ResetPasswordBody(){}


}
