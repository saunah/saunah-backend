package ch.saunah.saunahbackend.dto;

/**
 * This class is used as the DTO object when creating a price structure.
 */
public class PriceBody {
    private float transportService;
    private float washService;
    private float saunahImp;
    private float deposit;
    private float handTowel;
    private float wood;
    private float discount;
    private String discountDescription;

    public float getTransportService() {
        return transportService;
    }

    public float getWashService() {
        return washService;
    }

    public float getSaunahImp() {
        return saunahImp;
    }

    public float getDeposit() {
        return deposit;
    }

    public float getHandTowel() {
        return handTowel;
    }

    public float getWood() {
        return wood;
    }

    public float getDiscount() {
        return discount;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setTransportService(float transportService) {
        this.transportService = transportService;
    }

    public void setWashService(float washService) {
        this.washService = washService;
    }

    public void setSaunahImp(float saunahImp) {
        this.saunahImp = saunahImp;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public void setHandTowel(float handTowel) {
        this.handTowel = handTowel;
    }

    public void setWood(float wood) {
        this.wood = wood;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }
}
