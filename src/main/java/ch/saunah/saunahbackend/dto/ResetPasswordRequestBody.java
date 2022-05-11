package ch.saunah.saunahbackend.dto;
/**
 * This class is used as the response DTO object, when the reset request arrived.
 */
public class ResetPasswordRequestBody {
    private String email;

    public String getEmail (){return  email;}

    /**
     * This constructor sets all the fields of this object.
     *
     * @param email Email of the requester
     */
    public ResetPasswordRequestBody(String email){
        this.email = email;
    }
    public ResetPasswordRequestBody(){}
}
