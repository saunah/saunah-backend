package ch.saunah.saunahbackend.dto;

public class ResetPasswordBody {
    private final String email;
    private final String resetToken;
    private final String newPassword;

    public String getEmail (){return  email;}
    public String getResetToken (){return resetToken;}
    public String getNewPassword (){return newPassword;}

    public ResetPasswordBody (String email , String resetToken , String newPassword){
        this.email = email;
        this.resetToken = resetToken;
        this.newPassword = newPassword;
    }


}
