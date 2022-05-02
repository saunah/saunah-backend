package ch.saunah.saunahbackend.dto;
/**
 * This class is used as the response DTO object, when the reset request arrived.
 */
public class ResetPasswordBody {
    private final String email;
    private final String resetToken;
    private final String newPassword;

    public String getEmail (){return  email;}
    public String getResetToken (){return resetToken;}
    public String getNewPassword (){return newPassword;}

    /**
     *
     * @param email Email of the requester
     * @param resetToken Unique Reset token , that is send over mail
     * @param newPassword New password for the user
     */
    public ResetPasswordBody (String email , String resetToken , String newPassword){
        this.email = email;
        this.resetToken = resetToken;
        this.newPassword = newPassword;
    }


}
