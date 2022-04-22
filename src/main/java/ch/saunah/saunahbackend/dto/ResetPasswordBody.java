package ch.saunah.saunahbackend.dto;

public class ResetPasswordBody {
    private String email;
    private String resetToken;

    public String getEmail (){return  email;}

    public String getResetToken (){return resetToken;}

}
