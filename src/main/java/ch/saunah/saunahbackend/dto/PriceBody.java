package ch.saunah.saunahbackend.dto;

public class PriceBody {
    private float baseRent;
    private float transportService;
    private float washService;
    private float saunahImp;
    private float deposit;
    private float handTowel;
    private float wood;
    private float extras;

    public float getBaseRent() { return baseRent; }

    public float getTransportService() { return  transportService; }

    public float getWashService() { return  washService; }

    public float getSaunahImp() { return saunahImp; }

    public float getDeposit() { return deposit; }

    public float getHandTowel() { return handTowel; }

    public float getWood() { return wood; }

    public float getExtras() { return extras; }

    public void setBaseRent(float baseRent) { this.baseRent = baseRent; }

    public void setTransportService(float transportService) { this.transportService = transportService; }

    public void setWashService(float washService) { this.washService = washService; }

    public void setSaunahImp(float saunahImp) { this.saunahImp = saunahImp; }

    public void setDeposit(float deposit) { this.deposit = deposit; }

    public void setHandTowel(float handTowel) { this.handTowel = handTowel; }

    public void setWood(float wood) { this.wood = wood; }

    public void setExtras(float extras) { this.extras = extras;}

}
